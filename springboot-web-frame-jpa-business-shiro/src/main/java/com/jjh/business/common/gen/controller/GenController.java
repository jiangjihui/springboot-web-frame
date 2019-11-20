package com.jjh.business.common.gen.controller;

import com.jjh.business.common.gen.controller.dto.GenEntityDTO;
import com.jjh.business.common.gen.service.GenService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.SimpleResponseForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码生成
 *
 * @author jjh
 * @date 2019/11/18
 **/
@Api(tags = "代码生成")
@RestController
@RequestMapping("/gen")
public class GenController extends BaseController {

    private static final Logger logger= LoggerFactory.getLogger(GenController.class);

    @Autowired
    private GenService genService;


    /**
     * 生成实体相关代码
     * @param dto 实体类信息
     */
    @ApiOperation("生成实体相关代码")
    @PostMapping("/gen_code")
    public SimpleResponseForm<String> genCode(GenEntityDTO dto) {
        dto.setAuthor("jjh");
        genService.generatorCodeForEntity(dto);
        return success();
    }


    /**
     * 生成指定目录下的实体相关代码
     * @param packagePath 指定的目录（包含到model包名）
     * @param author 作者
     */
    @ApiOperation(value = "生成指定目录下的实体相关代码", notes = "包含到实体所在包下的目录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "packagePath", value = "指定的目录（包含到model包名）", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "author", value = "作者", required = true, dataType = "String")
    })
    @GetMapping("/gen_code_to_target_path")
    public SimpleResponseForm<String> genCodeToTargetPath(String packagePath, String author) throws Exception {
        genService.genCodeFromTargetPath(packagePath, author);
        return success();
    }

}
