package com.linhnm.exception;

import com.linhnm.common.response.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException {
    private String errorCode;
    private Integer httpStatus;
    private String message;
    private String errorType;
    private Object data;

    public CommonException() {}

    public CommonException(String errorCode) {
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public CommonException(ErrorCode errorCode, String args) {
        super(String.format(errorCode.getMessage(), args));
        this.message = String.format(errorCode.getMessage(), args);
        this.errorCode = errorCode.getCode();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public CommonException(String errorCode, String message) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
