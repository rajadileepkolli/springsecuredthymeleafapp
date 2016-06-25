package com.learning.securedapp.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 *  Internationalization messages configuration.
 */
@Configuration
public class MessagesConfig {
    
    private static final String MESSAGES_BASENAME = "messages";

    /**
     * 
     * @return bean for Message externalization/internationalization.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MESSAGES_BASENAME);
        return messageSource;
    }
}
