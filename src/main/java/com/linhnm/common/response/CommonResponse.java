package com.linhnm.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse<T> {
    private String errorCode;
    private String errorType;
    private String message;
    private String traceId;
    private Long total;
    private Long limit;
    private Long page;
    private Map<String, Object> meta;
    private T data;

    public static <V> CommonResponse<V> of(V data) {
        CommonResponse<V> commonResponse = new CommonResponse<>();
        commonResponse.setData(data);
        return commonResponse;
    }

    public static <V> CommonResponse<List<V>> of(Page<V> page) {
        CommonResponse<List<V>> response = new CommonResponse<>();
        response.data = page.getContent();
        response.total = page.getTotalElements();
        response.limit = (long) page.getPageable().getPageSize();
        response.page = (long) page.getPageable().getPageNumber() + 1; // Page spring boot start with index 0

        return response;
    }
}
