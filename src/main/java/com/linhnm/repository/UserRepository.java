package com.linhnm.repository;

import com.linhnm.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query(nativeQuery = true,
            value = """
                    select r.role
                    from user u
                             join user_role ur on u.id = ur.user_id
                             join role r on ur.role_id = r.id
                    where u.username = :username
                      and u.enabled = true
                      and r.enabled = true
                    """)
    List<String> getUserRoles(String username);
}
