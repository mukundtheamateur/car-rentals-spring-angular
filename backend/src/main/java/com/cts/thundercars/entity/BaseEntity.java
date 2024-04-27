package com.cts.thundercars.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	
	/**
	 * @CreatedDate: automatically populate the created_at
	 */
	@CreatedDate
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @CreatedBy
    @Column(name="created_by",updatable = false, columnDefinition = "varchar(200) default 'anonymous'")
    private String createdBy;
    
    @LastModifiedDate
    @Column(name="updated_at",insertable = false)
    private LocalDateTime updatedAt;
    
    @LastModifiedBy
    @Column(name="updated_by", insertable = false, columnDefinition = "varchar(200) default 'anonymous'")
    private String updatedBy;
}
