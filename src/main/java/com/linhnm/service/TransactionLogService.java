package com.linhnm.service;

import com.linhnm.entity.TransactionLogEntity;
import com.linhnm.exception.TransactionLogNotFoundException;
import com.linhnm.mapper.TransactionLogMapper;
import com.linhnm.model.query.FindTransactionLogsQuery;
import com.linhnm.model.request.TransactionLogRequest;
import com.linhnm.model.response.TransactionLogResponse;
import com.linhnm.repository.TransactionLogRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;
    private final TransactionLogMapper transactionLogMapper;

    public Page<TransactionLogResponse> findAllTransactionLogs(FindTransactionLogsQuery findTransactionLogsQuery) {

        // create Pageable instance
        Pageable pageable = createPageable(findTransactionLogsQuery);

        Page<TransactionLogEntity> transactionLogsPage = transactionLogRepository.findAll(pageable);

        return transactionLogsPage.map(transactionLogMapper::toResponse);
    }

    private Pageable createPageable(FindTransactionLogsQuery findTransactionLogsQuery) {
        int pageNo = Math.max(findTransactionLogsQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findTransactionLogsQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findTransactionLogsQuery.sortBy())
                        : Sort.Order.desc(findTransactionLogsQuery.sortBy()));
        return PageRequest.of(pageNo, findTransactionLogsQuery.pageSize(), sort);
    }

    public Optional<TransactionLogResponse> findTransactionLogById(Long id) {
        return transactionLogRepository.findById(id).map(transactionLogMapper::toResponse);
    }

    @Transactional
    public TransactionLogResponse saveTransactionLog(TransactionLogRequest transactionLogRequest) {
        TransactionLogEntity transactionLog = transactionLogMapper.toEntity(transactionLogRequest);
        TransactionLogEntity savedTransactionLog = transactionLogRepository.save(transactionLog);
        return transactionLogMapper.toResponse(savedTransactionLog);
    }

    @Transactional
    public TransactionLogResponse updateTransactionLog(Long id, TransactionLogRequest transactionLogRequest) {
        TransactionLogEntity transactionLogEntity =
                transactionLogRepository.findById(id).orElseThrow(() -> new TransactionLogNotFoundException(id));

        // Update the transactionLog object with data from transactionLogRequest
        transactionLogMapper.mapTransactionLogWithRequest(transactionLogEntity, transactionLogRequest);

        // Save the updated transactionLog object
        TransactionLogEntity updatedTransactionLog = transactionLogRepository.save(transactionLogEntity);

        return transactionLogMapper.toResponse(updatedTransactionLog);
    }

    @Transactional
    public void deleteTransactionLogById(Long id) {
        transactionLogRepository.deleteById(id);
    }
}
