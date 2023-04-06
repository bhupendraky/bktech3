package com.tech.hub.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U> implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedBy
	protected U createdBy;

	@CreatedDate
	protected Date createdOn;

	@LastModifiedBy
	protected U updatedBy;

	@LastModifiedDate
	protected Date updatedOn;

}
