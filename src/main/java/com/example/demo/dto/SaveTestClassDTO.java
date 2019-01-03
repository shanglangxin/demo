package com.example.demo.dto;

import java.util.List;

public class SaveTestClassDTO {

    private Integer testPaperId;
    private List<TestClassDTO> classList;

    public Integer getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {
        this.testPaperId = testPaperId;
    }

    public List<TestClassDTO> getClassList() {
        return classList;
    }

    public void setClassList(List<TestClassDTO> classList) {
        this.classList = classList;
    }
}
