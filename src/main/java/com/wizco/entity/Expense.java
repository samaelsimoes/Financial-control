package com.wizco.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Expense extends GenericEntity {
	
	@Column(name = "code", nullable = false)
	private int code;
	
	@Column(name = "situation", nullable = false)
	private String situation;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "UserId", nullable = false)
	private User user;
	
	@Column(name = "paidValue", nullable = false)
	private float paidValue;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "CostCenterId", nullable = false)
	private CostCenter costcenter;
	
	@Column(name = "reason", nullable = false)
	private String reason;
	
	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date date;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "TypeId", nullable = false)
	private Genre type;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "value", nullable = false)
	private float value;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public float getPaidValue() {
		return paidValue;
	}

	public void setPaidValue(float paidValue) {
		this.paidValue = paidValue;
	}

	public CostCenter getCostCenter() {
		return costcenter;
	}

	public void setCostCenter(CostCenter costcenter) {
		this.costcenter = costcenter;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Genre getType() {
		return type;
	}

	public void setType(Genre type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
