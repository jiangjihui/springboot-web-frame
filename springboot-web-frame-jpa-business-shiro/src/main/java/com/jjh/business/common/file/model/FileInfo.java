package com.jjh.business.common.file.model;

import com.jjh.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import static com.jjh.business.common.file.model.FileInfo.TABLE_NAME;


/**
 * 文件信息
 *
 * @author jjh
 * @date 2019/11/19
 */
@ApiModel("文件信息")
@Data
@Entity
@Table(name = TABLE_NAME, indexes = {@Index(columnList = "fileKey")})
public class FileInfo extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"file_info";

    /** 文件名称*/
    @ApiModelProperty("文件名称")
    @Column(length = 100)
    private String filename = "";

    /** 实际文件名*/
    @ApiModelProperty("实际文件名")
    @Column
    private String fileKey;

    /** 文件内容的md5摘要*/
    @ApiModelProperty("文件内容的md5摘要")
    @Column
    private String fileMd5 = "";

    /** 文件大小*/
    @ApiModelProperty("文件大小")
    @Column
    private long fileSize = 0L;

}
