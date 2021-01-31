package com.jt.dubbo_jt_web.exception;

import com.jt.dubbo_jt_web.returnData.ReturnData;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ReturnData doHandleRuntimeException(RuntimeException e) {
        e.printStackTrace();

        ReturnData returnData = new ReturnData();
        if(e instanceof BadSqlGrammarException){
            returnData.setResultMessage("系统异常");
            returnData.setSuccess(false);
        }else if(e instanceof RuntimeException) {
            returnData.setResultMessage(e.getMessage());
            returnData.setSuccess(false);
        }else{
            returnData.setResultMessage("系统异常");
            returnData.setSuccess(false);
        }
        return returnData;
    }
}
