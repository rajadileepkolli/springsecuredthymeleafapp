package com.learning.securedapp.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfiguration class.
 *
 * @author rajakolli
 *     <p>To redirect from HTTP to HTTPS let us configure TomcatEmbeddedServletContainerFactory bean
 *     in our WebConfiguration.java
 *     <p>keystore can be generated using the command keytool -genkey -alias securedapp -storetype
 *     PKCS12 -keyalg RSA -keysize 2048 -keystore securekeystore.p12 -validity 3650
 * @version 1: 0
 */
@Configuration(proxyBeanMethods = false)
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${server.port:8443}")
    private int serverPort;

    /** {@inheritDoc} */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home.html");
        registry.addViewController("/registration.html").setViewName("registration");
        registry.addViewController("/successRegister.html").setViewName("successRegister");
        registry.addViewController("/invalidSession.html");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/home.html").setViewName("home");
        registry.addViewController("/categories.html").setViewName("categories");
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory tomcat =
                new TomcatServletWebServerFactory() {
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
