package com.example.demo.mapper;

import com.example.demo.pojo.StudentPO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StudentMapper {
    void addStudent(Map<String,Object> param);

    StudentPO queryStudentById(Object username);
}
