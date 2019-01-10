package com.example.demo.vo;

import com.example.demo.pojo.OptionPO;

import java.util.List;

public class ImportQuestionVO {

    private Integer id;
    private String title;
    private String showTitle;
    private String answer;
    private List<String> optionTitleList;
    private Integer type;
    private Integer subjectId;
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptionTitleList() {
        return optionTitleList;
    }

    public void setOptionTitleList(List<String> optionTitleList) {
        this.optionTitleList = optionTitleList;
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
}
