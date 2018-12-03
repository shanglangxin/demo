package com.example.demo.service.imp;

import com.example.demo.mapper.*;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.MyException;
import com.example.demo.util.QuestionTypeUtil;
import com.github.pagehelper.PageHelper;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public void addQuestion(Map<String, Object> param) {
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        questionMapper.addQuestion(param);
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
    public List<Question> queryQuestionList(Map<String, Object> param) {
        List<Question> list = new ArrayList<>();
        if(param.get("type") == null && param.get("type").equals("")){
//            throw new MyException(-1,"选择查询题型");
        }
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        PageHelper.startPage((Integer) param.get("page"), 30);
        list = questionMapper.queryQuestion(param);
        return list;
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



}
