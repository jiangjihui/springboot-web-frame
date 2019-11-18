package com.jjh.business.common.gen.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.jjh.business.common.gen.controller.dto.GenEntityDTO;
import com.jjh.business.common.gen.service.GenService;
import com.jjh.common.exception.BusinessException;
import com.jjh.framework.plugin.VelocityInitializer;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成 服务层处理
 *
 */
@Service
public class GenServiceImpl implements GenService {

    /**
     * 生成实体相关代码
     *
     * @param dto 实体类信息
     * @return 数据
     */
    @Override
    public void generatorCodeForEntity(GenEntityDTO dto) {
        String moduleName = dto.getModuleName();
        String className = dto.getClassName();
        String comment = dto.getComment();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        VelocityInitializer.initVelocity();

        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = dto.getPackageName();
        String classname = StringUtils.uncapitalize(className);
        velocityContext.put("tableComment", comment);
        velocityContext.put("className", className);
        velocityContext.put("classEntity", className);
        velocityContext.put("classname", classname);
        velocityContext.put("moduleName", moduleName);
        velocityContext.put("package", packageName + "." + classname);
        velocityContext.put("author", dto.getAuthor());
        velocityContext.put("datetime", DateUtil.formatDate(new Date()).replace("-","/"));

        // 生成压缩文件
        velocityContext(velocityContext, dto);

    }

    public static void velocityContext(VelocityContext context, GenEntityDTO dto) {
        // 获取模板列表
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/Service.java.vm");
        templates.add("vm/java/ServiceImpl.java.vm");
        templates.add("vm/java/Controller.java.vm");
        templates.add("vm/java/Repository.java.vm");

        // 渲染模板
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            FileWriter fileWriter = null;
            try {
                String fileName = getFileName(template, dto);
                FileUtil.mkParentDirs(fileName);
                fileWriter = new FileWriter(fileName);
                fileWriter.append(sw.toString());
                fileWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("渲染模板失败，类名：" + dto.getClassName(), e);
            }
            finally {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenEntityDTO dto)
    {
        String packageName = dto.getPackageName();
        String className = dto.getClassName();
        // 小写类名
        String classname = StringUtils.uncapitalize(className);
        // 大写类名
        String javaPath = "D://";

        if (StringUtils.isNotEmpty(classname))
        {
            javaPath += classname.replace(".", "/") + "/";
        }

        if (template.contains("domain.java.vm"))
        {
            return javaPath + "domain" + "/" + className + ".java";
        }

        if (template.contains("Service.java.vm"))
        {
            return javaPath + "service" + "/" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm"))
        {
            return javaPath + "service" + "/" + "impl" + "/" + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm"))
        {
            return javaPath + "controller" + "/" + className + "Controller.java";
        }

        if (template.contains("Repository.java.vm"))
        {
            return javaPath + "repository" + "/" + className + "Repository.java";
        }
        return null;
    }

}
