package com.jjh.business.common.gen.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jjh.business.common.gen.controller.form.GenEntityForm;
import com.jjh.business.common.gen.controller.form.GenTargetPathForm;
import com.jjh.business.common.gen.service.GenService;
import com.jjh.common.exception.BusinessException;
import com.jjh.framework.plugin.velocity.VelocityInitializer;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 代码生成 服务层处理
 *
 */
@Service
public class GenServiceImpl implements GenService {

    private static final Logger logger = LoggerFactory.getLogger(GenServiceImpl.class);

    /**
     * 生成实体相关代码
     *
     * @param dto 实体类信息
     * @return 数据
     */
    @Override
    public void generatorCodeForEntity(GenEntityForm dto) {
        String moduleName = dto.getModuleName();
        String ClassName = dto.getClassName();
        String comment = dto.getComment();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        VelocityInitializer.initVelocity();

        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = dto.getPackageName();
        String className = StringUtils.uncapitalize(ClassName);
        String classname = StrUtil.toUnderlineCase(ClassName);
        velocityContext.put("tableComment", comment);
        velocityContext.put("ClassName", ClassName);
        velocityContext.put("classEntity", ClassName);
        velocityContext.put("className", className);
        velocityContext.put("classname", classname);
        velocityContext.put("moduleName", moduleName);
        velocityContext.put("package", packageName);
        velocityContext.put("author", dto.getAuthor());
        velocityContext.put("datetime", DateUtil.formatDate(new Date()).replace("-","/"));

        // 生成压缩文件
        velocityApply(velocityContext, dto);

    }

    /**
     * 生成指定目录下的实体相关代码
     * @param form
     */
    @Override
    public void genCodeFromTargetPath(GenTargetPathForm form) {
        String packagePath = form.getPackagePath();
        String author = form.getAuthor();
        if (StrUtil.isBlank(packagePath)) {
            throw new BusinessException("目录不能为空");
        }
        // 获取各个类
        File[] modelFileArray = new File(packagePath).listFiles();
        String prefix = "src\\main\\java\\";
        String packageName = packagePath.substring(packagePath.lastIndexOf(prefix) + prefix.length(), packagePath.length()).replace("\\",".");

        try {
            for (File file : modelFileArray) {
                String className = FileUtil.mainName(file.getName());
                // 忽略排除项
                if (StrUtil.isNotBlank(form.getExcludeEntity()) && form.getExcludeEntity().contains(className)) {
                    continue;
                }
                String classPackageName = packageName + "." + className;
                Class<?> clazz = Class.forName(classPackageName);
                // 获取对应的注解（方便获取注解内的值）
                ApiModel apiModel = clazz.getAnnotation(ApiModel.class);

                GenEntityForm dto = new GenEntityForm();
                String packageParentName = packageName.substring(0, packageName.lastIndexOf("."));
                String moduleName = StrUtil.subAfter(packageName, "business.", false).replace(".", "/");
                moduleName = moduleName.substring(0, moduleName.lastIndexOf("/"));
                dto.setAuthor(author);
                dto.setModuleName(moduleName);
                dto.setClassName(className);
                dto.setPackageName(packageParentName);
                dto.setComment(apiModel.value());
                dto.setTargetPath(packagePath);

                logger.info(dto.toString());

                this.generatorCodeForEntity(dto);
            }
        } catch (Exception e) {
            throw new BusinessException("反射获取类信息异常", e);
        }
    }

    /**
     * 
     * @param context
     * @param dto
     */
    public static void velocityApply(VelocityContext context, GenEntityForm dto) {
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
    public static String getFileName(String template, GenEntityForm dto)
    {
        String packageName = dto.getPackageName();
        String className = dto.getClassName();
        // 小写类名
        String classname = StringUtils.uncapitalize(className);
        String javaPath = "D:\\";
        if (StrUtil.isNotBlank(dto.getTargetPath())) {
            javaPath = dto.getTargetPath().substring(0, dto.getTargetPath().lastIndexOf("\\") + 1);
        }

//        if (StringUtils.isNotEmpty(classname))
//        {
//            javaPath += classname.replace(".", "/") + "/";
//        }

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
