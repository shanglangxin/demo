package com.example.demo.mapper;

import com.example.demo.dto.TestClassDTO;
import com.example.demo.pojo.ClazzPO;
import com.example.demo.pojo.DepartmentPO;
import com.example.demo.vo.TestClassVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassMapper {
    ClazzPO queryClassById(Integer classId);

    List<ClazzPO> queryClassByDepartmentId(Integer departmentId);

    List<DepartmentPO> queryDepartmentList();

    void addTeachClass(@Param("staffId") String staffId, @Param("list") List<TestClassDTO> classList);

    List<TestClassVO> queryTeachClass(String staffId);

    void deleteTeachClass(@Param("staffId") String staffId, @Param("list") List<TestClassDTO> classList);
}
