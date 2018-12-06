package com.example.demo.dto;

import java.util.List;

public class PaperDTO {

    private Integer id;
    private String title;
    private Integer approach;
    private Integer singleChoiceCount;
    private Integer multiChoiceCount;
    private Integer judgeCount;
    private Integer completionCount;
    private Integer multiEntryCount;
    private Integer subjectId;
    private List<QuestionDTO> questionList;


    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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

    public Integer getApproach() {
        return approach;
    }

    public void setApproach(Integer approach) {
        this.approach = approach;
    }

    public Integer getSingleChoiceCount() {
        return singleChoiceCount;
    }

    public void setSingleChoiceCount(Integer singleChoiceCount) {
        this.singleChoiceCount = singleChoiceCount;
    }

    public Integer getMultiChoiceCount() {
        return multiChoiceCount;
    }

    public void setMultiChoiceCount(Integer multiChoiceCount) {
        this.multiChoiceCount = multiChoiceCount;
    }

    public Integer getJudgeCount() {
        return judgeCount;
    }

    public void setJudgeCount(Integer judgeCount) {
        this.judgeCount = judgeCount;
    }

    public Integer getCompletionCount() {
        return completionCount;
    }

    public void setCompletionCount(Integer completionCount) {
        this.completionCount = completionCount;
    }

    public Integer getMultiEntryCount() {
        return multiEntryCount;
    }

    public void setMultiEntryCount(Integer multiEntryCount) {
        this.multiEntryCount = multiEntryCount;
    }

    public List<QuestionDTO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionDTO> questionList) {
        this.questionList = questionList;
    }
}
