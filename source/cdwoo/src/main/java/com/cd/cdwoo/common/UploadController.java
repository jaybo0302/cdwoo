/**
 * File：UploadController.java
 * Package：com.cd.cdwoo.common
 * Author：chendong
 * Date：2016年12月12日 上午10:04:14
 * Copyright (C) 2003-2016 搜房资讯有限公司-版权所有
 */
package com.cd.cdwoo.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.cd.cdwoo.util.CDWooLogger;
import com.cd.cdwoo.util.ConvertSwf;
import com.cd.cdwoo.util.PropertiesReaderUtils;

/**
 * @author chendong
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
  
  @ResponseBody
  @RequestMapping("/uploadDocument")
  public CtResult uploadDocument(HttpServletRequest request, HttpServletResponse response) throws IOException{
    String fileRealPathDir = null;
    /** 构建文件保存的目录 **/
    //fileRealPathDir = PropertiesReaderUtils.getProperties(Constants.CONSTANTS_PATH, "documentUploadPath");
    fileRealPathDir = "D://uploadVideo";
    if (fileRealPathDir.equals("documentUploadPath")) {
      CDWooLogger.error("get documentUploadPath error!!");
      return CtResult.failure("get documentUploadPath error!!");
    }
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    /** 根据真实路径创建目录 **/
    File documentSaveFile = new File(fileRealPathDir);
    if (!documentSaveFile.exists()) {
      documentSaveFile.mkdirs();
    }
    /** 页面控件的文件流 **/
    MultipartFile multipartFile = multipartRequest.getFile("file");
    if (multipartFile == null || multipartFile.getSize() == 0) {
      return CtResult.failure("choose some file to upload");
    }
    /** 获取文件的后缀 **/
    String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
    /** 使用UUID生成文件名称 **/
    String fileName = UUID.randomUUID().toString() + suffix;
    /** 拼成完整的文件保存路径加文件 **/
    String filePath = fileRealPathDir + File.separator + fileName;
    CDWooLogger.info(fileName);
    CDWooLogger.info(filePath);
    File file = new File(filePath);
    multipartFile.transferTo(file);
    request.setAttribute("documentName", fileName);
    return CtResult.success(fileName);
  }
  
  @RequestMapping("getDocument")
  public String getDocument(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException{
    String documentName = request.getParameter("documentName");
    String fileRealPathDir = PropertiesReaderUtils.getProperties(Constants.CONSTANTS_PATH, "documentUploadPath");
    String toolPath = WebUtils.getRealPath(request.getServletContext(), "/tool");
    ConvertSwf swf = new ConvertSwf();
    String docSwfName = swf.beginConvert(fileRealPathDir, toolPath, documentName);
    request.setAttribute("documentName", documentName.replace(documentName.substring(documentName.indexOf(".")), ".swf"));
    CDWooLogger.info(WebUtils.getRealPath(request.getServletContext(), File.separator));
    return "pages/testFlex/view_document";
  }
}
