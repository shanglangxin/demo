package com.example.demo.service.imp;

import com.example.demo.dto.TestClassDTO;
import com.example.demo.dto.TestPaperQuestionDTO;
import com.example.demo.mapper.*;
import com.example.demo.pojo.OptionPO;
import com.example.demo.pojo.StudentPO;
import com.example.demo.pojo.TestQuestionOptionPO;
import com.example.demo.pojo.TestPaperPO;
import com.example.demo.service.ITestPaperMgrService;
import com.example.demo.util.AssertUtil;
import com.example.demo.util.MyException;
import com.example.demo.util.QuestionTypeUtil;
import com.example.demo.vo.*;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("testPaperMgrService")
public class TestPaperMgrServiceImp implements ITestPaperMgrService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private TestPaperQuestionMapper testPaperQuestionMapper;
    @Autowired
	private TestQuestionOptionMapper testQuestionOptionMapper;
    @Autowired
	private TestClassStudentMapper testClassStudentMapper;
    @Autowired
	private StudentMapper studentMapper;
    
    @Override
	public List<TestPaperPO> queryPaperList(Map<String, Object> param) {
    	List<TestPaperPO> list = testPaperMapper.queryPaperList(param);
		return list;
	}

    @Override
    public void addOrUpdatePaper(Map<String, Object> param) throws MyException {
    	Integer status = this.handlePaperStatus((Date) param.get("startTime"), (Date) param.get("endTime"));
    	param.put("status", status);
    	Integer id = (Integer) param.get("id");
		List<TestPaperQuestionDTO> list = (List<TestPaperQuestionDTO>) param.get("questionList");
		this.handleQuestionAnswer(list);
    	if(id == null || id == 0){
			testPaperMapper.addPaper(param);
			testPaperQuestionMapper.addTestPaperQuestions(list, ((Long)param.get("id")).intValue());
    	}else{
			List<Integer> questionIdList = testPaperQuestionMapper.queryTestQuestionIdsByPaperId(id);
			testQuestionOptionMapper.deleteOptionByTestQuestionIds(questionIdList);
			testPaperQuestionMapper.deleteQuestionsByPaperId(id);
			testPaperMapper.updateTestPaper(param);
			testPaperQuestionMapper.addTestPaperQuestions(list, id);
    	}
		for(TestPaperQuestionDTO dto : list){
    		if(dto.getType() == QuestionTypeUtil.SINGLE_CHOICE_QUESTION || dto.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
				testQuestionOptionMapper.addTestOption(dto.getId(), dto.getOptionList(), dto.getType());
			}
		}
    }

	@Override
	public List<QuestionDetailVO> autoCreateQuestionList(Map<String, Object> param) throws MyException {
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
	public TestPaperDetailVO queryPaperDetail(Integer paperId) {
		TestPaperDetailVO paperDetailVO = testPaperMapper.queryTestPaperById(paperId);
		List<TestPaperQuestionDetailVO> list = testPaperQuestionMapper.queryTestQuestionByPaperId(paperId);
		for(TestPaperQuestionDetailVO vo : list){
			if(vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION || vo.getType() == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
				vo.setMultiAnswer(Arrays.asList(vo.getAnswer().split(",")));
			}else{
				vo.setSingleAnswer(vo.getAnswer());
			}
			if(vo.getType() == QuestionTypeUtil.SINGLE_CHOICE_QUESTION || vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
				List<TestQuestionOptionPO> optionList = testQuestionOptionMapper.queryTestOptionByTestQuestionId(vo.getId());
				vo.setOptionList(optionList);
			}
		}
		paperDetailVO.setQuestionList(list);
		return paperDetailVO;
	}

	@Override
	public List<TestClassVO> queryTestClass(Integer paperId) {
		List<TestClassVO> list = testClassStudentMapper.queryTestClass(paperId);
		return list;
	}

	@Override
	public void saveTestClass(Map<String, Object> param) throws MyException {
		Integer paperId = (Integer) param.get("testPaperId");
		List<TestClassDTO> classList = (List<TestClassDTO>) param.get("classList");
		List<TestClassVO> list = testClassStudentMapper.queryTestClass(paperId);
		for(TestClassVO vo : list){
			for(TestClassDTO dto : classList){
				if(vo.getClassId().equals(dto.getClassId())){
					throw new MyException(-1, "存在相同班级");
				}
			}
		}
		testClassStudentMapper.saveTestClass(param);
		List<StudentPO> studentList = studentMapper.queryStudentByClassId(classList);
		testClassStudentMapper.saveTestStudent(paperId, studentList);
	}

	@Override
	public void deleteTestClass(Map<String, Object> param) {
		testClassStudentMapper.deleteTestClass(param);
		testClassStudentMapper.deleteTestStudent(param);
	}

	@Override
	public void deleteTestPaper(List<Integer> ids) throws MyException {
    	for(Integer paperId : ids){
    		List<TestClassVO> classList = testClassStudentMapper.queryTestClass(paperId);
    		if(!AssertUtil.isEmpty(classList)){
    			throw new MyException(-1, "试卷编号为"+paperId+"仍有班级在考试");
			}
			List<Integer> list = testPaperQuestionMapper.queryTestQuestionIdsByPaperId(paperId);
			testQuestionOptionMapper.deleteOptionByTestQuestionIds(list);
			testPaperQuestionMapper.deleteQuestionsByPaperId(paperId);
			testPaperMapper.deleteTestPaper(paperId);
		}
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
                List<OptionPO> optionList = optionMapper.queryOptionListByQuestionId(vo);
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
        int i = 0;
        while(i<questionCount){
            Integer id = (int)(Math.random()*totalCount) ;
            if(id > totalCount){
            	continue;
			}
            Integer questionId = allQuestionIdList.get(id);
            if (!idList.contains(questionId)){
                idList.add(questionId);
                i++;
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

	// 处理题目答案
	private void handleQuestionAnswer(List<TestPaperQuestionDTO> list) throws MyException {
    	for(TestPaperQuestionDTO dto : list){
    		Integer type = dto.getType();
    		if(type == null || type == 0){
    			throw new MyException(-1,"题目类型不能为空");
			}
			if(type == QuestionTypeUtil.COMPLETION_QUESTION || type == QuestionTypeUtil.JUDGE_QUESTION || type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
				String answer = dto.getSingleAnswer();
				dto.setAnswer(answer);
			}else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION || type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
				List<String> multiple = dto.getMultiAnswer();
				String answer = StringUtils.join(multiple, ',');
				dto.setAnswer(answer);
			}
		}
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
//          List<OptionPO> optionList = optionMapper.queryOptionListByQuestionId(vo);
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
//          List<OptionPO> optionList = optionMapper.queryOptionListByQuestionId(vo);
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
