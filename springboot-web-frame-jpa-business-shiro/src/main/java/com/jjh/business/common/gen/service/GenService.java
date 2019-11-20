package com.jjh.business.common.gen.service;

import com.jjh.business.common.gen.controller.dto.GenEntityDTO;

/**
 * GenService
 *
 * @author jjh
 * @date 2019/11/18
 **/
public interface GenService {

    /**
     * 生成实体相关代码
     * @param dto 实体类信息
     */
    void generatorCodeForEntity(GenEntityDTO dto);


    /**
     * 生成指定目录下的实体相关代码
     * @param packagePath 指定的目录（包含到model包名）
     * @param author 作者
     */
    void genCodeFromTargetPath(String packagePath, String author);
}
