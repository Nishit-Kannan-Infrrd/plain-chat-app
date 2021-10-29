package com.crazynerve.chatter.usermanagement.services;

import com.crazynerve.chatter.usermanagement.entities.UserEntity;
import com.crazynerve.chatter.usermanagement.models.UserAuthenticationVO;
import com.crazynerve.chatter.usermanagement.models.UserEmailAddressVO;
import com.crazynerve.chatter.usermanagement.models.UserResponseVO;
import com.crazynerve.chatter.usermanagement.repositories.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;


@GRpcService
public class UserManagementServiceImpl extends UserManagementServiceGrpc.UserManagementServiceImplBase
{
    private static final Logger LOGGER = Logger.getLogger( UserManagementServiceImpl.class.getName() );

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository( UserRepository userRepository )
    {
        this.userRepository = userRepository;
    }


    @Override
    public void addUser( UserAuthenticationVO request, StreamObserver<UserResponseVO> responseObserver )
    {
        LOGGER.info( () -> "Adding user with email address " + request.getEmailAddress() );
        // check if email address is already present
        if ( !isUserPresent( request.getEmailAddress() ) ) {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmailAddress( request.getEmailAddress() );
            userEntity.setPassword( request.getPassword() );
            UserEntity newUserEntity = userRepository.save( userEntity );
            responseObserver.onNext( UserResponseVO.newBuilder().setUserId( newUserEntity.getUserId() )
                .setEmailAddress( newUserEntity.getEmailAddress() ).build() );
            responseObserver.onCompleted();
        } else {
            Status status = Status.ALREADY_EXISTS
                .withDescription( "Email address " + request.getEmailAddress() + " already exists." );
            responseObserver.onError( status.asRuntimeException() );
            return;
        }

    }


    @Override
    public void getUserDetails( UserEmailAddressVO request, StreamObserver<UserResponseVO> responseObserver )
    {
        UserEntity userEntity = userRepository.findByEmailAddress( request.getEmailAddress() ).orElse( null );
        if ( userEntity != null ) {
            UserResponseVO userResponseVO = UserResponseVO.newBuilder().setUserId( userEntity.getUserId() )
                .setEmailAddress( userEntity.getEmailAddress() ).build();
            responseObserver.onNext( userResponseVO );
            responseObserver.onCompleted();
        } else {
            Status status = Status.NOT_FOUND.withDescription( "No records for email address " + request.getEmailAddress() );
            responseObserver.onError( status.asRuntimeException() );
            return;
        }

    }

    private boolean isUserPresent( String emailAddress )
    {
        LOGGER.fine( () -> "Checking for email address " + emailAddress );
        return userRepository.findByEmailAddress( emailAddress ).isPresent();
    }


}
