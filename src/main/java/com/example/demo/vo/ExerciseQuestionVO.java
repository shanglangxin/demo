package com.example.demo.vo;

import java.util.List;

public class ExerciseQuestionVO {

    private Integer id;
    private String title;
    private Integer subjecctId;
    private Integer type;
    private List<String> multiAnswer;
    private String singleAnswer;
    private String answer;
    private String analysis;
    private List<ExerciseOptionVO> optionList;

    public List<String> getMultiAnswer() {
        return multiAnswer;
    }

    public void setMultiAnswer(List<String> multiAnswer) {
        this.multiAnswer = multiAnswer;
    }

    public List<ExerciseOptionVO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<ExerciseOptionVO> optionList) {
        this.optionList = optionList;
    }

    public String getSingleAnswer() {
        return singleAnswer;
    }

    public void setSingleAnswer(String singleAnswer) {
        this.singleAnswer = singleAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

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

    public Integer getSubjecctId() {
        return subjecctId;
    }

    public void setSubjecctId(Integer subjecctId) {
        this.subjecctId = subjecctId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
