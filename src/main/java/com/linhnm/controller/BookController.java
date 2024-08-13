package com.linhnm.controller;

import com.linhnm.common.response.CommonResponse;
import com.linhnm.exception.BookNotFoundException;
import com.linhnm.model.query.FindBooksQuery;
import com.linhnm.model.request.BookRequest;
import com.linhnm.model.response.BookResponse;
import com.linhnm.service.BookService;
import com.linhnm.utils.AppConstants;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
@RequiredArgsConstructor
class BookController {

    private final BookService bookService;

    @GetMapping
    CommonResponse<List<BookResponse>> getAllBookList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        FindBooksQuery findBooksQuery = new FindBooksQuery(pageNo, pageSize, sortBy, sortDir);
        Page<BookResponse> bookResponsePage = bookService.findAllBooks(findBooksQuery);
        return CommonResponse.of(bookResponsePage);
    }

    @GetMapping("/{id}")
    CommonResponse<BookResponse> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id).map(CommonResponse::of).orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping
    CommonResponse<BookResponse> createBook(@RequestBody @Validated BookRequest bookRequest) {
        BookResponse response = bookService.saveBook(bookRequest);
        return CommonResponse.of(response);
    }

    @PutMapping("/{id}")
    CommonResponse<BookResponse> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest) {
        return CommonResponse.of(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    CommonResponse<BookResponse> deleteBook(@PathVariable Long id) {
        return bookService
                .findBookById(id)
                .map(book -> {
                    bookService.deleteBookById(id);
                    return CommonResponse.of(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
