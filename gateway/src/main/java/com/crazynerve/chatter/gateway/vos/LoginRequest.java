package com.crazynerve.chatter.gateway.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest implements Serializable
{
    private String emailAddress;
    private String password;
}
