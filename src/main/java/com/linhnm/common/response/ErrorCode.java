package com.linhnm.common.response;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {
    INVALID_CREDENTIALS(400, "invalid_credentials", "Sai tên đăng nhập hoặc mật khẩu"),
    INVALID_ACCESS_TOKEN(400, "invalid_access_token", "Token không hợp lệ"),
    API_NOT_FOUND(404, "api_not_found", "api is not found"),
    INVALID_INPUT(400, "invalid_input", "Dữ liệu đầu vào không hợp lệ"),
    INVALID_INPUT_COMMON(400, "invalid_input", "%s"),
    INTERNAL_SERVER_ERROR(
            500,
            "internal_server_error",
            "Đã có lỗi xảy ra. Xin vui lòng thử lại sau hoặc liên hệ quản trị viên để được hỗ trợ."),
    DUPLICATE_ERROR(400, "duplicate_error", "%s"),
    ACCESS_DENIED(403, "access_denied", "Bạn không có quyền truy cập tài nguyên này"),
    INVALID_CONTEXT(403, "invalid_context", "Invalid signed device status"),
    ;

    private final Integer httpStatus;
    private final String code;
    private final String message;

    ErrorCode(Integer httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    private static final Map<String, ErrorCode> lookup = new HashMap<>();

    static {
        for (ErrorCode d : ErrorCode.values()) {
            lookup.put(d.code, d);
        }
    }

    public static ErrorCode get(String code) {
        return lookup.get(code);
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
