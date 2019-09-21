package com.common.web.controller;

import com.common.web.form.PageResponseForm;
import com.common.web.form.SimpleResponseForm;
import org.springframework.data.domain.Page;

/**
 * web层通用数据处理
 *
 * @author jjh
 * @date 2019/6/1
 **/
public class BaseController {


    /**
     * 返回成功结果
     * @param result 结果
     */
    public Object success(Object result){
        return SimpleResponseForm.success(result);
    }

    /**
     * 返回成功结果
     */
    public Object success(){
        return this.success("success");
    }

    /**
     * 返回失败结果
     * @param message 失败信息
     */
    public Object error(String message){
        return SimpleResponseForm.error(message);
    }

    public Object page(Page result) {
        PageResponseForm form = new PageResponseForm(result.getTotalElements(), result.getContent());
        return SimpleResponseForm.success(form);
    }


}
