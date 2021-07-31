package com.feng.common.exception;

import com.feng.common.pojo.StatusCode;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class LeadnewsException extends RuntimeException{
    private Integer code;   //异常返回码
    private String message; //异常信息

    public LeadnewsException() {
    }

    public LeadnewsException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public LeadnewsException(String message){
        //自定义的异常的状态
        this.code= StatusCode.CUSTOM_FAILURE.code();
        this.message=message;
    }

    @Override
    public String getMessage(){

        return message;
    }

}
