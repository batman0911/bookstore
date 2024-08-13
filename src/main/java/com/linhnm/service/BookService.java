package com.linhnm.service;

import com.linhnm.entity.BookEntity;
import com.linhnm.exception.BookNotFoundException;
import com.linhnm.mapper.BookMapper;
import com.linhnm.model.query.FindBooksQuery;
import com.linhnm.model.request.BookRequest;
import com.linhnm.model.response.BookResponse;
import com.linhnm.repository.BookRepository;
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
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Page<BookResponse> findAllBooks(FindBooksQuery findBooksQuery) {

        // create Pageable instance
        Pageable pageable = createPageable(findBooksQuery);

        Page<BookEntity> booksPage = bookRepository.findAll(pageable);

        return booksPage.map(bookMapper::toResponse);
    }

    private Pageable createPageable(FindBooksQuery findBooksQuery) {
        int pageNo = Math.max(findBooksQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findBooksQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findBooksQuery.sortBy())
                        : Sort.Order.desc(findBooksQuery.sortBy()));
        return PageRequest.of(pageNo, findBooksQuery.pageSize(), sort);
    }

    public Optional<BookResponse> findBookById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toResponse);
    }

    @Transactional
    public BookResponse saveBook(BookRequest bookRequest) {
        BookEntity book = bookMapper.toEntity(bookRequest);
        BookEntity savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Transactional
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        // Update the book object with data from bookRequest
        bookMapper.mapBookWithRequest(bookEntity, bookRequest);

        // Save the updated book object
        BookEntity updatedBook = bookRepository.save(bookEntity);

        return bookMapper.toResponse(updatedBook);
    }

    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
