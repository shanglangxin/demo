package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TestPaperDTO {

    private Integer id;
    @NotEmpty(message="试卷标题不能为空")
    private String title;
    @NotNull(message="考试时间不能为空")
    private Integer duration;
    @Future(message = "发布时间不能是过去的时间")
    private Date startTime;
    private Date endTime;
    private Integer status;
    @NotNull(message="试卷科目不能为空")
    private Integer subjectId;
    private List<TestPaperQuestionDTO> questionList;
    private Integer totalMark;
    private TestQuestionNumDTO questionNumDto;
    private Byte testForm;

    public Byte getTestForm() {
        return testForm;
    }

    public void setTestForm(Byte testForm) {
        this.testForm = testForm;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public Integer getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Integer totalMark) {
        this.totalMark = totalMark;
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

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<TestPaperQuestionDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<TestPaperQuestionDTO> questionList) {
		this.questionList = questionList;
	}

    public TestQuestionNumDTO getQuestionNumDto() {
        return questionNumDto;
    }

    public void setQuestionNumDto(TestQuestionNumDTO questionNumDto) {
        this.questionNumDto = questionNumDto;
    }
}
