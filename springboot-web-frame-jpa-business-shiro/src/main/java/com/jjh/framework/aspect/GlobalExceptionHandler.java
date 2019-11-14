package com.jjh.framework.aspect;

import com.jjh.common.web.form.SimpleResponseForm;
import com.jjh.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局接口异常处理
 * https://blog.csdn.net/qiuqiu_qiuqiu123/article/details/78489619
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     *  校验错误拦截处理（比如@NotBlank等）
     *  https://blog.csdn.net/jytxioabai/article/details/83823486
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public SimpleResponseForm<Object> validationBodyException(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        StringBuilder msg = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p ->{
                FieldError fieldError = (FieldError) p;
                msg.append(fieldError.getDefaultMessage()).append(";");
//                logger.error("数据校验失败 : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+ "},errorMessage{"+fieldError.getDefaultMessage()+"}");
            });
        }
        return SimpleResponseForm.error(400, "请填写正确信息：" + msg.toString());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        if (e instanceof BusinessException) {
            return SimpleResponseForm.error(400, "业务异常："+e.getMessage());
        }
        logger.error("API Exception",e);
        return SimpleResponseForm.error(500,"服务器异常："+e.getMessage());
    }

}
