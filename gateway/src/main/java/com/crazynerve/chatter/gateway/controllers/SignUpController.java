package com.crazynerve.chatter.gateway.controllers;

import com.crazynerve.chatter.gateway.services.SignUpService;
import com.crazynerve.chatter.gateway.services.UserManagementService;
import com.crazynerve.chatter.gateway.vos.LoginRequest;
import com.crazynerve.chatter.gateway.vos.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;


@RestController
@RequestMapping ("/signup")
public class SignUpController
{
    private static final Logger LOGGER = Logger.getLogger( SignUpController.class.getName() );

    private SignUpService signUpService;
    private UserManagementService userManagementService;


    @Autowired
    public void setSignUpService( SignUpService signUpService )
    {
        this.signUpService = signUpService;
    }


    @Autowired
    public void setUserManagementService( UserManagementService userManagementService )
    {
        this.userManagementService = userManagementService;
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
            deferredResult.setResult( new ResponseEntity<>( new LoginResponse( "1234", "a@b.com" ), HttpStatus.CREATED ) );
        } );
        return deferredResult;
    }


    @PostMapping ("/user")
    public ResponseEntity<LoginResponse> addUser( @RequestBody LoginRequest loginRequest )
    {
        LOGGER.info( () -> "Adding new user with email address " + loginRequest.getEmailAddress() );
        return new ResponseEntity<>( userManagementService.addUser( loginRequest ), HttpStatus.CREATED );
    }


    @GetMapping("/user/email/{emailAddress}")
    public ResponseEntity<LoginResponse> findUserByEmailAddress( @PathVariable (name = "emailAddress") String emailAddress )
    {
        LOGGER.info( () -> "Finding user with email address " + emailAddress );
        return new ResponseEntity<>( userManagementService.getUserByEmailAddress( emailAddress ), HttpStatus.OK );
    }
}
