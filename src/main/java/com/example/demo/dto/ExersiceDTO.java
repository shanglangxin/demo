package com.example.demo.dto;

import java.util.List;

public class ExersiceDTO {

    private Integer subjectId;
    private Integer type;
    private Integer questionCount;

    private List<ExersiceAnswerDTO> list;

    public List<ExersiceAnswerDTO> getList() {
        return list;
    }

    public void setList(List<ExersiceAnswerDTO> list) {
        this.list = list;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }
}
