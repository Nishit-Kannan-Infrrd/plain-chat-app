package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.vos.LoginRequest;


public interface SignUpService
{
    void publishUserSignUpEvent( LoginRequest loginRequest );

}
