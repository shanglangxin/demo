package com.example.demo.service.imp;

import com.example.demo.mapper.*;
import com.example.demo.pojo.OptionPO;
import com.example.demo.pojo.SubjectPO;
import com.example.demo.service.IQuestionMgrService;
import com.example.demo.util.AssertUtil;
import com.example.demo.util.QuestionTypeUtil;
import com.example.demo.vo.ImportQuestionVO;
import com.example.demo.vo.QuestionDetailVO;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.util.*;

@Service("questionMgrService")
public class QuestionMgrServiceImp implements IQuestionMgrService {

    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    @Transactional
    public void addOrUpdateQuestion(Map<String, Object> param) {
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        this.handleQuestionAnswer(param);
        this.handleAnswer(param);
        Integer id = (Integer) param.get("id");
        if(param.get("id") == null){
            questionMapper.addQuestion(param);
        }else{
            questionMapper.updateQuestion(param);
            optionMapper.deleteOptionByQuestionId(param);
        }
        if(param.get("type").equals(QuestionTypeUtil.SINGLE_CHOICE_QUESTION) || param.get("type").equals(QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION)){
            optionMapper.addOptions(param);
        }
    }

    @Override
    public List<SubjectPO> querySubjects(Map<String, Object> param) {
        List<SubjectPO> list = new ArrayList<>();
        list = subjectMapper.querySubjects(param);
        return list;
    }

    @Override
    public List<QuestionDetailVO> queryQuestionList(Map<String, Object> param) {
        List<QuestionDetailVO> list = new ArrayList<>();
        Integer type = (Integer) param.get("type");
        if(type == null){
//            throw new MyException(-1,"选择查询题型");
        }
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        PageHelper.startPage((Integer) param.get("page"), 30);
        list = questionMapper.queryQuestion(param);
        if(type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION || type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
            for(QuestionDetailVO vo : list){
                List<OptionPO> optionList = optionMapper.queryOptionListByQuestionId(vo);
                vo.setOptionList(optionList);
            }
        }

        return list;
    }

    @Override
    @Transactional
    public void updateQuestion(Map<String, Object> param) {
        String table = queryQuestionTable((Integer) param.get("type"));
        param.put("table",table);
        this.handleQuestionAnswer(param);
        questionMapper.updateQuestion(param);
        if(param.get("type").equals(QuestionTypeUtil.SINGLE_CHOICE_QUESTION) || param.get("type").equals(QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION)){
            optionMapper.deleteOptionByQuestionId(param);
            optionMapper.addOptions(param);
        }
    }

    @Override
    public void deleteQuestion(Map<String, Object> param) {
        Integer type = (Integer) param.get("type");
        String table = this.queryQuestionTable(type);
        param.put("table", table);
        if(type.equals(QuestionTypeUtil.SINGLE_CHOICE_QUESTION) || type.equals(QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION)){
            optionMapper.deleteOptionByQuestionIds(param);
        }
        questionMapper.deleteQuestion(param);
    }

    @Override
    @Transactional
    public void importQuestionList(File file, String savePath) throws IOException {
        String[] titleTemp = new String[]{"题目标题","题目类型","选项（非选择题可不填，用“,”隔开）","答案（多选答案，用“,”隔开）", "科目"};
        String fileName = file.getName();
//        ExcelUtil.getExcelTitle(file,fileName);
        List<ImportQuestionVO> list = new ArrayList<>();
        InputStream in = new FileInputStream(file);
        if (fileName.endsWith("xlsx")){
            XSSFWorkbook workbook = new XSSFWorkbook(in);//Excel 2007
            list = readExcel(workbook);
        }else if (fileName.endsWith("xls")){
            HSSFWorkbook workbook = new HSSFWorkbook(in);//Excel 2003
            list = readExcel(workbook);
        }
        if(!AssertUtil.isEmpty(list)){
//            for(ImportQuestionVO questionVO : list){
//                questionVO.setTableName(queryQuestionTable(questionVO.getType()));
//            }
//            questionMapper.batchAddQuestions1(list);
            handleImportQuestionList(list);
            optionMapper.batchAddOptions(list);
        }
    }

    private String queryQuestionTable(Integer type){
        String tableName = "";
        if(type == QuestionTypeUtil.COMPLETION_QUESTION){
            tableName = "completion_question";
        }else if(type == QuestionTypeUtil.JUDGE_QUESTION){
            tableName = "judge_question";
        }else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
            tableName = "multiple_choice_question";
        }else if(type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
            tableName = "multiple_entry_question";
        }else if(type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
            tableName = "single_choice_question";
        }
        return tableName;
    }

    private void handleQuestionAnswer(Map<String, Object> param){
        Integer type = (Integer) param.get("type");
        if(type == QuestionTypeUtil.COMPLETION_QUESTION || type == QuestionTypeUtil.JUDGE_QUESTION || type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
            String answer = (String) param.get("singleAnswer");
            param.put("answer", answer);
        }else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION || type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
            List<String> multiple = (List<String>) param.get("multiAnswer");
            String answer = StringUtils.join(multiple, ',');
            param.put("answer", answer);
        }
    }
    
    private void handleAnswer(Map<String, Object> param){
    	Integer type = (Integer) param.get("type");
    	if(type == QuestionTypeUtil.COMPLETION_QUESTION){
            param.put("answer", param.get("singleAnswer"));
        }else if(type == QuestionTypeUtil.JUDGE_QUESTION){
        	param.put("answer", param.get("singleAnswer"));
        }else if(type == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
            List<String> list = (List<String>) param.get("multiAnswer");
            String answer = StringUtils.join(list,',');
            param.put("answer", answer);
        }else if(type == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
        	List<String> list = (List<String>) param.get("multiAnswer");
            String answer = StringUtils.join(list,',');
            param.put("answer", answer);
        }else if(type == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
        	param.put("answer", param.get("singleAnswer"));
        }
    }

    private List<ImportQuestionVO> readExcel(HSSFWorkbook workbook){
        List<SubjectPO> subjectList = subjectMapper.querySubjectList("");
        Map<String, Integer> subjectMap = new HashMap<>();
        for(SubjectPO subject : subjectList){
            subjectMap.put(subject.getName(), subject.getId());
        }
        List<ImportQuestionVO> list = new ArrayList<>();
        for(int sheetNum = 0; sheetNum<workbook.getNumberOfSheets(); sheetNum++){
            HSSFSheet sheet = workbook.getSheetAt(sheetNum);
            if(AssertUtil.isEmpty(sheet)){
                continue;
            }
            for(int rowNum = 0; rowNum<sheet.getLastRowNum(); rowNum++){
                ImportQuestionVO question = new ImportQuestionVO();
                HSSFRow row = sheet.getRow(rowNum+1);
                String questionTitle = row.getCell(0).getStringCellValue();
                Integer questionType = (int)row.getCell(1).getNumericCellValue();
                String questionOption = row.getCell(2).getStringCellValue();
                String questionAnswer = row.getCell(3).getStringCellValue();
                String questionSubject = row.getCell(4).getStringCellValue();
                question.setTitle(questionTitle);
                question.setAnswer(questionAnswer);
                question.setShowTitle("<p>"+questionTitle+"</p>");
                question.setType(Integer.valueOf(questionType));
                question.setOptionTitleList(Arrays.asList(questionOption.split(",")));
                question.setSubjectId(subjectMap.get(questionSubject));
                list.add(question);
            }
        }
        return list;
    }

    private List<ImportQuestionVO> readExcel(XSSFWorkbook workbook){
        ImportQuestionVO question = new ImportQuestionVO();
        List<SubjectPO> subjectList = subjectMapper.querySubjectList("");
        Map<String, Integer> subjectMap = new HashMap<>();
        for(SubjectPO subject : subjectList){
            subjectMap.put(subject.getName(), subject.getId());
        }
        List<ImportQuestionVO> list = new ArrayList<>();
        for(int sheetNum = 0; sheetNum<workbook.getNumberOfSheets(); sheetNum++){
            XSSFSheet sheet = workbook.getSheetAt(sheetNum);
            if(AssertUtil.isEmpty(sheet)){
                continue;
            }
            for(int rowNum = 0; rowNum<sheet.getLastRowNum(); rowNum++){
                XSSFRow row = sheet.getRow(rowNum+1);
                String questionTitle = row.getCell(0).getStringCellValue();
                Integer questionType = (int)row.getCell(1).getNumericCellValue();
                String questionOption = row.getCell(2).getStringCellValue();
                String questionAnswer = row.getCell(3).getStringCellValue();
                String questionSubject = row.getCell(4).getStringCellValue();
                question.setTitle(questionTitle);
                question.setAnswer(questionAnswer);
                question.setShowTitle("<p>"+questionTitle+"</p>");
                question.setType(Integer.valueOf(questionType));
                question.setOptionTitleList(Arrays.asList(questionOption.split(",")));
                question.setSubjectId(subjectMap.get(questionSubject));
                list.add(question);
            }
        }
        return list;
    }

    private void handleImportQuestionList(List<ImportQuestionVO> list){
        List<ImportQuestionVO> singleChoiceList = new ArrayList<>();
        List<ImportQuestionVO> multiChoiceList = new ArrayList<>();
        List<ImportQuestionVO> completionList = new ArrayList<>();
        List<ImportQuestionVO> judgeList = new ArrayList<>();
        List<ImportQuestionVO> multiEntryList = new ArrayList<>();
        for(ImportQuestionVO vo : list){
            if(vo.getType() == QuestionTypeUtil.MULTIPLE_ENTRY_QUESTION){
                multiEntryList.add(vo);
            }else if(vo.getType() == QuestionTypeUtil.MULTIPLE_CHOICE_QUESTION){
                multiChoiceList.add(vo);
            }else if(vo.getType() == QuestionTypeUtil.SINGLE_CHOICE_QUESTION){
                singleChoiceList.add(vo);
            }else if(vo.getType() == QuestionTypeUtil.COMPLETION_QUESTION){
                completionList.add(vo);
            }else if(vo.getType() == QuestionTypeUtil.JUDGE_QUESTION){
                judgeList.add(vo);
            }
        }
        if(!AssertUtil.isEmpty(completionList)){
            questionMapper.batchAddQuestions(completionList, "completion_question");
        }
        if(!AssertUtil.isEmpty(multiChoiceList)){
            questionMapper.batchAddQuestions(multiChoiceList, "multiple_choice_question");
        }
        if(!AssertUtil.isEmpty(multiEntryList)){
            questionMapper.batchAddQuestions(multiEntryList, "multiple_entry_question");
        }
        if(!AssertUtil.isEmpty(judgeList)){
            questionMapper.batchAddQuestions(judgeList, "judge_question");
        }
        if(!AssertUtil.isEmpty(singleChoiceList)){
            questionMapper.batchAddQuestions(singleChoiceList, "single_choice_question");
        }
    }

}
