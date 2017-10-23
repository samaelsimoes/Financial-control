package com.wizco.pojo;

import java.util.List;

import com.wizco.entity.CostCenter;
import com.wizco.entity.Genre;
import com.wizco.entity.User;

public class ExpensePojo {
	
	private List<CostCenter> CostCenter;	
	private List<Genre> Genre;	
	private List<User> User;
	
	public ExpensePojo() {
		super();
	}	
	public ExpensePojo(List<CostCenter> CostCenter, List<Genre> TypeExpense, List<User> User) {
		super();
	}	
	public List<CostCenter> getCostCenter() {
		return CostCenter;
	}
	public void setCostCenter(List<CostCenter> costCenter) {
		CostCenter = costCenter;
	}
	public List<Genre> getGenre() {
		return Genre;
	}
	public void setGenre(List<Genre> genre) {
		Genre = genre;
	}
	public List<User> getUser() {
		return User;
	}
	public void setUser(List<User> user) {
		User = user;
	}
}
