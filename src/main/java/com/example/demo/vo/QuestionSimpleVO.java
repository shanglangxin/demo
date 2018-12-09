package com.example.demo.vo;

import java.util.List;

import com.example.demo.pojo.Option;

public class QuestionSimpleVO {
	
	private Integer id;
	private String title;
	private String showTitle;
	private List<Option> optionList;
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
	public List<Option> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	
	

}
