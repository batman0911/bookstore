package com.linhnm.exception;

import brave.Span;
import brave.Tracer;
import com.linhnm.common.response.CommonResponse;
import com.linhnm.common.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private Tracer tracer;

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<?> handleRuntimeException(CommonException ex) {
        return createResponse(ex, ex.getMessage());
    }

    @ExceptionHandler(value = InputValidationException.class)
    public ResponseEntity<?> handleInputValidation(InputValidationException ex) {
        log.error("InputValidation", ex);
        CommonException commonException = new CommonException(ErrorCode.INVALID_INPUT.getCode());
        commonException.initCause(ex.getCause());
        return createResponse(commonException, null, ex.getError());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exc) {
        if (exc.getCause().getCause().getMessage().startsWith("Duplicate entry")) {
            log.error("DataIntegrityViolationException", exc);
            CommonException commonException = new CommonException(ErrorCode.DUPLICATE_ERROR.getCode());
            commonException.initCause(exc.getCause());
            return createResponse(commonException, exc.getMessage());
        }

        log.error("DataIntegrityViolationException", exc);
        CommonException commonException = new CommonException(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        commonException.initCause(exc.getCause());
        return createResponse(commonException, commonException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException(Exception exc) {
        log.error("AllException", exc);
        CommonException commonException = new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        commonException.initCause(exc.getCause());
        return createResponse(commonException, commonException.getMessage());
    }

    @SuppressWarnings("all")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("ArgumentNotValidException", exception);
        InputValidationException inputValidationException = new InputValidationException(exception.getBindingResult());
        CommonException commonException = new CommonException(ErrorCode.INVALID_INPUT.getCode());
        commonException.initCause(exception.getCause());
        return (ResponseEntity<Object>)
                createResponse(commonException, commonException.getMessage(), inputValidationException.getError());
    }

    private ResponseEntity<?> createResponse(CommonException commonException, String message) {
        return createResponse(commonException, message, commonException.getData());
    }

    private <T> ResponseEntity<?> createResponse(CommonException exception, String message, T data) {
        CommonResponse<T> responseObject = new CommonResponse<>();
        responseObject.setErrorCode(exception.getErrorCode());
        responseObject.setErrorType(exception.getErrorType());

        Span span = tracer.currentSpan();
        String traceId = span.context().traceIdString();
        responseObject.setTraceId(traceId);

        int httpStatus = 400;
        try {
            httpStatus = exception.getHttpStatus();
            if (message != null) {
                responseObject.setMessage(message);
            }
        } catch (Exception ex) {
            log.error("error: ", ex);
        }

        if (!StringUtils.isBlank(message)) {
            responseObject.setMessage(message);
        }

        if (data != null) {
            responseObject.setData(data);
        }

        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(httpStatus));
    }
}
