package com.linhnm.mapper;

import com.linhnm.entity.TransactionLogEntity;
import com.linhnm.model.request.TransactionLogRequest;
import com.linhnm.model.response.TransactionLogResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionLogMapper {

    public TransactionLogEntity toEntity(TransactionLogRequest transactionLogRequest) {
        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setClient(transactionLogRequest.text());
        return transactionLogEntity;
    }

    public void mapTransactionLogWithRequest(
            TransactionLogEntity transactionLogEntity, TransactionLogRequest transactionLogRequest) {
        transactionLogEntity.setClient(transactionLogRequest.text());
    }

    public TransactionLogResponse toResponse(TransactionLogEntity transactionLogEntity) {
        return new TransactionLogResponse(transactionLogEntity.getId(), transactionLogEntity.getClient());
    }

    public List<TransactionLogResponse> toResponseList(List<TransactionLogEntity> transactionLogEntityList) {
        return transactionLogEntityList.stream().map(this::toResponse).toList();
    }
}
