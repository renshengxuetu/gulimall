package com.rs.gulimall.product.exception;

import com.rs.common.exception.BizCodeEnum;
import com.rs.common.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller 层全局异常处理
 */
@RestControllerAdvice(basePackages = "com.rs.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach((item) -> {
            String message = item.getDefaultMessage();
            String field = item.getField();
            map.put(field, message );
        });
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMessage()).put("data", map);
    }

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e){
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMessage());
    }

}
