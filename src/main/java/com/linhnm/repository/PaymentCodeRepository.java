package com.linhnm.repository;

import com.linhnm.entity.PaymentCodeEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCodeRepository extends JpaRepository<PaymentCodeEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PaymentCodeEntity p WHERE p.code = :code and p.enabled = true")
    Optional<PaymentCodeEntity> findByCodeLock(String code);
}
