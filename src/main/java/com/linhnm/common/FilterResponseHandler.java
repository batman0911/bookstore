package com.linhnm.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linhnm.common.response.ErrorCode;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;

public class FilterResponseHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    private FilterResponseHandler() {}

    public static void returnError(ServletResponse response, ErrorCode errorCode, String message) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(errorCode.getHttpStatus());
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String res = message == null ? errorCode.getMessage() : message;
        mapper.writeValue(httpServletResponse.getOutputStream(), res);
    }
}
