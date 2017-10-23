package com.wizco.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.wizco.core.transaction.Managed;
import com.wizco.core.transaction.Transacional;
import com.wizco.entity.User;
import com.wizco.hash.md5.Hashmd5;
import com.wizco.pojo.LoginPojo;
import com.wizco.pojo.UserPojo;
import com.wizco.repository.LoginRepository;

@RequestScoped 
@Named
public class LoginService extends Managed{
	
	@Inject
	private LoginRepository logrep;
	@Inject
	private User users;
	
	@Transacional
	public UserPojo searchLogin(LoginPojo loginpojo) throws NoSuchAlgorithmException, ParseException {

		Hashmd5 hashmd6 = new Hashmd5();
			
		String md5 = hashmd6.convertLogin(loginpojo.getPassword());
		loginpojo.setPassword(md5);
		
		List<User> users = logrep.findAllLogin(loginpojo);
		
		UserPojo pojo = null;
		
		if ( !users.isEmpty()) {
			pojo = pojoajuste(users);
		}
			
		return pojo;
	}

	private UserPojo pojoajuste(List<User> users) throws ParseException {
		
		UserPojo userp = new UserPojo();
		
		for(User user : users) {
			
			UserPojo pojo = new UserPojo();
			
			userp.setLogin(user.getUserLogin());
			userp.setOid(user.getOid());
			if( user.getTypeUser() == 0) {
				userp.setTypeUser("Administrador");
			}else if(user.getTypeUser() == 1) {
				userp.setTypeUser("Operacional");
			}
		}
		return userp;
	}
}
