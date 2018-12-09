package com.example.demo.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class PaperQuestionDTO {
	
	private Integer id;

    private String title;

    private String singleAnswer;

    private List<String> multiAnswer;

    private List<OptionDTO> optionList;

    private Integer type;

    private String showTitle;

    private Integer subjectId;

}
