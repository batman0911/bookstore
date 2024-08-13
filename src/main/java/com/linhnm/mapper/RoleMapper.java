package com.linhnm.mapper;

import com.linhnm.entity.RoleEntity;
import com.linhnm.model.request.RoleRequest;
import com.linhnm.model.response.RoleResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    public RoleEntity toEntity(RoleRequest roleRequest) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(roleRequest.text());
        return roleEntity;
    }

    public void mapRoleWithRequest(RoleEntity roleEntity, RoleRequest roleRequest) {
        roleEntity.setRole(roleRequest.text());
    }

    public RoleResponse toResponse(RoleEntity roleEntity) {
        return new RoleResponse(roleEntity.getId(), roleEntity.getRole());
    }

    public List<RoleResponse> toResponseList(List<RoleEntity> roleEntityList) {
        return roleEntityList.stream().map(this::toResponse).toList();
    }
}
