package com.learning.securedapp.web.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.learning.securedapp.ApplicationTests;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;

public class UserRepositoryTest extends ApplicationTests {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	public final void test() throws SecuredAppException {
		userRepository.deleteAll();
		User user = User.builder().userName("superadmin").password(new BCryptPasswordEncoder(10).encode("superadmin"))
				.email("expoenviron@gmail.com").enabled(true).roleList(roleRepository.findAll()).build();
		userRepository.save(user);
	}

}
