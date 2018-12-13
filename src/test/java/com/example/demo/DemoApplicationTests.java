package com.example.demo;

import com.example.demo.controller.SpringController;
import com.example.demo.dto.OptionDTO;
import com.example.demo.dto.TestPaperQuestionDTO;
import com.example.demo.service.ITestPaperMgrService;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.MyException;
import com.example.demo.vo.QuestionDetailVO;
import com.example.demo.vo.TestPaperDetailVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private IQuestionMgrService questionMgrService;
	@Autowired
	private ITestPaperMgrService testPaperMgrService;


	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(new SpringController()).build();
	}

	@Test
	public 	void index() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/index").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(equalTo("Hello World")));
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void addQuestionTest(){
		Map<String, Object> param = new HashMap<>();
		param.put("id",null);
		param.put("title","dfdsfds");
		param.put("answer","dfds");
		param.put("creator", "dfsf");
		param.put("createTime", new java.util.Date());
		param.put("type",1);
		param.put("singleAnswer","dsfsdf");
		List<OptionDTO> list = new ArrayList<OptionDTO>();
		OptionDTO optionDTO = new OptionDTO();
		optionDTO.setName("fdsfsfds");
		optionDTO.setQuestionId(2);
		list.add(optionDTO);
		param.put("list",list);
//		questionMgrService.addQuestion(param);
		System.out.println("------------------------------------");
		System.out.println(param.get("id"));
		System.out.println("------------------------------------");
	}

	@Test
	public void testAddOrUpdate() throws MyException {
		Map<String, Object> param = new HashMap<>();
		param.put("id", 14);
		param.put("title", "wqeqwe");
		param.put("duration", 30);
		param.put("startTime", new Date());
		param.put("totalMark", 100);
		param.put("endTime", new Date());
		param.put("subjectId", 1);
		param.put("creator", "21321");
		param.put("createTime", new Date());

		List<TestPaperQuestionDTO> list = new ArrayList<>();
		List<OptionDTO> optionLIst = new ArrayList<>();
		OptionDTO optionDTO = new OptionDTO();
		optionDTO.setName("fdsfsfds");
		optionDTO.setQuestionId(2);
		optionLIst.add(optionDTO);
		optionLIst.add(optionDTO);

		TestPaperQuestionDTO dto1 = new TestPaperQuestionDTO();
		dto1.setTitle("21321321");
		dto1.setShowTitle("sdsadsadsa");
		dto1.setType(2);
		dto1.setSingleAnswer("2321321");
		dto1.setSubjectId(1);
		dto1.setSort(1);
		dto1.setOptionList(optionLIst);

		TestPaperQuestionDTO dto2 = new TestPaperQuestionDTO();
		dto2.setTitle("213213223323221");
		dto2.setShowTitle("sdsad323sadsa");
		dto2.setType(1);
		dto2.setSingleAnswer("2321321");
		dto2.setSubjectId(1);
		dto2.setSort(1);

		TestPaperQuestionDTO dto3 = new TestPaperQuestionDTO();
		dto3.setTitle("213213223323221");
		dto3.setShowTitle("sdsad323sadsa");
		dto3.setType(1);
		dto3.setSingleAnswer("2321321");
		dto3.setSubjectId(1);
		dto3.setSort(1);
		list.add(dto1);
		list.add(dto2);
		list.add(dto3);

		param.put("questionList", list);
		testPaperMgrService.addOrUpdatePaper(param);
	}

	@Test
	public void testAutoCreate() throws MyException {
		Map<String, Object> param = new HashMap<>();
		param.put("singleChoiceCount", 1);
		param.put("multiChoiceCount", 1);
		param.put("multiEntryCount",1);
		param.put("judgeCount", 1);
		param.put("completionCount", 1);
		param.put("subjectId", 1);
		List<QuestionDetailVO> list = testPaperMgrService.autoCreateQuestionList(param);
		Integer in = 1;
	}

	@Test
	public void getPaperDetail(){
		Integer id = 14;
		TestPaperDetailVO vo = testPaperMgrService.queryPaperDetail(14);
		Integer i = 1;
	}

}
