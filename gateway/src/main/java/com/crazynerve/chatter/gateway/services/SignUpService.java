package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.vos.LoginRequest;

import java.util.Map;


public interface SignUpService
{
    void publishUserSignUpEvent( LoginRequest loginRequest, Map<String, String> headers );

}
