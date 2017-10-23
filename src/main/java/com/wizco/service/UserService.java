 package com.wizco.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wizco.core.transaction.Managed;
import com.wizco.core.transaction.Transacional;
import com.wizco.entity.User;
import com.wizco.hash.md5.Hashmd5;
import com.wizco.repository.UserRepository;

@RequestScoped 
@Named
public class UserService extends Managed {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private UserRepository userRepository;

	@Transacional
	public List save(User user) {
		
		List resultVal = null;
		Hashmd5 hashmd6 = new Hashmd5();
		try {
			
			resultVal = validate(user.getUserLogin());
			String md5 = hashmd6.convert(user);
			
			if(!md5.equals("")) {
		    	user.setPassword(md5);			
				if(resultVal.isEmpty()) {
					this.getEntityManager().persist(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultVal;
	}	

	@Transacional
	public boolean edit(User user){
		
		Hashmd5 hashmd6 = new Hashmd5();
		String md5;
		Boolean retorno = null;
		try {
			
			if(user.getPassword() != null) {
				md5 = hashmd6.convert(user);
				user.setPassword(md5);
			} else {
				User userOld = userRepository.findOid(user.getOid());
				user.setPassword(userOld.getPassword());
			}
			
			List validateUserLogin = validateUserLogin(user);
			if(validateUserLogin != null && validateUserLogin.size() > 0) {
				retorno = false;
			} else {
				this.getEntityManager().merge(user);
				retorno = true;
			}					
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		return retorno;	// undef
	}	

	@Transacional
	public Boolean delete( List<String> ids) {
		Boolean valDelUsers = null;
		try {
			for (String id : ids) {
				
				valDelUsers = valIdsExpense(ids); // Validating if it exists in expenses, any linked user
				
				System.out.println("teste");
				System.out.println(valDelUsers);
				if(valDelUsers == false ) {
					User finds = this.getEntityManager().find(User.class, id);
					this.getEntityManager().remove(finds);		
				}else {
					return valDelUsers;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valDelUsers;
	}	
	
	@Transacional	
	public List<User> list() {
		return this.userRepository.findsAllUsers();
	}
	
	private List validate(String userLogin) {
		return this.userRepository.findvalidate(userLogin);
	}
	
	private List validateUserLogin(User user) {
		return this.userRepository.validateUserLogin(user.getUserLogin(), user.getOid());
	}
	private Boolean valIdsExpense(List<String> ids) {
		
		return this.userRepository.valAllidsExpense(ids);
	}
}
