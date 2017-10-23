package com.wizco.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class GenericEntity {
	
	@Id
	protected String oid;
	
	@JsonIgnore
	@Column(name = "dt_creation")
	private Date dtCreation;
	
	@JsonIgnore
	@Column(name = "dt_update")
	private Date dtUpdate;
	
	public Date getDtCreation() {
		return dtCreation;
	}
	public void setDtCreation(Date dtCreation) {
		this.dtCreation = dtCreation;
	}
	public Date getDtUpdate() {
		return dtUpdate;
	}
	public void setDtUpdate(Date dtUpdate) {
		this.dtUpdate = dtUpdate;
	}
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	@PrePersist
	public void prePersist() {
		this.oid = UUID.randomUUID().toString();
		this.dtCreation = new Date();
	}
	
	@PreUpdate
	public void preupdate() {
		this.dtUpdate = new Date();
	}
}
