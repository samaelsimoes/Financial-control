package com.wizco.repository;

import java.util.List;

import com.wizco.core.transaction.Managed;
import com.wizco.entity.User;
import com.wizco.pojo.LoginPojo;

public class LoginRepository extends Managed {

	@SuppressWarnings("unchecked")
	public List<User> findAllLogin(LoginPojo pojologin) {

		return this.getEntityManager().createQuery("SELECT u FROM User u where userLogin = :userLogin and password = :password")
				.setParameter("userLogin", pojologin.getLogin()).setParameter("password", pojologin.getPassword()).getResultList();
	}
}
