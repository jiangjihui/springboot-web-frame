package com.jjh.business.common.gen.controller;

import com.jjh.business.common.gen.controller.dto.GenEntityDTO;
import com.jjh.business.common.gen.service.GenService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.SimpleResponseForm;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 *
 * @author jjh
 * @date 2019/11/18
 **/
@Api(tags = "代码生成")
@RestController
@RequestMapping("/gen")
public class GenController extends BaseController {

    @Autowired
    private GenService genService;


    @PostMapping("/gen_code")
    public SimpleResponseForm<String> genCode(GenEntityDTO dto) {
        dto.setAuthor("jjh");
        genService.generatorCodeForEntity(dto);
        return success();
    }

}
