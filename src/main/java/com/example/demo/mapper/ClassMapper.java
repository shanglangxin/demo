package com.example.demo.mapper;

import com.example.demo.pojo.ClazzPO;
import com.example.demo.pojo.DepartmentPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassMapper {
    ClazzPO queryClassById(Integer classId);

    List<ClazzPO> queryClassByDepartmentId(Integer departmentId);

    List<DepartmentPO> queryDepartmentList();
}
