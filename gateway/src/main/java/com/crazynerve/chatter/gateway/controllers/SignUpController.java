package com.crazynerve.chatter.gateway.controllers;

import com.crazynerve.chatter.gateway.services.SignUpService;
import com.crazynerve.chatter.gateway.vos.LoginRequest;
import com.crazynerve.chatter.gateway.vos.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;


@RestController
@RequestMapping ("/signup")
public class SignUpController
{
    private static final Logger LOGGER = Logger.getLogger( SignUpController.class.getName() );

    private SignUpService signUpService;


    @Autowired
    public void setSignUpService( SignUpService signUpService )
    {
        this.signUpService = signUpService;
    }


    @PostMapping ("")
    public DeferredResult<ResponseEntity<LoginResponse>> signUp( @RequestBody LoginRequest loginRequest )
    {
        LOGGER.info( () -> "New user sign up " + loginRequest.getEmailAddress() );
        DeferredResult<ResponseEntity<LoginResponse>> deferredResult = new DeferredResult<>( 5000l );
        deferredResult.onTimeout( () -> deferredResult
            .setErrorResult( ResponseEntity.status( HttpStatus.REQUEST_TIMEOUT ).body( "Request timed out." ) ) );
        deferredResult.onError( deferredResult::setErrorResult );
        // publish event
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit( () -> {
            signUpService.publishUserSignUpEvent( loginRequest );
            // wait for response
            deferredResult.setResult( new ResponseEntity<>( new LoginResponse("1234"), HttpStatus.CREATED ) );
        } );
        return deferredResult;
    }
}
