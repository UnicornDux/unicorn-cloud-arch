package com.edu.code.web.exception;

import com.edu.code.base.result.IResultCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException{

    public IResultCode resultCode;

    public BizException(IResultCode code) {
       super(code.getMsg());
       this.resultCode = code;
    }

    public BizException(String message) {
        super(message);
    }
    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public  BizException(Throwable cause){
        super(cause);
    }

}
