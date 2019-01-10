package com.example.demo.mapper;

import com.example.demo.pojo.SubjectPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SubjectMapper {
    List<SubjectPO> querySubjects(Map<String,Object> param);

    void addTeachSubject(@Param("staffId")String staffId, @Param("list")List<Integer> subjectList);

    List<SubjectPO> querySubjectList(@Param("title")String title);
}
