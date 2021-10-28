package com.crazynerve.chatter.gateway.controllers;

import com.crazynerve.chatter.gateway.vos.LoginRequest;
import com.crazynerve.chatter.gateway.vos.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
@RequestMapping ("/login")
public class LoginController
{
    private static final Logger LOGGER = Logger.getLogger( LoginController.class.getName() );

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest ){
        LOGGER.info( () -> "User login for " + loginRequest.getEmailAddress() );
        return new ResponseEntity<>( new LoginResponse("1234"), HttpStatus.OK );
    }
}
