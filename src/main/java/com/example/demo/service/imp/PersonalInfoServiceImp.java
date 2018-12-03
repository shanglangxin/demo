package com.example.demo.service.imp;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.ClassMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.pojo.ClazzPO;
import com.example.demo.pojo.StudentPO;
import com.example.demo.service.IPersonalInfoService;
import com.example.demo.util.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("personalInfoService")
public class PersonalInfoServiceImp implements IPersonalInfoService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public void completeInfo(Map<String, Object> param) throws MyException {
        ClazzPO po = classMapper.queryClassById((Integer) param.get("classId"));
        if(po == null){
            throw new MyException(-1,"没有这个班级编号");
        }
        StudentPO student = studentMapper.queryStudentById(param.get("username"));
        if(student != null){
            throw new MyException(-1,"该账号信息已完善");
        }
        accountMapper.updatePersonalInfo(param);
        studentMapper.addStudent(param);
    }
}
