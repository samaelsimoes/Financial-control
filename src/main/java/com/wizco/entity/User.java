package com.wizco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends GenericEntity {
	
	@Column(name = "name", nullable = false)
	private String name;	
	
	@Column(name = "typeUser", nullable = false)
	private int typeUser;	
	
	@Column(name = "userLogin", nullable = false)
	private String userLogin;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	public User() {
	}

	public User(String oid, String name, int typeUser, String userLogin) {
		
		super();
		
		this.oid = oid;
		this.name = name;
		this.typeUser = typeUser;
		this.userLogin = userLogin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeUser() {
		return typeUser;
	}

	public void setType(int typeUser) {
		this.typeUser = typeUser;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
