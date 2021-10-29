package com.crazynerve.chatter.ipblockerservice.services;

import com.crazynerve.chatter.ipblocker.models.BlackListIPAddressVO;
import com.crazynerve.chatter.ipblocker.models.BlackListedIpAddressResponseVO;
import com.crazynerve.chatter.ipblocker.services.IpBlockerServiceGrpc;
import com.crazynerve.chatter.ipblockerservice.entities.BlackListedIPAddressEntity;
import com.crazynerve.chatter.ipblockerservice.repositories.BlackListIPAddressRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;


@GRpcService
public class IPBlockerServiceImpl extends IpBlockerServiceGrpc.IpBlockerServiceImplBase
{
    private static final Logger LOGGER = Logger.getLogger( IPBlockerServiceImpl.class.getName() );

    private BlackListIPAddressRepository blackListIPAddressRepository;


    @Autowired
    public void setBlackListIPAddressRepository( BlackListIPAddressRepository blackListIPAddressRepository )
    {
        this.blackListIPAddressRepository = blackListIPAddressRepository;
    }


    @Override
    public void blackListIpAddress( BlackListIPAddressVO request,
        StreamObserver<BlackListedIpAddressResponseVO> responseObserver )
    {
        LOGGER.info( () -> "Blacklisting IP Address " + request.getIpAddress() );
        // add to the repository
        if ( isIpAddressPresent( request.getIpAddress() ) ) {
            Status status = Status.ALREADY_EXISTS
                .withDescription( "IP Address " + request.getIpAddress() + " is already blacklisted." );
            responseObserver.onError( status.asRuntimeException() );
            return;
        } else {
            BlackListedIPAddressEntity blackListedIPAddressEntity = new BlackListedIPAddressEntity();
            blackListedIPAddressEntity.setIpAddress( request.getIpAddress() );
            BlackListedIPAddressEntity newBlackListedIPAddressEntity = blackListIPAddressRepository
                .save( blackListedIPAddressEntity );
            responseObserver.onNext(
                BlackListedIpAddressResponseVO.newBuilder().setIpAddressId( newBlackListedIPAddressEntity.getIpAddressId() )
                    .setIpAddress( newBlackListedIPAddressEntity.getIpAddress() ).build() );
            responseObserver.onCompleted();
        }
    }


    @Override
    public void getBlackListedIpAddressByIp( BlackListIPAddressVO request,
        StreamObserver<BlackListedIpAddressResponseVO> responseObserver )
    {
        LOGGER.info( () -> "Finding IP Address details " + request.getIpAddress() );
        BlackListedIPAddressEntity entity = blackListIPAddressRepository.findByIpAddress( request.getIpAddress() )
            .orElse( null );
        if ( entity != null ) {
            responseObserver.onNext( BlackListedIpAddressResponseVO.newBuilder().setIpAddressId( entity.getIpAddressId() )
                .setIpAddress( entity.getIpAddress() ).build() );
            responseObserver.onCompleted();
        } else {
            Status status = Status.NOT_FOUND.withDescription( "Ip address " + request.getIpAddress() + " is not blacklisted." );
            responseObserver.onError( status.asRuntimeException() );
            return;
        }
    }


    private boolean isIpAddressPresent( String ipAddress )
    {
        LOGGER.fine( () -> "Checking if IP Address " + ipAddress + " is blocked." );
        return blackListIPAddressRepository.findByIpAddress( ipAddress ).isPresent();
    }
}
