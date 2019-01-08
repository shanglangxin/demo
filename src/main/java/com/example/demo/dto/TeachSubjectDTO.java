package com.example.demo.dto;

import java.util.List;

public class TeachSubjectDTO {

    private String staffId;
    private List<Integer> subjectList;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public List<Integer> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Integer> subjectList) {
        this.subjectList = subjectList;
    }
}
