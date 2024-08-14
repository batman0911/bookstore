package com.linhnm.controller;

import com.linhnm.common.response.CommonResponse;
import com.linhnm.exception.TransactionLogNotFoundException;
import com.linhnm.model.query.FindTransactionLogsQuery;
import com.linhnm.model.request.TransactionLogRequest;
import com.linhnm.model.response.TransactionLogResponse;
import com.linhnm.service.TransactionLogService;
import com.linhnm.utils.AppConstants;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v${api.version}/transaction-logs")
@Slf4j
@RequiredArgsConstructor
class TransactionLogController {

    private final TransactionLogService transactionLogService;

    @GetMapping
    CommonResponse<List<TransactionLogResponse>> getAllTransactionLogList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        FindTransactionLogsQuery findTransactionLogsQuery =
                new FindTransactionLogsQuery(pageNo, pageSize, sortBy, sortDir);
        Page<TransactionLogResponse> transactionLogResponsePage =
                transactionLogService.findAllTransactionLogs(findTransactionLogsQuery);
        return CommonResponse.of(transactionLogResponsePage);
    }

    @GetMapping("/{id}")
    CommonResponse<TransactionLogResponse> getTransactionLogById(@PathVariable Long id) {
        return transactionLogService
                .findTransactionLogById(id)
                .map(CommonResponse::of)
                .orElseThrow(() -> new TransactionLogNotFoundException(id));
    }

    @PostMapping
    CommonResponse<TransactionLogResponse> createTransactionLog(
            @RequestBody @Validated TransactionLogRequest transactionLogRequest) {
        TransactionLogResponse response = transactionLogService.saveTransactionLog(transactionLogRequest);
        return CommonResponse.of(response);
    }

    @PutMapping("/{id}")
    CommonResponse<TransactionLogResponse> updateTransactionLog(
            @PathVariable Long id, @RequestBody @Valid TransactionLogRequest transactionLogRequest) {
        return CommonResponse.of(transactionLogService.updateTransactionLog(id, transactionLogRequest));
    }

    @DeleteMapping("/{id}")
    CommonResponse<TransactionLogResponse> deleteTransactionLog(@PathVariable Long id) {
        return transactionLogService
                .findTransactionLogById(id)
                .map(transactionLog -> {
                    transactionLogService.deleteTransactionLogById(id);
                    return CommonResponse.of(transactionLog);
                })
                .orElseThrow(() -> new TransactionLogNotFoundException(id));
    }
}
