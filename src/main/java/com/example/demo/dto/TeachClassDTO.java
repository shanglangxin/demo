package com.example.demo.dto;

import java.util.List;

public class TeachClassDTO {

    private String staffId;
    private List<TestClassDTO> classList;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public List<TestClassDTO> getClassList() {
        return classList;
    }

    public void setClassList(List<TestClassDTO> classList) {
        this.classList = classList;
    }
}
