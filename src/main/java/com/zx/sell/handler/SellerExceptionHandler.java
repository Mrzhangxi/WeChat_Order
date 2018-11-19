package com.zx.sell.handler;

import com.zx.sell.ViewObject.ResultVO;
import com.zx.sell.exception.SellException;
import com.zx.sell.exception.SellerAuthorizeException;
import com.zx.sell.utils.ResultVOUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        return new ModelAndView("redirect:/tologin");
    }

    //处理SellException异常，保证先前端发送的数据的一致性
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtils.error(e.getCode(), e.getMessage());
    }
}
