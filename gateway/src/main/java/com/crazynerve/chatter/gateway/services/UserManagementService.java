package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.vos.LoginRequest;
import com.crazynerve.chatter.gateway.vos.LoginResponse;


public interface UserManagementService
{
    LoginResponse addUser( LoginRequest loginRequest );

    LoginResponse getUserByEmailAddress(String emailAddress);
}
