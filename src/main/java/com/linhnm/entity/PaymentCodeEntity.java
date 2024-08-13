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
@Table(name = "payment_code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "expired_at")
    private Date expiredAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "enabled")
    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentCodeEntity paymentCodeEntity = (PaymentCodeEntity) o;
        return id != null && Objects.equals(id, paymentCodeEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
