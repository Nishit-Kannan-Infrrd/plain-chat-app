package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.vos.BlackListedIPAddressRequest;
import com.crazynerve.chatter.gateway.vos.BlackListedIPAddressResponse;
import com.crazynerve.chatter.ipblocker.models.BlackListIPAddressVO;
import com.crazynerve.chatter.ipblocker.models.BlackListedIpAddressResponseVO;
import com.crazynerve.chatter.ipblocker.services.IpBlockerServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class IPBlockerServiceImpl implements IPBlockerService
{
    private static final Logger LOGGER = Logger.getLogger( IPBlockerServiceImpl.class.getName() );

    @GrpcClient ("ipblocker-service") private IpBlockerServiceGrpc.IpBlockerServiceBlockingStub ipBlockerServiceBlockingStub;


    @Override
    public BlackListedIPAddressResponse blackListIPAddress( BlackListedIPAddressRequest blackListedIPAddressRequest )
    {
        LOGGER.info( () -> "Blacklisting " + blackListedIPAddressRequest );
        BlackListedIpAddressResponseVO blackListedIpAddressResponseVO = ipBlockerServiceBlockingStub.blackListIpAddress(
            BlackListIPAddressVO.newBuilder().setIpAddress( blackListedIPAddressRequest.getIpAddress() ).build() );
        return new BlackListedIPAddressResponse( blackListedIpAddressResponseVO.getIpAddressId(),
            blackListedIpAddressResponseVO.getIpAddress() );
    }


    @Override
    public BlackListedIPAddressResponse findBlackListedIPAddressByIPAddress(
        String ipAddress )
    {
        LOGGER.info( () -> "Finding if " + ipAddress + " is  blocked." );
        BlackListedIpAddressResponseVO blackListedIpAddressByIp = ipBlockerServiceBlockingStub.getBlackListedIpAddressByIp(
            BlackListIPAddressVO.newBuilder().setIpAddress( ipAddress ).build() );
        return new BlackListedIPAddressResponse( blackListedIpAddressByIp.getIpAddressId(),
            blackListedIpAddressByIp.getIpAddress() );
    }
}
