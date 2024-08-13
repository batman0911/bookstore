package com.linhnm.service;

import com.linhnm.entity.AuthorEntity;
import com.linhnm.exception.AuthorNotFoundException;
import com.linhnm.mapper.AuthorMapper;
import com.linhnm.model.query.FindAuthorsQuery;
import com.linhnm.model.request.AuthorRequest;
import com.linhnm.model.response.AuthorResponse;
import com.linhnm.repository.AuthorRepository;
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
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public Page<AuthorResponse> findAllAuthors(FindAuthorsQuery findAuthorsQuery) {

        // create Pageable instance
        Pageable pageable = createPageable(findAuthorsQuery);

        Page<AuthorEntity> authorsPage = authorRepository.findAll(pageable);

        return authorsPage.map(authorMapper::toResponse);
    }

    private Pageable createPageable(FindAuthorsQuery findAuthorsQuery) {
        int pageNo = Math.max(findAuthorsQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findAuthorsQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findAuthorsQuery.sortBy())
                        : Sort.Order.desc(findAuthorsQuery.sortBy()));
        return PageRequest.of(pageNo, findAuthorsQuery.pageSize(), sort);
    }

    public Optional<AuthorResponse> findAuthorById(Long id) {
        return authorRepository.findById(id).map(authorMapper::toResponse);
    }

    @Transactional
    public AuthorResponse saveAuthor(AuthorRequest authorRequest) {
        AuthorEntity author = authorMapper.toEntity(authorRequest);
        AuthorEntity savedAuthor = authorRepository.save(author);
        return authorMapper.toResponse(savedAuthor);
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

        // Update the author object with data from authorRequest
        authorMapper.mapAuthorWithRequest(authorEntity, authorRequest);

        // Save the updated author object
        AuthorEntity updatedAuthor = authorRepository.save(authorEntity);

        return authorMapper.toResponse(updatedAuthor);
    }

    @Transactional
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
