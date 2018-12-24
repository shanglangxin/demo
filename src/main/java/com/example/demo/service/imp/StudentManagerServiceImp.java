package com.example.demo.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.StudentPO;
import com.example.demo.service.IStudentManagerService;
import com.example.demo.util.AssertUtil;
import com.example.demo.util.MyException;

@Service("studentManagerService")
public class StudentManagerServiceImp implements IStudentManagerService {
	
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public List<StudentPO> queryStudentList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<StudentPO> list = studentMapper.queryStudentList(param);
		return list;
	}


	@Override
	public void addOrUpdateStudent(Map<String, Object> param) throws MyException {
		// TODO Auto-generated method stub
		Integer id = (Integer)param.get("id");
		StudentPO student = studentMapper.queryStudentById(id);
		if(!AssertUtil.isEmpty(student) && !student.getId().equals(id)){
			throw new MyException(-1, "该学生信息已存在");
		}
		if(AssertUtil.isEmpty(id)){
			studentMapper.addStudent(param);
			param.put("type", 3);
			accountMapper.addTestUser(param);
		}else{
			Account account = accountMapper.queryAccountByStaffId(student.getStaffId());
			studentMapper.updateStudentInfo(param);
			param.put("accountId", account.getId());
			accountMapper.updateTestUser(param);
		}
	}

	public void deleteStudent(List<Integer> ids){
		studentMapper.deleteStudent(ids);
	}
	
}
