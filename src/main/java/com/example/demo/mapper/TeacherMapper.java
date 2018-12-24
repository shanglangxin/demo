package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.pojo.TeacherPO;

@Repository
public interface TeacherMapper {

	List<TeacherPO> queryTeacherList(Map<String, Object> param);

	TeacherPO queryTeacherById(Integer id);

	void addTeacherInfo(Map<String, Object> param);

	void updateTeacherInfo(Map<String, Object> param);

	void deleteTeacherInfo(List<Integer> ids);

}
