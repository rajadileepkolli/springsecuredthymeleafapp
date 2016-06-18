package com.learning.securedapp.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
public class RememberMeToken {
    
    @Id
    private String id;

    private String username;

    @Indexed
    private String series;

    private String tokenValue;

    private Date date;

    public RememberMeToken(PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.tokenValue = token.getTokenValue();
        this.date = token.getDate();
    }
}
