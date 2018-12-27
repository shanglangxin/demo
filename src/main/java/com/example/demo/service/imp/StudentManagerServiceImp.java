package com.example.demo.service.imp;

import java.util.List;
import java.util.Map;

import com.example.demo.mapper.ClassMapper;
import com.example.demo.pojo.ClazzPO;
import com.example.demo.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.pojo.AccountPO;
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
	@Autowired
	private ClassMapper classMapper;

	@Override
	public List<StudentVO> queryStudentList(Map<String, Object> param) {
		List<StudentVO> list = studentMapper.queryStudentList(param);
		return list;
	}


	@Override
	public void addOrUpdateStudent(Map<String, Object> param) throws MyException {
		Integer id = (Integer)param.get("id");
		StudentPO student = studentMapper.queryStudentByStaffId((String) param.get("staffId"));
		if(!AssertUtil.isEmpty(student) && !student.getId().equals(id)){
			throw new MyException(-1, "该学生信息已存在");
		}
		if(AssertUtil.isEmpty(id)){
			studentMapper.addStudent(param);
			param.put("type", 3);
			accountMapper.addTestUser(param);
		}else{
			AccountPO account = accountMapper.queryAccountByStaffId(student.getStaffId());
			studentMapper.updateStudentInfo(param);
			param.put("accountId", account.getId());
			accountMapper.updateTestUser(param);
		}
	}

	public void deleteStudent(List<String> ids){
		studentMapper.deleteStudent(ids);
		accountMapper.deleteAccountInfo(ids);
	}

	@Override
	public List<ClazzPO> queryDepartmentClass(Integer departmentId) {
		List<ClazzPO> list = classMapper.queryClassByDepartmentId(departmentId);
		return list;
	}

}
