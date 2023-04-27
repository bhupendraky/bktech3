package com.bktech.customer.vo;

import com.bktech.customer.entity.AuditableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenVO extends AuditableEntity<String> {

	private static final long serialVersionUID = -5312370255319044385L;

	private Long id;
	private String value;
	private boolean valid;
}
