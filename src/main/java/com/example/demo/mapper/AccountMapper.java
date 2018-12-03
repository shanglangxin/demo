package com.example.demo.mapper;

import com.example.demo.dto.AccountDTO;
import com.example.demo.vo.AccountVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AccountMapper {


    void insert(AccountDTO dto);

    AccountVO queryAccount(AccountDTO dto);

    void updatePersonalInfo(Map<String,Object> param);
}
