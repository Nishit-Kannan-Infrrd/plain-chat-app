package com.crazynerve.chatter.gateway.common;

import com.crazynerve.chatter.gateway.protos.UserSignUpEvent;
import org.apache.kafka.common.serialization.Serializer;


public class UserSignUpSerializer implements Serializer<UserSignUpEvent>
{
    @Override
    public byte[] serialize( String topic, UserSignUpEvent data )
    {
        return data.toByteArray();
    }
}
