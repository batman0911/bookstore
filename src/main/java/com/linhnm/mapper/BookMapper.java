package com.linhnm.mapper;

import com.linhnm.entity.BookEntity;
import com.linhnm.model.request.BookRequest;
import com.linhnm.model.response.BookResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public BookEntity toEntity(BookRequest bookRequest) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookRequest.text());
        return bookEntity;
    }

    public void mapBookWithRequest(BookEntity bookEntity, BookRequest bookRequest) {
        bookEntity.setTitle(bookRequest.text());
    }

    public BookResponse toResponse(BookEntity bookEntity) {
        return new BookResponse(bookEntity.getId(), bookEntity.getTitle());
    }

    public List<BookResponse> toResponseList(List<BookEntity> bookEntityList) {
        return bookEntityList.stream().map(this::toResponse).toList();
    }
}
