package com.example.demo;

import com.example.demo.controller.SpringController;
import com.example.demo.dto.OptionDTO;
import com.example.demo.service.IPaperMgrService;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.MyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private IPaperMgrService paperMgrService;

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
		param.put("approach", 0);
		param.put("singleChoiceCount", 1);
		param.put("multiChoiceCount", 1);
		param.put("multiEntryCount", 1);
		param.put("judgeCount", 1);
		param.put("completionCount", 1);
		param.put("subjectId", 1);
		paperMgrService.addOrUpdatePaper(param);
	}

}
