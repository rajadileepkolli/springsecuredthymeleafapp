package com.learning.securedapp.web.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.domain.VerificationToken;
import com.learning.securedapp.web.repositories.UserRepository;
import com.learning.securedapp.web.repositories.VerificationTokenRepository;

@Service
public class UserServiceImpl implements IUserService{
    
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    
    @Autowired private VerificationTokenRepository tokenRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public void createVerificationTokenForUser(User user, String token) {
       VerificationToken myToken = new VerificationToken(token, user);
       tokenRepository.save(myToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return null;
    }

}
