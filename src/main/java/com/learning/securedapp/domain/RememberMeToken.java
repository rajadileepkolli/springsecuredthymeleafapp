package com.learning.securedapp.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@NoArgsConstructor
public class RememberMeToken {
    
    @Id
    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String username;

    @Indexed
    @Setter
    @Getter
    private String series;

    @Setter
    @Getter
    private String tokenValue;

    private Date date;

    public RememberMeToken(PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.tokenValue = token.getTokenValue();
        this.date = token.getDate();
    }

	public Date getDate() {
		return (Date) date.clone();
	}

	public void setDate(Date date) {
		this.date = date != null ? new Date(date.getTime()) : null;
	}
}
