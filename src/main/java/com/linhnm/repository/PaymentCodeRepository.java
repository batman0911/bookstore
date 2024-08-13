package com.linhnm.repository;

import com.linhnm.entity.PaymentCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCodeRepository extends JpaRepository<PaymentCodeEntity, Long> {}
