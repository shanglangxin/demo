package com.example.demo.mapper;

import com.example.demo.pojo.ClazzPO;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassMapper {
    ClazzPO queryClassById(Integer classId);
}
