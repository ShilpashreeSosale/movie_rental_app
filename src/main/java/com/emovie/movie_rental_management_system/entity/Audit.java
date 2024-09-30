package com.emovie.movie_rental_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Audited
public class Audit {
    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private String createdBy = "MovieRentalProvider";

    @CreatedDate
    @Column(name = "CREATED_TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime createdTimestamp;

    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private String updatedBy = "MovieRentalProvider";

    @LastModifiedDate
    @Column(name = "UPDATED_TIMESTAMP")
    private LocalDateTime updatedTimestamp;
}