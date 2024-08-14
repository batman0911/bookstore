package com.linhnm.controller;

import com.linhnm.common.response.CommonResponse;
import com.linhnm.exception.BookTransactionNotFoundException;
import com.linhnm.model.query.FindBookTransactionsQuery;
import com.linhnm.model.request.BookTransactionRequest;
import com.linhnm.model.response.BookTransactionResponse;
import com.linhnm.service.BookTransactionService;
import com.linhnm.utils.AppConstants;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v${api.version}/book-transactions")
@Slf4j
@RequiredArgsConstructor
class BookTransactionController {

    private final BookTransactionService bookTransactionService;

    @GetMapping
    CommonResponse<List<BookTransactionResponse>> getAllBookTransactionList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        FindBookTransactionsQuery findBookTransactionsQuery =
                new FindBookTransactionsQuery(pageNo, pageSize, sortBy, sortDir);
        Page<BookTransactionResponse> bookTransactionResponsePage =
                bookTransactionService.findAllBookTransactions(findBookTransactionsQuery);
        return CommonResponse.of(bookTransactionResponsePage);
    }

    @GetMapping("/{id}")
    CommonResponse<BookTransactionResponse> getBookTransactionById(@PathVariable Long id) {
        return bookTransactionService
                .findBookTransactionById(id)
                .map(CommonResponse::of)
                .orElseThrow(() -> new BookTransactionNotFoundException(id));
    }

    @PostMapping
    CommonResponse<BookTransactionResponse> createBookTransaction(
            @RequestBody @Validated BookTransactionRequest bookTransactionRequest) {
        BookTransactionResponse response = bookTransactionService.saveBookTransaction(bookTransactionRequest);
        return CommonResponse.of(response);
    }

    @PutMapping("/{id}")
    CommonResponse<BookTransactionResponse> updateBookTransaction(
            @PathVariable Long id, @RequestBody @Valid BookTransactionRequest bookTransactionRequest) {
        return CommonResponse.of(bookTransactionService.updateBookTransaction(id, bookTransactionRequest));
    }

    @DeleteMapping("/{id}")
    CommonResponse<BookTransactionResponse> deleteBookTransaction(@PathVariable Long id) {
        return bookTransactionService
                .findBookTransactionById(id)
                .map(bookTransaction -> {
                    bookTransactionService.deleteBookTransactionById(id);
                    return CommonResponse.of(bookTransaction);
                })
                .orElseThrow(() -> new BookTransactionNotFoundException(id));
    }
}
