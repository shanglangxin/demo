package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.File;

public class ResultUtil {

    public static Result addResult(Object object){
        return new Result(0, object, "操作成功");
    }

    public static Result success(){
        return new Result(0, null, "操作成功");
    }

    public static Result fail(){
        return new Result(0, null, "操作失败");
    }



//    public static final String UPLOAD_FILE_URL = ConfigUtil.getValue("fileWeb") + "/Struts2/fileUploadAction";
//    public static final String ORG_ID = ConfigUtil.getValue("orgId");
//
//
//    /**
//     * 上传文件到文件服务器
//     *
//     * @param file        文件
//     * @param fileType    文件后缀，不带.
//     * @param isImage     是否图片文件
//     * @param type        媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
//     * @param newFileName 新的文件名称，如果文件名为空，随机产生文件名
//     * @return
//     */
//    public static String uploadFile(File file, String fileType, boolean isImage, String type, String newFileName) {
//        String returnStr = null;
//        if (file.exists()) {
//            try {
//                if (isImage) {
//                    returnStr = uploadImage(file, fileType, newFileName);
//                } else {
//                    //FilePart：用来上传文件的类
//                    FilePart fp = new FilePart("file", file);
//                    Part[] parts = {new StringPart("fileFileType", fileType), new StringPart("orgId", ORG_ID.replace("-", ""))
//                            , new StringPart("type", type), new StringPart("newFileName", newFileName), fp};
//                    returnStr = uploadFile(UPLOAD_FILE_URL + "!doUploadFile.action", parts);
//                }
//            } catch (Exception e) {
//                log.error("上传失败,{}", e);
//            }
//        }
//        return returnStr;
//    }
//
//
//    /**
//     * 上传文件工具接口
//     *
//     * @param url
//     * @param parts
//     * @return 返回数据
//     * @throws Exception          这是一个异常
//     * @throws NonePrintException 这是一个异常
//     */
//    private static String uploadFile(String url, Part[] parts) throws Exception {
//        PostMethod postMethod = null;
//        try {
//            postMethod = new PostMethod(url);
//            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//            //FilePart：用来上传文件的类
//            MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
//            postMethod.setRequestEntity(mre);
//            HttpClient client = new HttpClient();
//            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);// 设置连接时间
//            client.getHttpConnectionManager().getParams().setSoTimeout(1800000);// 读取数据超时时间
//            int status = client.executeMethod(postMethod);
//            if (status == HttpStatus.SC_OK) {
//                String returnJson = postMethod.getResponseBodyAsString();
//                if (!AssertUtil.isEmpty(returnJson)) {
//                    JSONObject toClientJson = JSONObject.parseObject(returnJson);
//                    String codeString = toClientJson.getString("code");
//                    //上传成功
//                    if ("0".equals(codeString)) {
//                        return toClientJson.getString("url");
//                    } else {
//                        throw new NonePrintException(codeString, toClientJson.getString("desc"));
//                    }
//                } else {
//                    throw new NonePrintException("4001", "接口返回为空");
//                }
//            } else {
//                throw new NonePrintException("4000", "接口返回" + status);
//            }
//        } finally {
//            //释放连接
//            if (postMethod != null) {
//                postMethod.abort();
//                postMethod.releaseConnection();
//            }
//        }
//    }


//    /**
//     * 上传图片接口
//     *
//     * @param file        文件
//     * @param fileType    文件后缀，不带.
//     * @param newFileName 新的文件名称，如果文件名为空，随机产生文件名
//     * @return 返回数据
//     * @throws Exception          这是一个异常
//     * @throws NonePrintException 这是一个异常
//     */
//    public static String uploadImage(File file, String fileType, String newFileName) throws Exception, NonePrintException {
//        if (file.exists()) {
//            FilePart fp = new FilePart("file", file);
//            Part[] parts = {new StringPart("fileFileType", fileType), new StringPart("orgId", ORG_ID.replace("-", "")),
//                    new StringPart("type", "image"), new StringPart("newFileName", newFileName), fp
//            };
//            return uploadFile(UPLOAD_FILE_URL + "!doUploadImage.action", parts);
//        } else {
//            throw new NonePrintException("4003", "文件为空");
//        }
//    }
//
//    public static boolean isImagesFile(String fileType) {
//        if (AssertUtil.isEmpty(fileType)) {
//            return false;
//        } else {
//            String[] fileTypes = new String[]{"jpg", "png", "gif", "bmp", "tif", "jpeg"};
//
//            for (int i = 0; i < fileTypes.length; ++i) {
//                if (fileType.equalsIgnoreCase(fileTypes[i])) {
//                    return true;
//                }
//            }
//
//            return false;
//        }
//    }
//
//
//    /**
//     * 推送文件到企业微信
//     *
//     * @param file
//     * @param wxUserId
//     */
//    @SneakyThrows
//    public static void sendFileToCP(@NonNull File file, @NonNull String wxUserId, @NonNull String agentCode) {
//        WxCpService wxCpService = WxCpConfig.getCpServices().get(agentCode);
//        WxMediaUploadResult upload = wxCpService.getMediaService().upload(WxConsts.MediaFileType.FILE, file);
//        if (AssertUtil.isNotEmpty(upload)) {
//            log.info("推送文件消息给用户{}", wxUserId);
//            wxCpService.messageSend(WxCpMessage.FILE().mediaId(upload.getMediaId()).toUser(wxUserId).build());
//        }
//    }


//    /**
//     * 统一错误信息
//     *
//     * @param code
//     * @param desc
//     * @return
//     */
//    public static Result fail(String code, String desc) {
//        Result result = new Result();
//        if (StringUtils.isEmpty(code)) {
//            result.setCode(FAIL_CODE);
//        } else {
//            result.setCode(code);
//        }
//        if (StringUtils.isEmpty(desc)) {
//            result.setDesc(FAIL_MSG);
//        } else {
//            result.setDesc(desc);
//        }
//        return result;
//    }
//
//    /**
//     * 正确返回数据
//     *
//     * @param desc
//     * @return
//     */
//    public static Result success(Object o, String desc) {
//        Result result = new Result();
//        result.setCode(SUCCESS_CODE);
//        result.setDesc(desc);
//        result.setData(o);
//        return result;
//    }
//
//    public static Result success(String desc) {
//        return success(null, desc);
//    }
//
//    public static Result success() {
//        return success(null, SUCCESS_MSG);
//    }
//
//    /**
//     * 返回表单校验的结果
//     *
//     * @param result
//     * @return
//     */
//    public static Result formValid(BindingResult result) {
//        String errorMsg = null;
//        for (FieldError fieldError : result.getFieldErrors()) {
//            if (StringUtils.isEmpty(errorMsg)) {
//                errorMsg = fieldError.getDefaultMessage();
//            } else {
//                errorMsg += "," + fieldError.getDefaultMessage();
//            }
//        }
//        return fail(FAIL_CODE, errorMsg);
//    }
//
//    public static Result initResult() {
//        Result result = new Result();
//        result.setCode(FAIL_CODE);
//        result.setDesc(FAIL_MSG);
//        result.setData(null);
//        return result;
//    }


}
