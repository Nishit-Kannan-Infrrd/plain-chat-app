package com.crazynerve.chatter.gateway.controllers;

import com.crazynerve.chatter.gateway.services.IPBlockerService;
import com.crazynerve.chatter.gateway.vos.BlackListedIPAddressRequest;
import com.crazynerve.chatter.gateway.vos.BlackListedIPAddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@RequestMapping ("/ipaddress")
public class IPController
{
    private static final Logger LOGGER = Logger.getLogger( IPController.class.getName() );

    private IPBlockerService ipBlockerService;


    @Autowired
    public void setIpBlockerService( IPBlockerService ipBlockerService )
    {
        this.ipBlockerService = ipBlockerService;
    }


    @RequestMapping ("/block")
    public ResponseEntity<BlackListedIPAddressResponse> blackListIpAddress(
        @RequestBody BlackListedIPAddressRequest blackListedIPAddressRequest )
    {
        LOGGER.info( () -> "Blacklisting " + blackListedIPAddressRequest );
        return new ResponseEntity<>( ipBlockerService.blackListIPAddress( blackListedIPAddressRequest ), HttpStatus.CREATED );
    }


    @GetMapping("/{ipAddress}")
    public ResponseEntity<BlackListedIPAddressResponse> findIpAddressDetails(
        @PathVariable (name = "ipAddress") String ipAddress )
    {
        LOGGER.info( () -> "Checking if " + ipAddress + " is blocked." );
        return new ResponseEntity<>( ipBlockerService.findBlackListedIPAddressByIPAddress( ipAddress ), HttpStatus.OK );
    }
}
