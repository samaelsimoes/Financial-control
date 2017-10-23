package com.wizco.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wizco.entity.User;
import com.wizco.repository.UserRepository;

public class UserServiceTest {
	
	private UserService userService;	
	
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService();
	}
	
	@Test
	public void testSavedUser() {
		User userFake = createUserFake();
		Mockito.when(userRepository.persist(userFake)).thenReturn(userFake);
		userService.save(userFake);
		Assert.assertEquals(userFake.getName(), "TESTE");
	}
	
	public User createUserFake() {
		User user = new User();
		user.setName("TESTE");
		return user;
	}

}
