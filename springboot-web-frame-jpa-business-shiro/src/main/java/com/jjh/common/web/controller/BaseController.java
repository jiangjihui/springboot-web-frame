package com.jjh.common.web.controller;

import com.jjh.common.web.form.PageResponseForm;
import com.jjh.common.web.form.SimpleResponseForm;
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
    public <T> SimpleResponseForm<T> success(T result){
        return new SimpleResponseForm<>(result);
    }

    /**
     * 返回成功结果
     */
    public <T> SimpleResponseForm<T> success(){
        return new SimpleResponseForm<>();
    }

    /**
     * 返回失败结果
     * @param message 失败信息
     */
    public SimpleResponseForm error(String message){
        return SimpleResponseForm.error(message);
    }

    public <T> SimpleResponseForm<T> page(Page<T> result) {
        PageResponseForm form = new PageResponseForm(result.getTotalElements(), result.getContent());
        return new SimpleResponseForm(form);
    }


}
