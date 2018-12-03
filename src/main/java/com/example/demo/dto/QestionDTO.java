package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class QestionDTO {

    private Integer id;

    @NotEmpty(message = "题目不能为空")
    private String title;

    private String singleAnswer;

    private List<String> multiAnswer;

    private List<OptionDTO> optionList;

    private Integer type;

    private String showTitle;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public List<OptionDTO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionDTO> optionList) {
        this.optionList = optionList;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }
}
