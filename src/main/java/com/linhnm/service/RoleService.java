package com.linhnm.service;

import com.linhnm.entity.RoleEntity;
import com.linhnm.exception.RoleNotFoundException;
import com.linhnm.mapper.RoleMapper;
import com.linhnm.model.query.FindRolesQuery;
import com.linhnm.model.request.RoleRequest;
import com.linhnm.model.response.RoleResponse;
import com.linhnm.repository.RoleRepository;
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
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Page<RoleResponse> findAllRoles(FindRolesQuery findRolesQuery) {

        // create Pageable instance
        Pageable pageable = createPageable(findRolesQuery);

        Page<RoleEntity> rolesPage = roleRepository.findAll(pageable);

        return rolesPage.map(roleMapper::toResponse);
    }

    private Pageable createPageable(FindRolesQuery findRolesQuery) {
        int pageNo = Math.max(findRolesQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findRolesQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findRolesQuery.sortBy())
                        : Sort.Order.desc(findRolesQuery.sortBy()));
        return PageRequest.of(pageNo, findRolesQuery.pageSize(), sort);
    }

    public Optional<RoleResponse> findRoleById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toResponse);
    }

    @Transactional
    public RoleResponse saveRole(RoleRequest roleRequest) {
        RoleEntity role = roleMapper.toEntity(roleRequest);
        RoleEntity savedRole = roleRepository.save(role);
        return roleMapper.toResponse(savedRole);
    }

    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));

        // Update the role object with data from roleRequest
        roleMapper.mapRoleWithRequest(roleEntity, roleRequest);

        // Save the updated role object
        RoleEntity updatedRole = roleRepository.save(roleEntity);

        return roleMapper.toResponse(updatedRole);
    }

    @Transactional
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
