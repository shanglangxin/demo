package com.example.demo.service.imp;

import com.example.demo.mapper.*;
import com.example.demo.pojo.Option;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.QuestionTypeUtil;
import com.example.demo.vo.QuestionDetailVO;
import com.github.pagehelper.PageHelper;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service("questionMgrService")
public class QuestionMgrServiceImp implements IQuestionMgrService {

    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    @Autowired
    private MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    @Autowired
    private JudgeQuestionMapper judgeQuestionMapper;
    @Autowired
    private CompletionQuestionMapper completionQuestionMapper;
    @Autowired
    private MultipleEntryQuestionMapper multipleEntryQuestionMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    @Transactional
    public void addOrUpdateQuestion(Map<String, Object> param) {
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        this.handleQuestionAnswer(param);
        this.handleAnswer(param);
        Integer id = (Integer) param.get("id");
        if(param.get("id") == null){
            questionMapper.addQuestion(param);
        }else{
            questionMapper.updateQuestion(param);
            optionMapper.deleteOptionByQuestionId(param);
        }
        if(param.get("type").equals(QuestionTypeUtil.SINGLE_CHOICE_QUESTION) || param.get("type").equals(QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION)){
            optionMapper.addOptions(param);
        }
    }

    @Override
    public List<SubjectPO> querySubjects(Map<String, Object> param) {
        List<SubjectPO> list = new ArrayList<>();
        list = subjectMapper.querySubjects(param);
        return list;
    }

    @Override
    public List<QuestionDetailVO> queryQuestionList(Map<String, Object> param) {
        List<QuestionDetailVO> list = new ArrayList<>();
        Integer type = (Integer) param.get("type");
        if(type == null){
//            throw new MyException(-1,"选择查询题型");
        }
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        PageHelper.startPage((Integer) param.get("page"), 30);
        list = questionMapper.queryQuestion(param);
        if(type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION || type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
            for(QuestionDetailVO vo : list){
                List<Option> optionList = optionMapper.queryOptionListByQuestionId(vo);
                vo.setOptionList(optionList);
            }
        }

        return list;
    }

    @Override
    @Transactional
    public void updateQuestion(Map<String, Object> param) {
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        this.handleQuestionAnswer(param);
        questionMapper.updateQuestion(param);
        if(param.get("type").equals(QuestionTypeUtil.SINGLE_CHOICE_QUESTION) || param.get("type").equals(QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION)){
            optionMapper.deleteOptionByQuestionId(param);
            optionMapper.addOptions(param);
        }
    }

    @Override
    public void deleteQuestion(Map<String, Object> param) {
        Integer type = (Integer) param.get("type");
        String table = this.queryQuestionTable(type);
        param.put("table", table);
        if(type.equals(QuestionTypeUtil.SINGLE_CHOICE_QUESTION) || type.equals(QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION)){
            optionMapper.deleteOptionByQuestionIds(param);
        }
        questionMapper.deleteQuestion(param);
    }

    private String queryQuestionTable(Integer type){
        String tableName = "";
        if(type == QuestionTypeUtil.COMPLETION_QUESTION){
            tableName = "completion_question";
        }else if(type == QuestionTypeUtil.JUDGE_QUESTION){
            tableName = "judge_question";
        }else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
            tableName = "multiple_choice_question";
        }else if(type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
            tableName = "multiple_entry_question";
        }else if(type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
            tableName = "single_choice_question";
        }
        return tableName;
    }

    private void handleQuestionAnswer(Map<String, Object> param){
        Integer type = (Integer) param.get("type");
        if(type == QuestionTypeUtil.COMPLETION_QUESTION || type == QuestionTypeUtil.JUDGE_QUESTION || type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
            String answer = (String) param.get("singleAnswer");
            param.put("answer", answer);
        }else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION || type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
            List<String> multiple = (List<String>) param.get("multiAnswer");
            String answer = StringUtils.join(multiple, ',');
            param.put("answer", answer);
        }
    }
    
    private void handleAnswer(Map<String, Object> param){
    	Integer type = (Integer) param.get("type");
    	if(type == QuestionTypeUtil.COMPLETION_QUESTION){
            param.put("answer", param.get("singleAnswer"));
        }else if(type == QuestionTypeUtil.JUDGE_QUESTION){
        	param.put("answer", param.get("singleAnswer"));
        }else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
            List<String> list = (List<String>) param.get("multiAnswer");
            String answer = StringUtils.join(list,',');
            param.put("answer", answer);
        }else if(type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
        	List<String> list = (List<String>) param.get("multiAnswer");
            String answer = StringUtils.join(list,',');
            param.put("answer", answer);
        }else if(type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
        	param.put("answer", param.get("singleAnswer"));
        }
    }
 





}
