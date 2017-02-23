package com.learning.securedapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
/**
 * <p>ThymeleafConfigurator class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class ThymeleafConfigurator {

	/**
	 * <p>emailTemplateResolver.</p>
	 *
	 * @return a {@link org.thymeleaf.templateresolver.ClassLoaderTemplateResolver} object.
	 */
	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver() {
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
		emailTemplateResolver.setPrefix("mails/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
		emailTemplateResolver.setCharacterEncoding("UTF-8");
		emailTemplateResolver.setOrder(0);
		emailTemplateResolver.setCheckExistence(true);

		return emailTemplateResolver;
	}
}
