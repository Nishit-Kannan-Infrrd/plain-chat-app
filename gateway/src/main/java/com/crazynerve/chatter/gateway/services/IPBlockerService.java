package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.vos.BlackListedIPAddressRequest;
import com.crazynerve.chatter.gateway.vos.BlackListedIPAddressResponse;


public interface IPBlockerService
{
    BlackListedIPAddressResponse blackListIPAddress( BlackListedIPAddressRequest blackListedIPAddressRequest );
    BlackListedIPAddressResponse findBlackListedIPAddressByIPAddress(String ipAddress);
}
