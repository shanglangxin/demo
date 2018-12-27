package com.example.demo.dto;

import java.util.List;

public class AnswerDTO {

    private Integer id;

    private String answer;

    private Integer type;

    List<AnswerDTO> list;

    public List<AnswerDTO> getList() {
        return list;
    }

    public void setList(List<AnswerDTO> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
