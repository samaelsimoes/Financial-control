package com.wizco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CostCenter extends GenericEntity {
	
	@Column(name = "code", nullable = false)
	private int code;
	
	@Column(name = "name", nullable = false)
	private String name;

	public int getCode() {
		return code;
	}

	public void setCode(int type) {
		this.code = type;
	}
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
