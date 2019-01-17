package com.example.demo.vo;

import java.util.Date;
import java.util.List;

public class StudentTestPaperVO {

    private Integer id;
    private String title;
    private Integer duration;
    private Integer subjectId;
    private Byte testForm;
    private List<TestPaperQuestionDetailVO> questionList;

    public Byte getTestForm() {
        return testForm;
    }

    public void setTestForm(Byte testForm) {
        this.testForm = testForm;
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

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public List<TestPaperQuestionDetailVO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<TestPaperQuestionDetailVO> questionList) {
        this.questionList = questionList;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
