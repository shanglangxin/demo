package com.example.demo.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.TeachClassDTO;
import com.example.demo.dto.TeachSubjectDTO;
import com.example.demo.dto.TestClassDTO;
import com.example.demo.mapper.ClassMapper;
import com.example.demo.mapper.SubjectMapper;
import com.example.demo.pojo.DepartmentPO;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.vo.TestClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.pojo.AccountPO;
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
	@Autowired
	private ClassMapper classMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	
	@Override
	public List<TeacherPO> queryTeacherList(Map<String, Object> param) {
		List<TeacherPO> list = teacherMapper.queryTeacherList(param);
		return list;
	}

	@Override
	public void addOrUpdateTeacherInfo(Map<String, Object> param) throws MyException {
		Integer id = (Integer)param.get("id");
		TeacherPO teacher = teacherMapper.queryTeacherByStaffId((String) param.get("staffId"));
		if(!AssertUtil.isEmpty(teacher) && !teacher.getId().equals(id)){
			throw new MyException(-1, "该老师信息已存在");
		}
		if(AssertUtil.isEmpty(id)){
			teacherMapper.addTeacherInfo(param);
			// 添加用户
			param.put("type", 2);
			accountMapper.addTestUser(param);
		}else{
			AccountPO account = accountMapper.queryAccountByStaffId(teacher.getStaffId());
			param.put("accountId", account.getId());
			teacherMapper.updateTeacherInfo(param);
			accountMapper.updateTestUser(param);
		}
	}

	@Override
	public void deleteTeacherInfo(List<String> ids) {
		teacherMapper.deleteTeacherInfo(ids);
		accountMapper.deleteAccountInfo(ids);
	}

	@Override
	public List<DepartmentPO> queryDepartmentList() {
		return classMapper.queryDepartmentList();
	}

	@Override
	public void addTeachClass(TeachClassDTO dto) throws MyException {
		List<TestClassVO> list = classMapper.queryTeachClass(dto.getStaffId());
		for(TestClassVO vo : list){
			for(TestClassDTO classDTO : dto.getClassList()){
				if(vo.getClassId().equals(classDTO.getClassId())){
					throw new MyException(-1, "该班级已存在");
				}
			}
		}
		classMapper.addTeachClass(dto.getStaffId(), dto.getClassList());
	}

	@Override
	public List<TestClassVO> queryTeachClass(String staffId) {
		List<TestClassVO> list = classMapper.queryTeachClass(staffId);
		return list;
	}

	@Override
	public void deleteTeachClass(TeachClassDTO dto) {
		classMapper.deleteTeachClass(dto.getStaffId(), dto.getClassList());
	}

	@Override
	public void addTeachSubject(TeachSubjectDTO dto) throws MyException {
		Map<String, Object> param = new HashMap<>();
		param.put("username", dto.getStaffId());
		List<SubjectPO> list = subjectMapper.querySubjects(param);
		for(SubjectPO subjectPO : list){
			for (Integer id : dto.getSubjectList()){
				if(id.equals(subjectPO.getId())){
					throw new MyException(-1,"任教科目已添加");
				}
			}
		}
		subjectMapper.addTeachSubject(dto.getStaffId(), dto.getSubjectList());
	}

	@Override
	public List<SubjectPO> querySubjectList(String title) {
		if(AssertUtil.isEmpty(title)){
			title = "";
		}
		List<SubjectPO> list = subjectMapper.querySubjectList(title);
		return list;
	}

}
