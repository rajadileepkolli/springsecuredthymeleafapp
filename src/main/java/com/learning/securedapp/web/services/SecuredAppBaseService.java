package com.learning.securedapp.web.services;

public interface SecuredAppBaseService {
    String getMessage(String code);

    String getCurrentUser();
}
