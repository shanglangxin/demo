package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;

public class PersonalInfoDTO {

    @NotEmpty(message = "班级编号不能为空")
    private Integer classId;
    @NotEmpty(message = "学号不能为空")
    private Integer staffId;
    @NotEmpty(message = "姓名不能为空")
    private String name;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
