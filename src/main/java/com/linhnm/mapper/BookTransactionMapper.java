package com.linhnm.mapper;

import com.linhnm.entity.BookTransactionEntity;
import com.linhnm.model.request.BookTransactionRequest;
import com.linhnm.model.response.BookTransactionResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookTransactionMapper {

    public BookTransactionEntity toEntity(BookTransactionRequest bookTransactionRequest) {
        BookTransactionEntity bookTransactionEntity = new BookTransactionEntity();
        bookTransactionEntity.setStatus(bookTransactionRequest.text());
        return bookTransactionEntity;
    }

    public void mapBookTransactionWithRequest(
            BookTransactionEntity bookTransactionEntity, BookTransactionRequest bookTransactionRequest) {
        bookTransactionEntity.setStatus(bookTransactionRequest.text());
    }

    public BookTransactionResponse toResponse(BookTransactionEntity bookTransactionEntity) {
        return new BookTransactionResponse(bookTransactionEntity.getId(), bookTransactionEntity.getStatus());
    }

    public List<BookTransactionResponse> toResponseList(List<BookTransactionEntity> bookTransactionEntityList) {
        return bookTransactionEntityList.stream().map(this::toResponse).toList();
    }
}
