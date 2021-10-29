package com.crazynerve.chatter.consumer.userchecker.services;

import com.crazynerve.chatter.gateway.protos.common.UserDetails;
import com.crazynerve.chatter.usermanagement.models.UserEmailAddressVO;
import com.crazynerve.chatter.usermanagement.services.UserManagementServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class UserService
{
    private static final Logger LOGGER = Logger.getLogger( UserService.class.getName() );

    @GrpcClient ("usermanagement-service") private UserManagementServiceGrpc.UserManagementServiceBlockingStub userManagementServiceBlockingStub;


    public void checkUserAndPublish( UserDetails userDetails )
    {
        boolean result = userManagementServiceBlockingStub
            .isUserPresent( UserEmailAddressVO.newBuilder().setEmailAddress( userDetails.getEmailAddress() ).build() ).getResult();

    }
}
