package com.feng.common.exception;

import com.feng.common.pojo.Result;
import com.feng.common.pojo.StatusCode;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice    //将这个类交给spring管理   对restcontroller经行增强  后置增强
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 抓取系统异常，未知错误
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        //e.printStackTrace();   //打印堆栈，一般项目上线就删除
        log.error(e.getMessage());

        // return  Result.errorMessage(e.getMessage());  打印系统错误
     return  Result.errorMessage("你的网络有问题");
    }


    /**
     * 用来抓取自定义异常   一般时自己的业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(LeadnewsException.class)
    public Result handlerLeadNewsException(LeadnewsException e){
        // e.printStackTrace();   //打印堆栈，一般项目上线就删除
        log.error(e.getMessage());
        //打印业务异常
        return Result.errorMessage(e.getMessage(), StatusCode.NOT_FOUND.code(),null);
    }
}
