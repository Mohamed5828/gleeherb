package com.mohamed.egHerb.entity;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public abstract class AbstractEntityModel {
//
//    @Version
//    @Column(name = "version", nullable = false)
//    private long version;
//
////    @CreatedBy
////    @Column(name = "creation_user", nullable = false, updatable = false)
////    private String creationUser;
//
//    @CreatedDate
//    @Column(name = "creation_date", nullable = false, updatable = false)
//    private OffsetDateTime creationDate;
//
////    @LastModifiedBy
////    @Column(name = "modification_user", nullable = false)
////    private String modificationUser;
//
//    @LastModifiedDate
//    @Column(name = "modification_date", nullable = false)
//    private OffsetDateTime modificationDate;

}

