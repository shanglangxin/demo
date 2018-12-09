package com.example.demo.service.imp;

import com.example.demo.mapper.OptionMapper;
import com.example.demo.mapper.PaperMapper;
import com.example.demo.mapper.PaperQuestionMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.pojo.Option;
import com.example.demo.pojo.PaperPO;
import com.example.demo.pojo.Question;
import com.example.demo.service.IPaperMgrService;
import com.example.demo.util.MyException;
import com.example.demo.vo.PaperDetailVO;
import com.example.demo.vo.QuestionDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("paperMgrService")
public class PaperMgrServiceImp implements IPaperMgrService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private PaperQuestionMapper paperQuestionMapper;
    
    @Override
	public List<PaperPO> queryPaperList(Map<String, Object> param) {
		// TODO Auto-generated method stub
    	List<PaperPO> list = paperMapper.queryPaperList(param);
		return list;
	}

    @Override
    public void addOrUpdatePaper(Map<String, Object> param) throws MyException {
//        paperMapper.addOrUpdatePaper(param);
//        Integer approach = (Integer) param.get("approach");
//        if(approach == 0){
//            List<QuestionDetailVO> questionList = queryQuestionList(param);
//        }
    	Integer status = this.handlePaperStatus((Date) param.get("startTime"), (Date) param.get("endTime"));
    	param.put("status", status);
    	Integer id = (Integer) param.get("id");
    	if(id == null || id == 0){
    		paperMapper.addPaper(param);
    		paperQuestionMapper.addQuestions(param);
    		
    	}else{
    		paperQuestionMapper.deleteQuestionsByPaperId(id);
    		paperMapper.update(param);
    		paperQuestionMapper.addQuestions(param);
    	}

    }

	@Override
	public List<QuestionDetailVO> autoCreateQuestionList(Map<String, Object> param) throws MyException {
		// TODO Auto-generated method stub
		List<QuestionDetailVO> list = new ArrayList<>();
        Integer singleChoiceCount = (Integer) param.get("singleChoiceCount");
        Integer multiChoiceCount = (Integer) param.get("multiChoiceCount");
        Integer completionCount = (Integer) param.get("completionCount");
        Integer multiEntryCount = (Integer) param.get("multiEntryCount");
        Integer judgeCount = (Integer) param.get("judgeCount");
        Integer subjectId = (Integer) param.get("subjectId");
        if(singleChoiceCount != null && singleChoiceCount>0){
        	String table = "single_choice_question";
        	List<QuestionDetailVO> questionList = queryQuestionList(table, subjectId, singleChoiceCount);
        	list.addAll(questionList);
        }
        if(multiChoiceCount != null && multiChoiceCount>0){
            String table = "multiple_choice_question";
            List<QuestionDetailVO> questionList = queryQuestionList(table, subjectId, multiChoiceCount);
        	list.addAll(questionList);
        }
        if(multiEntryCount != null && multiEntryCount>0){
        	String table = "multiple_entry_question";
        	List<QuestionDetailVO> questionList = queryQuestionList(table, subjectId, multiEntryCount);
        	list.addAll(questionList);
        }
        if(judgeCount != null && judgeCount>0){
	        String table = "judge_question";
	        List<QuestionDetailVO> questionList = queryQuestionList(table, subjectId, judgeCount);
	        list.addAll(questionList);
        }
        if(completionCount != null && completionCount>0){
	        String table = "completion_question";
	        List<QuestionDetailVO> questionList = queryQuestionList(table, subjectId, completionCount);
	      	list.addAll(questionList);
        }
		return list;
	}
	
	@Override
	public PaperDetailVO queryPaperDetail(Integer paperId) {
		// TODO Auto-generated method stub
		return null;
	}
    
	/**
	 * 获取题目
	 * @param table
	 * @param subjectId
	 * @param count
	 * @return
	 * @throws MyException 
	 */
	private List<QuestionDetailVO> queryQuestionList(String table, Integer subjectId, Integer count) throws MyException{
		// 确定题目总数
        Integer totalCount = questionMapper.queryQuestionTotalNumber(table);
        // 获取题库中所有题目id，确保随机id存在
        List<Integer> allQuestionIdList = questionMapper.queryQuestionIdList(table, subjectId); //todo subjectId 可以为空
        // 获取随机题目id列
        List<Integer> idList = queryQuestionIds(totalCount, count, allQuestionIdList);
        List<QuestionDetailVO> questionList = questionMapper.queryQuestionList(table,idList);
        if(table.equals("single_choice_question") || table.equals("multiple_choice_question")){
        	for(QuestionDetailVO vo : questionList){
                List<Option> optionList = optionMapper.queryOptionListByQuestionId(vo);
                if(optionList == null){
                    throw new MyException(-1,"题目有错");
                }
                vo.setOptionList(optionList);
            }
        }
        return questionList;
	}

	/**
	 * 获取题目id
	 * @param totalCount
	 * @param questionCount
	 * @param allQuestionIdList
	 * @return
	 */
    private List<Integer> queryQuestionIds(Integer totalCount, Integer questionCount, List<Integer> allQuestionIdList) {
        List<Integer> idList = new ArrayList<>();
        for(int i=0; i<=questionCount; i++){
            Integer id = (int)(Math.random()*totalCount + 1);
            if (!idList.contains(id) && allQuestionIdList.contains(id)){
                idList.add(id);
            }
        }
        return idList;
    }
    
    /**
     * 处理发布状态
     * @param startTime
     * @param endTime
     * @return 
     */
    private Integer handlePaperStatus(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
    	Integer status = 0;
		if(startTime == null || endTime == null){
			status = 0;
		}else if(new Date().before(endTime) && new Date().after(startTime)){
			status = 1;
		}else{
			status = 0;
		}
		return status;
	}


//  private List<QuestionDetailVO> queryQuestionList(Map<String,Object> param) throws MyException {
//  List<QuestionDetailVO> list = new ArrayList<>();
//  Integer singleChoiceCount = (Integer) param.get("singleChoiceCount");
//  Integer multiChoiceCount = (Integer) param.get("multiChoiceCount");
//  Integer completionCount = (Integer) param.get("completionCount");
//  Integer multiEntryCount = (Integer) param.get("multiEntryCount");
//  Integer judgeCount = (Integer) param.get("judgeCount");
//  Integer subjectId = (Integer) param.get("subjectId");
//  // 确定需要哪些类型题目
//  if(singleChoiceCount != null && singleChoiceCount>0){
//      String table = "single_choice_question";
//      // 确定题目总数
//      Integer totalCount = questionMapper.queryQuestionTotalNumber(table);
//      // 获取题库中所有题目id，确保随机id存在
//      List<Integer> allQuestionIdList = questionMapper.queryQuestionIdList(table, subjectId); //todo subjectId 可以为空
//      // 获取随机题目id列
//      List<Integer> idList = queryQuestionIds(totalCount, singleChoiceCount, allQuestionIdList);
//      List<QuestionDetailVO> questionList = questionMapper.queryQuestionList(table,idList);
//      for(QuestionDetailVO vo : questionList){
//          List<Option> optionList = optionMapper.queryOptionListByQuestionId(vo);
//          if(optionList == null){
//              throw new MyException(-1,"题目有错");
//          }
//          vo.setOptionList(optionList);
//      }
//      list.addAll(questionList);
//  }
//  if(multiChoiceCount != null && multiChoiceCount>0){
//      String table = "multiple_choice_question";
//      Integer totalCount = questionMapper.queryQuestionTotalNumber(table);
//      List<Integer> allQuestionIdList = questionMapper.queryQuestionIdList(table, subjectId);
//      List<Integer> idList = queryQuestionIds(totalCount, multiChoiceCount, allQuestionIdList);
//      List<QuestionDetailVO> questionList = questionMapper.queryQuestionList(table,idList);
//      for(QuestionDetailVO vo : questionList){
//          List<Option> optionList = optionMapper.queryOptionListByQuestionId(vo);
//          if(optionList == null){
//              throw new MyException(-1,"题目有错");
//          }
//          vo.setOptionList(optionList);
//      }
//      list.addAll(questionList);
//  }
//  if(multiEntryCount != null && multiEntryCount>0){
//      String table = "multiple_entry_question";
//      Integer totalCount = questionMapper.queryQuestionTotalNumber(table);
//      List<Integer> allQuestionIdList = questionMapper.queryQuestionIdList(table, subjectId);
//      List<Integer> idList = queryQuestionIds(totalCount, multiEntryCount, allQuestionIdList);
//      List<QuestionDetailVO> questionList = questionMapper.queryQuestionList(table,idList);
//      list.addAll(questionList);
//  }
//  if(judgeCount != null && judgeCount>0){
//      String table = "judge_question";
//      Integer totalCount = questionMapper.queryQuestionTotalNumber(table);
//      List<Integer> allQuestionIdList = questionMapper.queryQuestionIdList(table, subjectId);
//      List<Integer> idList = queryQuestionIds(totalCount, judgeCount, allQuestionIdList);
//      List<QuestionDetailVO> questionList = questionMapper.queryQuestionList(table,idList);
//      list.addAll(questionList);
//  }if(completionCount != null && completionCount>0){
//      String table = "completion_question";
//      Integer totalCount = questionMapper.queryQuestionTotalNumber(table);
//      List<Integer> allQuestionIdList = questionMapper.queryQuestionIdList(table, subjectId);
//      List<Integer> idList = queryQuestionIds(totalCount, completionCount, allQuestionIdList);
//      List<QuestionDetailVO> questionList = questionMapper.queryQuestionList(table,idList);
//      list.addAll(questionList);
//  }
//  return list;
//}
}
