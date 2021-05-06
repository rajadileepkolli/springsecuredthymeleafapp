package com.learning.securedapp.domain;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

/**
 * RememberMeToken class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Setter
@Getter
@Document
@NoArgsConstructor
public class RememberMeToken {

    @Id private String id;

    private String username;

    @Indexed private String series;

    private String tokenValue;

    private Date date;

    /**
     * Constructor for RememberMeToken.
     *
     * @param token a {@link
     *     org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken}
     *     object.
     */
    public RememberMeToken(PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.tokenValue = token.getTokenValue();
        this.date = token.getDate();
    }

    /**
     * Getter for the field <code>date</code>.
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getDate() {
        return (Date) date.clone();
    }

    /**
     * Setter for the field <code>date</code>.
     *
     * @param date a {@link java.util.Date} object.
     */
    public void setDate(Date date) {
        this.date = date != null ? new Date(date.getTime()) : null;
    }
}
