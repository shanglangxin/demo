package com.example.demo.vo;

import com.example.demo.pojo.OptionPO;

import java.sql.Date;
import java.util.List;

public class QuestionDetailVO {

    private Integer id;
    private String title;
    private String answer;
    private Integer type;
    private Integer subjectId;
    private String creator;
    private Date createTime;
    private String showTitle;
    private List<OptionPO> optionList;

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

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public List<OptionPO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionPO> optionList) {
        this.optionList = optionList;
    }
}
