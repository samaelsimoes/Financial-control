package com.wizco.repository;

import java.util.List;

import com.wizco.core.transaction.Managed;
import com.wizco.entity.Expense;
import com.wizco.entity.User;

public class UserRepository extends Managed {
	
	public User persist(User user) {
		this.getEntityManager().persist(user);
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return this.getEntityManager().createQuery("SELECT u FROM User u ORDER BY u.name").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findsAllUsers(){
		List<User> user = this.getEntityManager().createQuery("SELECT new User(u.oid, u.name, u.typeUser, u.userLogin)  FROM User u ORDER BY u.name").getResultList();
		return user;
	}
	@SuppressWarnings("unchecked")
	public List<User> validateUserLogin(String login, String oid) {
		return this.getEntityManager().createQuery("SELECT u FROM User u where userLogin = :userLogin and oid <> :oid")
				.setParameter("userLogin", login).setParameter("oid", oid).getResultList();
	}
	@SuppressWarnings("unchecked")
	public User findOid(String oid) {
		return (User) this.getEntityManager().createQuery("SELECT u FROM User u where u.oid = :oid")
				.setParameter("oid", oid).getSingleResult();
	}
	@SuppressWarnings("unchecked")
	public List<User> findvalidate(String userLogin) {
		return this.getEntityManager().createQuery("SELECT userLogin FROM User u where userLogin = :userLogin")
				.setParameter("userLogin", userLogin).getResultList();
	}

	public Boolean valAllidsExpense(List<String> UserId) {
		List<Expense> exp = this.getEntityManager().createQuery("SELECT u FROM Expense u ORDER BY u.oid").getResultList();

		boolean inf = false;
		if ( exp.isEmpty() ) {
			inf = false;
		}else {
			inf = true;
		}
		System.out.println("retorno");
		System.out.println(inf);
		return inf;
	}
}
