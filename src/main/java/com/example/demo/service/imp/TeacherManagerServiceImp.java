package com.example.demo.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.pojo.Account;
import com.example.demo.pojo.TeacherPO;
import com.example.demo.service.ITeacherManagerService;
import com.example.demo.util.AssertUtil;
import com.example.demo.util.MyException;

@Service("teacherManagerService")
public class TeacherManagerServiceImp implements ITeacherManagerService {

	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	public List<TeacherPO> queryTeacherList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<TeacherPO> list = teacherMapper.queryTeacherList(param);
		return list;
	}

	@Override
	public void addOrUpdateTeacherInfo(Map<String, Object> param) throws MyException {
		// TODO Auto-generated method stub
		Integer id = (Integer)param.get("id");
		TeacherPO teacher = teacherMapper.queryTeacherById(id);
		if(!AssertUtil.isEmpty(teacher) && !teacher.getId().equals(id)){
			throw new MyException(-1, "该老师信息已存在");
		}
		if(AssertUtil.isEmpty(id)){
			teacherMapper.addTeacherInfo(param);
			// 添加用户
			param.put("type", 2);
			accountMapper.addTestUser(param);
		}else{
			Account account = accountMapper.queryAccountByStaffId(teacher.getStaffId());
			param.put("accountId", account.getId());
			teacherMapper.updateTeacherInfo(param);
			accountMapper.updateTestUser(param);
		}
	}

	@Override
	public void deleteTeacherInfo(List<Integer> ids) {
		// TODO Auto-generated method stub
		teacherMapper.deleteTeacherInfo(ids);
	}

}
