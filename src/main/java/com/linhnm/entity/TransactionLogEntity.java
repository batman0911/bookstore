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
@Table(name = "transaction_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "client")
    private String client;

    @Column(name = "trans_type")
    private String transType;

    @Column(name = "url")
    private String url;

    @Column(name = "method")
    private String method;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "response_body")
    private String responseBody;

    @Column(name = "http_status")
    private Integer httpStatus;
    @Column(name = "created_at")
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TransactionLogEntity transactionLogEntity = (TransactionLogEntity) o;
        return id != null && Objects.equals(id, transactionLogEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
