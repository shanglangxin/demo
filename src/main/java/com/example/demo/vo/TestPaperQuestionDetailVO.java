package com.example.demo.vo;

import com.example.demo.dto.OptionDTO;
import com.example.demo.pojo.TestQuestionOptionPO;

import java.util.List;

public class TestPaperQuestionDetailVO {

    private Integer id;

    private String title;

    private String singleAnswer;

    private List<String> multiAnswer;

    private String answer;

    private List<TestQuestionOptionPO> optionList;

    private Integer type;

    private String showTitle;

    private Integer subjectId;

    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSingleAnswer() {
        return singleAnswer;
    }

    public void setSingleAnswer(String singleAnswer) {
        this.singleAnswer = singleAnswer;
    }

    public List<String> getMultiAnswer() {
        return multiAnswer;
    }

    public void setMultiAnswer(List<String> multiAnswer) {
        this.multiAnswer = multiAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<TestQuestionOptionPO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<TestQuestionOptionPO> optionList) {
        this.optionList = optionList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
