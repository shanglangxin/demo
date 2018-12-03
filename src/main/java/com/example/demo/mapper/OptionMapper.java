package com.example.demo.mapper;

import com.example.demo.dto.OptionDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OptionMapper {
    void addOptions(Map<String, Object> optionList);
}
