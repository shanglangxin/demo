package com.example.demo.mapper;

import com.example.demo.pojo.SubjectPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SubjectMapper {
    List<SubjectPO> querySubjects(Map<String,Object> param);
}
