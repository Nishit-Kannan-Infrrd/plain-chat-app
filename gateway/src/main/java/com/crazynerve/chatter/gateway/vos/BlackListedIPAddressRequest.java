package com.crazynerve.chatter.gateway.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedIPAddressRequest
{
    private String ipAddress;
}
