package com.jjh.business.common.file.controller;

import com.jjh.business.common.file.model.FileInfo;
import com.jjh.business.common.file.service.FileService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.SimpleResponseForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  文件处理
 *
 * @author jjh
 * @date 2019/11/19
 */
@Api(tags = "文件处理")
@Controller
@RequestMapping("/common")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param file 上传的文件
     */
    @ApiOperation(value = "文件上传",notes = "通用上传请求")
    @PostMapping("/upload")
    @ResponseBody
    public SimpleResponseForm<FileInfo> uploadFile(MultipartFile file) {
        FileInfo userFileInfo = fileService.uploadFile(file);
        return success(userFileInfo);
    }


    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @ApiOperation(value = "文件下载", notes = "通用下载请求，返回待下载文件的数据流")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileName", dataType = "String", required = true, value = "文件名称"),
            @ApiImplicitParam(paramType = "query", name = "delete", dataType = "Boolean", required = true, value = "是否删除")
    })
    @GetMapping("/download")
    public void downloadFile(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        fileService.downloadFile(fileName, delete, response, request);
    }
}
