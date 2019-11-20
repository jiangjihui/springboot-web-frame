package com.jjh.business.common.file.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传
 * Content-Type=multipart/dto-data
 */
@ApiModel("文件上传表单")
public class UploadFileForm {

    @ApiModelProperty("文件")
    private MultipartFile file;
    @ApiModelProperty("文件名称")
    private String filename;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
