package com.example.demo.service.imp;

import com.example.demo.dto.SubmitPaperQuestionDTO;
import com.example.demo.mapper.TestClassStudentMapper;
import com.example.demo.mapper.TestPaperMapper;
import com.example.demo.mapper.TestPaperQuestionMapper;
import com.example.demo.mapper.TestQuestionOptionMapper;
import com.example.demo.pojo.TestPaperPO;
import com.example.demo.pojo.TestPaperQuestionNumPO;
import com.example.demo.pojo.TestQuestionOptionPO;
import com.example.demo.service.IStudentTestService;
import com.example.demo.service.ITestPaperMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.QuestionTypeUtil;
import com.example.demo.vo.*;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("studentTestService")
public class StudentTestServiceImp implements IStudentTestService {

    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private TestPaperQuestionMapper testPaperQuestionMapper;
    @Autowired
    private TestQuestionOptionMapper testQuestionOptionMapper;
    @Autowired
    private TestClassStudentMapper testClassStudentMapper;
    @Autowired
    private ITestPaperMgrService testPaperMgrService;

    @Override
    public StudentTestPaperVO queryTestPaper(Integer paperId) throws MyException {
        StudentTestPaperVO detailVO = testPaperMapper.queryStudentTestPaperById(paperId);
        if(detailVO == null){
            throw new MyException(-1, "试卷不存在");
        }
        if(detailVO.getTestForm().equals(new Byte("1"))){
            List<TestPaperQuestionDetailVO> questionList = testPaperQuestionMapper.queryTestQuestionByPaperId(paperId);
            for(TestPaperQuestionDetailVO vo : questionList){
                if(vo.getType() == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
                    List<String> answerList = Arrays.asList(vo.getAnswer().split(","));
                    for(int i =0 ; i<answerList.size(); i++){
                        answerList.set(i,"");
                    }
                    vo.setMultiAnswer(answerList);
                    vo.setAnswer("");
                }else if(vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
                    vo.setMultiAnswer(new ArrayList<>());
                    vo.setAnswer("");
                } else{
                    vo.setSingleAnswer("");
                    vo.setAnswer("");
                }
                if(vo.getType() == QuestionTypeUtil.SINGLE_CHOICE_QUESTION || vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
                    List<TestQuestionOptionPO> optionList = testQuestionOptionMapper.queryTestOptionByTestQuestionId(vo.getId());
                    vo.setOptionList(optionList);
                }
            }
            detailVO.setQuestionList(questionList);
        }else{
            TestPaperQuestionNumPO questionNumPO = testPaperMapper.queryTestPaperQuestionNumByPaperId(paperId);
            Map<String, Object> param = new HashMap<>();
            param.put("singleChoiceCount", questionNumPO.getSingleChoiceCount());
            param.put("multiChoiceCount", questionNumPO.getMultiChoiceCount());
            param.put("multiEntryCount", questionNumPO.getMultiEntryCount());
            param.put("judgeCount", questionNumPO.getJudgeCount());
            param.put("completionCount", questionNumPO.getCompletionCount());
            List<QuestionDetailVO> list = testPaperMgrService.autoCreateQuestionList(param);
        }

        return detailVO;
    }

    @Override
    public Integer submitTestPaper(Map<String, Object> param) {
        Integer totalMark = 0;
        List<TestPaperQuestionDetailVO> questionList = testPaperQuestionMapper.queryTestQuestionByPaperId((Integer) param.get("paperId"));
        List<SubmitPaperQuestionDTO> list = (List<SubmitPaperQuestionDTO>) param.get("questionList");
        Map<Integer, TestPaperQuestionDetailVO> answerMap = new HashMap<>();
        questionList.forEach(question->{
            answerMap.put(question.getId(), question);
        });
        list.forEach(question->{
            String answer = "";
            if(question.getType() == QuestionTypeUtil.COMPLETION_QUESTION || question.getType() == QuestionTypeUtil.JUDGE_QUESTION || question.getType() == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
                if(question.getSingleAnswer() != null){
                    answer = question.getSingleAnswer();
                }
            }else if(question.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION || question.getType() == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
                if(question.getMultiAnswer().size() > 0){
                    List<String> multiple = question.getMultiAnswer();
                    answer = StringUtils.join(multiple, ',');
                }
            }
            question.setAnswer(answer);
        });
        for(SubmitPaperQuestionDTO question : list){
            TestPaperQuestionDetailVO answer = answerMap.get(question.getId());
            if(answer.getAnswer().equals(question.getAnswer())){
                totalMark += answer.getMark();
            }
        }
        Integer mark = totalMark;
        testClassStudentMapper.saveTestStudentMark((Integer)param.get("paperId"), (String)param.get("staffId"), mark);
        return mark;
    }

    @Override
    public List<StudentTestPaperRefVO> queryTestPaperList(Map<String, Object> param) {
        List<StudentTestPaperRefVO> list = testPaperMapper.queryTestPaperList(param);
        return list;
    }


}
