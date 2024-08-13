package com.linhnm.repository;

import com.linhnm.entity.BookTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransactionEntity, Long> {}
