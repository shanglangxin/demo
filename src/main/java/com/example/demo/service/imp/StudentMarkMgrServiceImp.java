package com.example.demo.service.imp;

import com.example.demo.dto.DeleteMarkDTO;
import com.example.demo.mapper.TestPaperStudentRefMapper;
import com.example.demo.service.IStudentMarkMgrService;
import com.example.demo.vo.StudentMarkVO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Service("studentMarkMgrService")
public class StudentMarkMgrServiceImp implements IStudentMarkMgrService {

    @Autowired
    private TestPaperStudentRefMapper testPaperStudentRefMapper;

    @Override
    public List<StudentMarkVO> queryStudentMarkList(Map<String, Object> param) {
        List<StudentMarkVO> list = testPaperStudentRefMapper.queryStudentMarkList(param);
        return list;
    }

    @Override
    public void exportStudentMarkList(Map<String, Object> param, HttpServletResponse response) throws IOException {
        List<StudentMarkVO> list = testPaperStudentRefMapper.queryStudentMarkList(param);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("考试成绩");
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("考试成绩");
        HSSFRow fieldRow = sheet.createRow(1);
        fieldRow.createCell(0).setCellValue("班级");
        fieldRow.createCell(1).setCellValue("学号");
        fieldRow.createCell(2).setCellValue("姓名");
        fieldRow.createCell(3).setCellValue("成绩");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        for(int i = 0; i<list.size(); i++){
            HSSFRow dataRow = sheet.createRow(i+2);
            StudentMarkVO markVO = list.get(i);
            dataRow.createCell(0).setCellValue(markVO.getClassName());
            dataRow.createCell(1).setCellValue(markVO.getStaffId());
            dataRow.createCell(2).setCellValue(markVO.getStudentName());
            String mark = markVO.getMark() == null ? "": String.valueOf(markVO.getMark());
            dataRow.createCell(3).setCellValue(mark);
        }
        FileOutputStream os = new FileOutputStream("C:\\Users\\admin\\Desktop\\studentMark.xls");
        workbook.write(os);
        os.close();
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment; filename=studentMark.xls");
        InputStream in = new FileInputStream(new File("C:\\Users\\admin\\Desktop\\studentMark.xls"));
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        in.close();
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }

    @Override
    public void batchDeleteStudentMark(List<DeleteMarkDTO> list) {
        testPaperStudentRefMapper.batchDeleteStudentMark(list);
    }
}
