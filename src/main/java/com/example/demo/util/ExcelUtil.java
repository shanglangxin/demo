package com.example.demo.util;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

@Slf4j
public class ExcelUtil {


        /**
     * 文件大小限制为10M
     */
    public static long fileMaxSize = 1024 * 1024 * 10;
    private static String UPLOAD_FILE_FAIL = "上传文件失败，请重试！";
    /**
     * 校验日期yyyy/MM/dd
     */
    public final static String checkDate1 = "^((([0-9]{4}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29))$";
    /**
     * 校验日期yyyy-MM-dd
     */
    public final static String checkDate2 = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$";

    /**
     * Excel导出方法
     *
     * @param header         表头
     * @param list           数据
     * @param clazz          数据类型
     * @param exportFileName excel名字
     */
    public static File exportForExcel(String[] header, List list, Class<?> clazz, String exportFileName) throws Exception {
        if (exportFileName == null || "".equals(exportFileName)) {
            exportFileName = "exportForExcel.xls";
        }
        File file = new File(exportFileName);
        FileOutputStream out = null;

        out = new FileOutputStream(file);

        HSSFWorkbook wb = createWB(header, list, clazz);
        wb.write(out);
        out.close();

        return file;

    }

        /**
         * @param header
         * @param list
         * @param clazz
         * @return
         * @throws NoSuchMethodException
         * @throws InstantiationException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @throws ParseException
         */
        public static HSSFWorkbook createWB(String[] header, List list, Class<?> clazz)
                throws NoSuchMethodException, InstantiationException,
                IllegalAccessException, InvocationTargetException, ParseException {
            return createWB(header, list, clazz, "Sheet1");
        }


        /**
         * @param header
         * @param list
         * @param clazz
         * @param wbName
         * @return
         * @throws NoSuchMethodException
         * @throws InstantiationException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @throws ParseException
         */
        public static HSSFWorkbook createWB(String[] header, List list, Class<?> clazz, String wbName)
                throws NoSuchMethodException, InstantiationException,
                IllegalAccessException, InvocationTargetException, ParseException {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(wbName);
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell;
            //设置单元格类型
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);//设置内容居左
            Map<Short, String> titleMap = new HashMap<Short, String>();
            //设置头信息
            for (short i = 0; i < header.length; i++) {
                cell = row.createCell(i);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(header[i]);
                titleMap.put(i, header[i]);
            }

            //得到目标类的所有可导入的字段列表
            Field field[] = clazz.getDeclaredFields();
            //将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
            Map<String, Method> fieldMap = new HashMap<String, Method>();
            Map<String,String> dateFormatMap = Maps.newConcurrentMap();
            // 循环读取所有字段
            for (int i = 0; i < field.length; i++) {
                // 得到单个字段上的Annotation
                Field f = field[i];
                if (f.isAnnotationPresent(TitleAnnotation.class)) {
                    TitleAnnotation title = f.getAnnotation(TitleAnnotation.class);
                    // 构造设置了Annotation的字段的Getter方法
                    String fieldname = f.getName();
                    String getMethodName = "get"
                            + fieldname.substring(0, 1).toUpperCase()
                            + fieldname.substring(1);
                    // 构造调用的method，
                    Method getMethod = clazz.getMethod(getMethodName);
                    // 将这个method以Annotation的名字为key来存入。
                    fieldMap.put(title.titleName(), getMethod);
                    if (f.isAnnotationPresent(JsonFormat.class)){
                        JsonFormat annotation = f.getAnnotation(JsonFormat.class);
                        dateFormatMap.put(title.titleName(),annotation.pattern());
                    }
                }

            }

            Object record;
            //遍历list
            for (int i = 0; i < list.size(); i++) {
                record = list.get(i);
                //第一行为标题，从第二行开始写入数据
                row = sheet.createRow(i + 1);
                for (short j = 0; j < header.length; j++) {
                    cell = row.createCell(j);
                    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置编码
                    cell.setCellStyle(style);    //设置style
                    String titleString = titleMap.get(j);
                    sheet.setColumnWidth(j, titleString.getBytes().length * 256 * 2);  //调整第j列宽度
                    if (fieldMap.containsKey(titleString)) {
                        Method getMethod = fieldMap.get(titleString);
                        Object value = getMethod.invoke(record);
                        //根据value类型选择不同的类型转换方法
                        if (value instanceof String) {
                            cell.setCellValue(value.toString());
                        } else if (value instanceof Double) {
                            cell.setCellValue((Double) value);
                        } else if (value instanceof BigDecimal) {
                            cell.setCellValue(value.toString());
                        } else if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value instanceof Float) {
                            cell.setCellValue((Float) value);
                        } else if (value instanceof Long) {
                            cell.setCellValue((Long) value);
                        } else if (value instanceof Boolean) {
                            cell.setCellValue((Boolean) value);
                        } else if (value instanceof java.util.Date | value instanceof java.sql.Date) {
                            if (dateFormatMap.containsKey(titleString)){
                                cell.setCellValue(DateUtil.format((Date)value,dateFormatMap.get(titleString)));
                            }else {
                                cell.setCellValue(DateUtil.formatDateTime((Date) value));
                            }
                        }
                    }
                }
            }
            return wb;
        }

        /**
         * 获取excel数据列表
         *
         * @param file     excel文件
         * @param fileName 文件名称
         * @param clazz    获取excel列表VO
         * @return the list
         * @throws Exception the exception
         */
        public static List importForExcel(File file, String fileName, Class<?> clazz)
                throws Exception {
            List<Object> targetList = new ArrayList<Object>();

            //判断是否传入了文件
            if (file == null) {
                throw new Exception("传入了一个空文件");
            }
            //判断文件格式
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                throw new Exception("文件格式不对");
            }
            //判断文件大小
            if (file.length() > fileMaxSize) {
                throw new Exception("文件太大");
            }
            //获取目标类所有可导入的字段列表
            Field[] field = clazz.getDeclaredFields();
            Map<String, Method> fieldMap = new HashMap<String, Method>();
            for (int i = 0; i < field.length; i++) {
                Field f = field[i];
                TitleAnnotation title = f.getAnnotation(TitleAnnotation.class);
                if (title != null) {
                    String fieldname = f.getName();
                    String setMethodName = "set"
                            + fieldname.substring(0, 1).toUpperCase()
                            + fieldname.substring(1);
                    //构造setter方法
                    Method setMethod = clazz.getMethod(setMethodName, new Class[]{f.getType()});
                    fieldMap.put(title.titleName(), setMethod);
                }
            }
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                //处理excel2003
                if (fileName.endsWith(".xls")) {
                    importExcel2003(targetList, fis, clazz, fieldMap);
                }
                //处理excel2007
                if (fileName.endsWith(".xlsx")) {
                    importExcel2007(targetList, fis, fieldMap, clazz);
                }
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
            return targetList;

        }

        /**
         * excel 2007 获取数据
         *
         * @param targetList 获取目标list
         * @param fis        文件流
         * @param fieldMap   导入数据标题map
         * @param clazz      获取excel列表VO
         * @throws IOException               the io exception
         * @throws InstantiationException    the instantiation exception
         * @throws IllegalAccessException    the illegal access exception
         * @throws IllegalArgumentException  the illegal argument exception
         * @throws InvocationTargetException the invocation target exception
         * @throws ParseException            the parse exception
         */
        private static void importExcel2007(List<Object> targetList, FileInputStream fis, Map<String, Method> fieldMap,
                                            Class<?> clazz) throws IOException,
                InstantiationException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException, ParseException {
            Map<Short, String> titleMap = new HashMap<Short, String>();
            //把一张xlsx的数据表读到wb里
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
            XSSFSheet sheet = wb.getSheetAt(0);
            //需要处理的列数
            int col = 0;
            //遍历所有行
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                //取第i行数据
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    //该行包含的单元格数既为列数
                    if (i == 0) {
                        col = row.getPhysicalNumberOfCells();
                        for (short j = 0; j < col; j++) {
                            XSSFCell titleCell = row.getCell(j);
                            titleMap.put(j, titleCell.getStringCellValue());
                        }
                        continue;
                    }
                    //新建clazz的实例，将每一行的数据写入此实例
                    Object tObject = clazz.newInstance();
                    //遍历第i行的列
                    for (short j = 0; j < col; j++) {
                        //得到第j列标题
                        String titleString = (String) titleMap.get(j);
                        boolean boo = true;
                        if (!fieldMap.containsKey(titleString)) {
                            boo = false;//如果该列不是默认模板的，就看该列是不是属于自定义字段的
                        }
                        //如果此列是需要的列，则继续操作
                        if (boo) {
                            //读取cell内容转换为字符串value
                            XSSFCell cell = row.getCell(j);
                            if (cell != null) {
                                String value = "";
                                switch (cell.getCellTypeEnum()) {
                                    //cell类型为字符串
                                    case STRING:
                                        value = cell.getStringCellValue();
                                        break;
                                    //cell类型为数字
                                    case NUMERIC:    //数值
                                        if (!org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                            //带中文的时间无法被识别为时间，特殊处理
                                            if ("阳历生日".equals(titleString) || "入职时间".equals(titleString)) {
                                                double d = cell.getNumericCellValue();
                                                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(d);
                                                value = date.toString();
                                            } else {
                                                cell.setCellType(STRING);
                                                String temp = cell.getStringCellValue();
                                                //判断是否包含小数点，如果不含小数点，则已字符串读取；如果含小数点，则转换为Double类型字符串
                                                if (temp.indexOf(".") > -1) {
                                                    value = String.valueOf(new Double(temp)).trim();
                                                } else {
                                                    value = temp.trim();
                                                }
                                            }
                                        } else {
                                            value = String.valueOf(cell.getDateCellValue());
                                        }
                                        break;
                                    //cell类型为boolean型
                                    case BOOLEAN:
                                        value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                                        break;
                                    // 导入时如果为公式生成的数据
                                    case FORMULA:
                                        try {
                                            value = String.valueOf(cell.getNumericCellValue());
                                        } catch (IllegalStateException e) {
                                            value = String.valueOf(cell.getRichStringCellValue());
                                        }
                                        break;
                                    case BLANK:
                                        value = "";
                                        break;
                                    case ERROR:
                                        value = "";
                                        break;
                                    default:
                                        value = "";
                                }
                                if ("".equals(value))
                                    continue;
                                //获取setter方法的参数类型，选择对应的方法将value放入tObject
                                Method setMethod;
                                String xclass;
                                Type[] ts;
                                setMethod = (Method) fieldMap.get(titleString);
                                ts = setMethod.getGenericParameterTypes();
                                xclass = ts[0].toString();
                                if (xclass.equals("class java.lang.String")) {
                                    setMethod.invoke(tObject, value);
                                } else if (xclass.equals("class java.util.Date")) {
                                    try {
                                        Date date = parseGreen(value);
                                        setMethod.invoke(tObject, date);
                                    } catch (ParseException e) {
                                        //maquanyang 2015-7-16 修改不是正确的时间格式时，没有错误提示
                                        //日期格式严格要求符合yyyy-MM-dd或yyyy/MMM/dd
                                        Date date = null;
                                        try {
                                            if (value.matches(checkDate2)) {//符合yyyy-MM-dd
                                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                date = format.parse(value + " 00:00:00");
                                                setMethod.invoke(tObject, date);
                                            } else if (value.matches(checkDate1)) {
                                                DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                                date = format.parse(value + " 00:00:00");
                                                setMethod.invoke(tObject, date);
                                            } else {
                                                setMethod = (Method) fieldMap.get(titleString + "(导入的数据)");
                                                setMethod.invoke(tObject, value);
                                            }
                                        } catch (ParseException e1) {
                                            setMethod = (Method) fieldMap.get(titleString + "(导入的数据)");
                                            setMethod.invoke(tObject, value);
                                        } catch (IllegalArgumentException e2) {
                                            setMethod = (Method) fieldMap.get(titleString + "(导入的数据)");
                                            setMethod.invoke(tObject, value);
                                        }
                                    }
                                } else if (xclass.equals("class java.lang.Boolean")) {
                                    Boolean boolname = true;
                                    if (value.equals("N")) {
                                        boolname = false;
                                    }
                                    setMethod.invoke(tObject, boolname);
                                } else if (xclass.equals("class java.lang.Integer")) {
                                    double d = Double.parseDouble(value);
                                    setMethod.invoke(tObject, (int) d);
                                } else if (xclass.equals("class java.lang.Long")) {
                                    setMethod.invoke(tObject, new Long(value));
                                } else if (xclass.equals("class java.lang.Float")) {
                                    Float d = Float.parseFloat(value);
                                    setMethod.invoke(tObject, d);
                                } else if (xclass.equals("class java.lang.Double")) {
                                    double d = Double.parseDouble(value);
                                    setMethod.invoke(tObject, d);
                                }
                            }
                        }
                    }
                    targetList.add(tObject);
                }
            }
        }

        /**
         * excel 2003 获取数据
         *
         * @param targetList 获取数据目标list
         * @param fis        文件流
         * @param fieldMap   导入数据标题
         * @param clazz      导入数据vo
         * @throws IOException               the io exception
         * @throws InstantiationException    the instantiation exception
         * @throws IllegalAccessException    the illegal access exception
         * @throws IllegalArgumentException  the illegal argument exception
         * @throws InvocationTargetException the invocation target exception
         * @throws ParseException            the parse exception
         */
        private static void importExcel2003(List<Object> targetList, FileInputStream fis,
                                            Class<?> clazz, Map<String, Method> fieldMap) throws IOException,
                InstantiationException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException, ParseException {
            Map<Short, String> titleMap = new HashMap<Short, String>();//Excel标题map
            //把一张xls的数据表读到wb里
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
            HSSFSheet sheet = wb.getSheetAt(0);

            //需要读取的列数
            int col = 0;
            //遍历所有行
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                //取第i行数据
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    if (i == 0) { //Excel标题行
                        col = row.getPhysicalNumberOfCells();
                        for (short j = 0; j < col; j++) {
                            HSSFCell titleCell = row.getCell(j);
                            titleMap.put(j, titleCell.getStringCellValue());
                        }
                        continue;
                    }
                    Object tObject = clazz.newInstance(); //新建clazz的实例，将每一行的数据写入此实例
                    for (short j = 0; j < col; j++) {//遍历第i行的列
                        String titleString = (String) titleMap.get(j); //得到第j列标题
                        boolean boo = true;
                        if (!fieldMap.containsKey(titleString)) {
                            boo = false;//如果该列不是默认模板的，就看该列是不是属于自定义字段的
                        }
                        //如果此列是需要的列，则继续操作
                        if (boo) {
                            HSSFCell cell = row.getCell(j);//读取cell内容转换为字符串value
                            if (cell != null) {
                                String value = "";
                                switch (cell.getCellTypeEnum()) {
                                    case STRING: //cell类型为字符串
                                        value = cell.getStringCellValue();
                                        break;
                                    case NUMERIC: //cell类型为数字（包含时间类型）
                                        if (!org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                            if ("阳历生日".equals(titleString) || "入职时间".equals(titleString)) { //带中文的时间无法被识别为时间，特殊处理
                                                double d = cell.getNumericCellValue();
                                                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(d);
                                                value = date.toString();
                                            } else {
                                                cell.setCellType(STRING);
                                                String temp = cell.getStringCellValue();
                                                if (temp.indexOf(".") > -1) { //判断是否包含小数点，如果不含小数点，则已字符串读取；如果含小数点，则转换为Double类型字符串
                                                    value = String.valueOf(new Double(temp)).trim();
                                                } else {
                                                    value = temp.trim();
                                                }
                                            }
                                        } else {
                                            value = String.valueOf(cell.getDateCellValue());
                                        }
                                        break;
                                    case BOOLEAN: //cell类型为boolean型
                                        value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                                        break;
                                    case FORMULA: // 导入时如果为公式生成的数据
                                        try {
                                            value = String.valueOf(cell.getNumericCellValue());
                                        } catch (IllegalStateException e) {
                                            value = String.valueOf(cell.getRichStringCellValue());
                                        }
                                        break;
                                    case BLANK:
                                        value = "";
                                        break;
                                    case ERROR:
                                        value = "";
                                        break;
                                    default:
                                        value = "";
                                }
                                if ("".equals(value))
                                    continue;
                                //获取setter方法的参数类型，选择对应的方法将value放入tObject
                                Method setMethod;
                                String xclass;
                                Type[] ts;
                                setMethod = (Method) fieldMap.get(titleString);
                                ts = setMethod.getGenericParameterTypes();
                                xclass = ts[0].toString();
                                if (xclass.equals("class java.lang.String")) {
                                    setMethod.invoke(tObject, value);
                                } else if (xclass.equals("class java.util.Date")) {
                                    try {
                                        Date date = parseGreen(value);
                                        setMethod.invoke(tObject, date);
                                    } catch (ParseException e) {
                                        //maquanyang 2015-7-16 修改不是正确的时间格式时，没有错误提示
                                        //日期格式严格要求符合yyyy-MM-dd或yyyy/MMM/dd
                                        Date date = null;
                                        try {
                                            if (value.matches(checkDate2)) {//符合yyyy-MM-dd
                                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                date = format.parse(value + " 00:00:00");
                                                setMethod.invoke(tObject, date);
                                            } else if (value.matches(checkDate1)) {
                                                DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                                date = format.parse(value + " 00:00:00");
                                                setMethod.invoke(tObject, date);
                                            } else {
                                                setMethod = (Method) fieldMap.get(titleString + "(导入的数据)");
                                                setMethod.invoke(tObject, value);
                                            }
                                        } catch (ParseException e1) {
                                            setMethod = (Method) fieldMap.get(titleString + "(导入的数据)");
                                            setMethod.invoke(tObject, value);
                                        } catch (IllegalArgumentException e2) {
                                            setMethod = (Method) fieldMap.get(titleString + "(导入的数据)");
                                            setMethod.invoke(tObject, value);
                                        }
                                    }
                                } else if (xclass.equals("class java.lang.Boolean")) {
                                    Boolean boolname = true;
                                    if (value.equals("N")) {
                                        boolname = false;
                                    }
                                    setMethod.invoke(tObject, boolname);
                                } else if (xclass.equals("class java.lang.Integer")) {
                                    setMethod.invoke(tObject, new Integer(value));
                                } else if (xclass.equals("class java.lang.Long")) {
                                    setMethod.invoke(tObject, new Long(value));
                                } else if (xclass.equals("class java.lang.Float")) {
                                    Float d = Float.parseFloat(value);
                                    setMethod.invoke(tObject, d);
                                } else if (xclass.equals("class java.lang.Double")) {
                                    double d = Double.parseDouble(value);
                                    setMethod.invoke(tObject, d);
                                }
                            }
                        }
                    }
                    targetList.add(tObject);
                }
            }
        }

        /**
         * Parse string to date date.
         *
         * @param date the date
         * @return the date
         * @throws ParseException the parse exception
         */
        public static Date parseStringToDate(String date) throws ParseException {
            Date result = null;
            String parse = date;
            parse = parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
            parse = parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1MM$2");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
            parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9])", "$1HH$2");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1mm$2");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");

            DateFormat format = new SimpleDateFormat(parse);

            result = format.parse(date);
            return result;
        }

        /**
         * Parse green date.
         *
         * @param date the date
         * @return the date
         * @throws ParseException the parse exception
         */
        public static Date parseGreen(String date) throws ParseException {
            SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            return f.parse(date);
        }

        public static String [] getExcelTitle(File file,String fileName){

            String [] titleArry = null;

            try {
                //判断是否传入了文件
                if(file==null){
                    throw new Exception("传入了一个空文件");
                }
                //判断文件格式
                if(!fileName.endsWith(".xls")&&!fileName.endsWith(".xlsx")){
                    throw new Exception("文件格式不对");
                }
                //判断文件大小
                if(file.length()>fileMaxSize){
                    throw new Exception("文件太大");
                }
                @Cleanup FileInputStream fis = new FileInputStream(file);
                //处理excel2003
                if(fileName.endsWith(".xls")){
                    //把一张xls的数据表读到wb里
                    HSSFWorkbook wb = new HSSFWorkbook(fis);
                    //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
                    HSSFSheet sheet = wb.getSheetAt(0);

                    //需要读取的列数
                    int col= 0;
                    //取标题行数据
                    HSSFRow row=sheet.getRow(0);
                    col = row.getPhysicalNumberOfCells();
                    titleArry = new String[col];
                    for(short j=0;j<col;j++){
                        HSSFCell titleCell=row.getCell(j);
                        titleArry[j] = titleCell.getStringCellValue();
                    }
                }
                //处理excel2007
                if(fileName.endsWith(".xlsx")){
                    XSSFWorkbook wb = new XSSFWorkbook(fis);
                    //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
                    XSSFSheet sheet = wb.getSheetAt(0);
                    //需要读取的列数
                    int col= 0;
                    //取标题行数据
                    XSSFRow row=sheet.getRow(0);
                    col = row.getPhysicalNumberOfCells();
                    titleArry = new String[col];
                    for(short j=0;j<col;j++){
                        XSSFCell titleCell=row.getCell(j);
                        titleArry[j] = titleCell.getStringCellValue();
                    }
                }
            } catch (FileNotFoundException e) {
                log.error("找不到文件,",e);
            } catch (IOException e) {
                log.error("获取excel标题错误",e);
            } catch (Exception e) {
                log.error("获取excel标题错误",e);
            }


            return titleArry;
        }

    /**
     * 校验导入模板
     * @param title
     * @param titleTemplate
     * @param resultVo
     * @return
     */
    public static boolean judgeTitleIsTrue(String[] title,String[] titleTemplate, ImportResultVO resultVo) {
        // 判断表头是否一致
        if (AssertUtil.isEmpty(title) || titleTemplate.length != title.length) {
            resultVo.setTips("你的模板出现了问题，请使用最新的模板导入数据。");
            resultVo.setFinish(true);
            return false;
        }
        for (int i = 0; i < titleTemplate.length; i++) {
            if (!title[i].equals(titleTemplate[i])) {
                resultVo.setTips("你的模板出现了问题，请使用最新的模板导入数据。");
                resultVo.setFinish(true);
                return false;
            }
        }
        return true;
    }

    /**
     * 处理上传文件
     * @param path
     * @param file
     * @throws IOException
     */
    public static void handleUploadFile(String path, CommonsMultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            FileOutputStream fos = new FileOutputStream(path + File.separator + file.getOriginalFilename());
            InputStream in = file.getInputStream();
            int b = 0;
            while ((b = in.read()) != -1) {
                fos.write(b);
            }
            fos.flush();
            fos.close();
            in.close();
        }
    }

    /**
     * service 方法
     */
//    public String validImportTaskSaleData(ImportTaskSalesVO importVo, Map<String, Object> taskSettingSalesMap) {
//        String msg = null;
//        TbQyUserInfoPOExample example = new TbQyUserInfoPOExample();
//        example.or().andWxUserIdEqualTo(importVo.getWxUserId());
//        List<TbQyUserInfoPO> list = userInfoMapper.selectByExample(example);
//        if (AssertUtil.isEmpty(importVo.getWxUserId())) {
//            msg = "工号不能为空";
//            return msg;
//        }
//        if (AssertUtil.isEmpty(importVo.getSalemanName())) {
//            msg = "用户不能为空";
//            return msg;
//        }
//        if (taskSettingSalesMap.containsKey(importVo.getWxUserId()+importVo.getSalemanName())) {
//            msg = "添加用户不能相同";
//            return msg;
//        }
//        if(AssertUtil.isEmpty(list)){
//            msg = "该工号不存在";
//            return msg;
//        }
//        String str = list.get(0).getWxUserId()+list.get(0).getPersonName();
//        if(!str.equals(importVo.getWxUserId()+importVo.getSalemanName())){
//            msg = "录入信息错误";
//            return  msg;
//        }
//        return msg;
//    }

}
