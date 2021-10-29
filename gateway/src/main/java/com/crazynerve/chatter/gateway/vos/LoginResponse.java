package com.crazynerve.chatter.gateway.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse
{
    private String userId;
    private String emailAddress;
}
