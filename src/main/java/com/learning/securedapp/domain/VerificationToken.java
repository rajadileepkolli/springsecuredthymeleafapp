package com.learning.securedapp.domain;

import java.util.Calendar;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * VerificationToken class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Getter
@Setter
@Document
@NoArgsConstructor
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id private String id;

    private String token;

    private Date expiryDate;

    @DBRef(lazy = true)
    private User user;

    /**
     * Constructor for VerificationToken.
     *
     * @param token a {@link java.lang.String} object.
     * @param user a {@link com.learning.securedapp.domain.User} object.
     */
    public VerificationToken(final String token, final User user) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    /**
     * Getter for the field <code>expiryDate</code>.
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getExpiryDate() {
        return (Date) expiryDate.clone();
    }

    /**
     * Setter for the field <code>expiryDate</code>.
     *
     * @param expiryDate a {@link java.util.Date} object.
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate != null ? (Date) expiryDate.clone() : null;
    }
}
