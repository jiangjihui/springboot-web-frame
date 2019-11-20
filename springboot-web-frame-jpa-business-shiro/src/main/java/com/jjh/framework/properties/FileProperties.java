package com.jjh.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件配置
 *
 * @author jjh
 * @date 2019/11/19
 **/
@Component
@ConfigurationProperties(prefix = "app.file")
public class FileProperties {

    /** 基础文件夹路径*/
    private static String basePath;

    /** 资源目录*/
    private static String resourceDir;

    /** 上传大小限制*/
    private static Integer maxUploadFileSize;


    public static String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        FileProperties.basePath = basePath;
    }

    public static String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        FileProperties.resourceDir = resourceDir;
    }

    public static Integer getMaxUploadFileSize() {
        return maxUploadFileSize;
    }

    public void setMaxUploadFileSize(Integer maxUploadFileSize) {
        FileProperties.maxUploadFileSize = maxUploadFileSize;
    }
}
