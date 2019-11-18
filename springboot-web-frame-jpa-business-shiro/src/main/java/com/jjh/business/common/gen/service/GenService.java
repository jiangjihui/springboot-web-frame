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
     *
     * @param dto 实体类信息
     * @return 数据
     */
    void generatorCodeForEntity(GenEntityDTO dto);


}
