package com.learning.securedapp.service.email;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.securedapp.exception.SecuredAppException;

public class EmailServiceTest {

	@Autowired
	private EmailService emailService;

	@Test
	@Ignore
	public final void testSendEmail() throws SecuredAppException {
		emailService.sendEmail("rajadileepkolli@gmail.com", "JCart - Test Mail", "This is a test email from JCart");
	}
}
