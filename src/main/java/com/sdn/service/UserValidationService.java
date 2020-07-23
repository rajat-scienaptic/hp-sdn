package com.sdn.service;


import com.sdn.model.User;

public interface UserValidationService {
    public User getUserService(String userName, String email);
    public String getUserNameFromCookie(String cookie);
}
