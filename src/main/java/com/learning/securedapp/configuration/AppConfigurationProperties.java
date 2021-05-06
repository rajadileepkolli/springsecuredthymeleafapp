package com.learning.securedapp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.learning")
@Setter
@Getter
public class AppConfigurationProperties {

    private String supportEmail;
}
