package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.vos.LoginRequest;
import com.crazynerve.chatter.gateway.vos.LoginResponse;
import com.crazynerve.chatter.usermanagement.models.UserAuthenticationVO;
import com.crazynerve.chatter.usermanagement.models.UserEmailAddressVO;
import com.crazynerve.chatter.usermanagement.models.UserResponseVO;
import com.crazynerve.chatter.usermanagement.services.UserManagementServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class UserManagementServiceImpl implements UserManagementService
{
    private static final Logger LOGGER = Logger.getLogger( UserManagementServiceImpl.class.getName() );

    @GrpcClient ("usermanagement-service") private UserManagementServiceGrpc.UserManagementServiceBlockingStub userManagementServiceBlockingStub;


    @Override
    public LoginResponse addUser( LoginRequest loginRequest )
    {
        LOGGER.info( () -> "Adding user with email address " + loginRequest.getEmailAddress() );
        UserResponseVO userResponseVO = userManagementServiceBlockingStub.addUser(
            UserAuthenticationVO.newBuilder().setEmailAddress( loginRequest.getEmailAddress() )
                .setPassword( loginRequest.getPassword() ).build() );
        return new LoginResponse( userResponseVO.getUserId(), userResponseVO.getEmailAddress() );
    }


    @Override
    public LoginResponse getUserByEmailAddress( String emailAddress )
    {
        LOGGER.info( () -> "Checking for user with email address " + emailAddress );
        UserResponseVO userResponseVO = userManagementServiceBlockingStub
            .getUserDetails( UserEmailAddressVO.newBuilder().setEmailAddress( emailAddress ).build() );
        return new LoginResponse( userResponseVO.getUserId(), userResponseVO.getEmailAddress() );
    }
}
