package com.linhnm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "book_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "price")
    private Long price;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookTransactionEntity bookTransactionEntity = (BookTransactionEntity) o;
        return id != null && Objects.equals(id, bookTransactionEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
