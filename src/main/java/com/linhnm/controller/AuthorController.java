package com.linhnm.controller;

import com.linhnm.common.response.CommonResponse;
import com.linhnm.exception.AuthorNotFoundException;
import com.linhnm.model.query.FindAuthorsQuery;
import com.linhnm.model.request.AuthorRequest;
import com.linhnm.model.response.AuthorResponse;
import com.linhnm.service.AuthorService;
import com.linhnm.utils.AppConstants;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
@Slf4j
@RequiredArgsConstructor
class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    CommonResponse<List<AuthorResponse>> getAllAuthorList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        FindAuthorsQuery findAuthorsQuery = new FindAuthorsQuery(pageNo, pageSize, sortBy, sortDir);
        Page<AuthorResponse> authorResponsePage = authorService.findAllAuthors(findAuthorsQuery);
        return CommonResponse.of(authorResponsePage);
    }

    @GetMapping("/{id}")
    CommonResponse<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return authorService
                .findAuthorById(id)
                .map(CommonResponse::of)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @PostMapping
    CommonResponse<AuthorResponse> createAuthor(@RequestBody @Validated AuthorRequest authorRequest) {
        AuthorResponse response = authorService.saveAuthor(authorRequest);
        return CommonResponse.of(response);
    }

    @PutMapping("/{id}")
    CommonResponse<AuthorResponse> updateAuthor(
            @PathVariable Long id, @RequestBody @Valid AuthorRequest authorRequest) {
        return CommonResponse.of(authorService.updateAuthor(id, authorRequest));
    }

    @DeleteMapping("/{id}")
    CommonResponse<AuthorResponse> deleteAuthor(@PathVariable Long id) {
        return authorService
                .findAuthorById(id)
                .map(author -> {
                    authorService.deleteAuthorById(id);
                    return CommonResponse.of(author);
                })
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
