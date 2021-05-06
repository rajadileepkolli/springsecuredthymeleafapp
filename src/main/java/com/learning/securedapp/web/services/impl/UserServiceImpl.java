package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.domain.VerificationToken;
import com.learning.securedapp.web.repositories.UserRepository;
import com.learning.securedapp.web.repositories.VerificationTokenRepository;
import com.learning.securedapp.web.services.IUserService;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    /** Constant <code>TOKEN_INVALID="invalidToken"</code> */
    public static final String TOKEN_INVALID = "invalidToken";
    /** Constant <code>TOKEN_EXPIRED="expired"</code> */
    public static final String TOKEN_EXPIRED = "expired";

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    /** {@inheritDoc} */
    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    /** {@inheritDoc} */
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
