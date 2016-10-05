package com.learning.securedapp.configuration;

import java.util.Locale;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <p>WebConfiguration class.</p>
 *
 * @author rajakolli
 *
 *         To redirect from HTTP to HTTPS let us configure
 *         TomcatEmbeddedServletContainerFactory bean in our
 *         WebConfiguration.java
 *
 *         keystore can be generated using the command keytool -genkey -alias
 *         securedapp -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore
 *         securekeystore.p12 -validity 365
 * @version $Id: $Id
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Value("${server.port:8443}")
	private int serverPort;

	/** {@inheritDoc} */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(localeChangeInterceptor());
	}

	/** {@inheritDoc} */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/").setViewName("forward:/home.html");
		registry.addViewController("/registration.html").setViewName("registration");
		registry.addViewController("/successRegister.html").setViewName("successRegister");
		registry.addViewController("/invalidSession.html");
		registry.addViewController("/login.html").setViewName("login");
		registry.addViewController("/home.html").setViewName("home");
		registry.addViewController("/categories.html").setViewName("categories");
	}

	/**
	 * <p>localeChangeInterceptor.</p>
	 *
	 * @return a {@link org.springframework.web.servlet.HandlerInterceptor} object.
	 */
	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 * <p>localeResolver.</p>
	 *
	 * @return a {@link org.springframework.web.servlet.LocaleResolver} object.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		// SessionLocaleResolver
		cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		return cookieLocaleResolver;
	}

	/**
	 * <p>servletContainer.</p>
	 *
	 * @return a {@link org.springframework.boot.context.embedded.EmbeddedServletContainerFactory} object.
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
		return tomcat;
	}

	private Connector initiateHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(serverPort);

		return connector;
	}
}
