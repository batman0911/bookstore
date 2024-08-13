package com.linhnm.mapper;

import com.linhnm.entity.AuthorEntity;
import com.linhnm.model.request.AuthorRequest;
import com.linhnm.model.response.AuthorResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthorMapper {

    public AuthorEntity toEntity(AuthorRequest authorRequest) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorRequest.text());
        return authorEntity;
    }

    public void mapAuthorWithRequest(AuthorEntity authorEntity, AuthorRequest authorRequest) {
        authorEntity.setName(authorRequest.text());
    }

    public AuthorResponse toResponse(AuthorEntity authorEntity) {
        return new AuthorResponse(authorEntity.getId(), authorEntity.getName());
    }

    public List<AuthorResponse> toResponseList(List<AuthorEntity> authorEntityList) {
        return authorEntityList.stream().map(this::toResponse).toList();
    }
}
