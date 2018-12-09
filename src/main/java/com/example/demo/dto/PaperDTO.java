package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class PaperDTO {

    private Integer id;
    @NotEmpty(message="试卷标题不能为空")
    private String title;
    @NotEmpty(message="考试时间不能为空")
    private Integer duration;
    private Date startTime;
    private Date endTime;
    private Integer status;
    @NotEmpty(message="试卷科目不能为空")
    private Integer subjectId;
    private List<PaperQuestionDTO> questionList;
    private Integer approach;
    private Integer singleChoiceCount;
    private Integer multiChoiceCount;
    private Integer judgeCount;
    private Integer completionCount;
    private Integer multiEntryCount;

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

	public List<PaperQuestionDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<PaperQuestionDTO> questionList) {
		this.questionList = questionList;
	}

    
}
