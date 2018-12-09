package com.example.demo.dto;

public class QuestionCountDTO {
	
	private Integer singleChoiceCount;
    private Integer multiChoiceCount;
    private Integer judgeCount;
    private Integer completionCount;
    private Integer multiEntryCount;
    private Integer subjectId;
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
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
    
    

}
