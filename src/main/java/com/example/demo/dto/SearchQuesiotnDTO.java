package com.example.demo.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class SearchQuesiotnDTO {

    private String creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotEmpty(message = "科目类型不能为空")
    private Integer subject;
    @NotEmpty(message = "题目类型不能为空")
    private Integer type;
    private Integer page;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
