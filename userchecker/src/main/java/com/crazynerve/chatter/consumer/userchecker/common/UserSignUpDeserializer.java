package com.crazynerve.chatter.consumer.userchecker.common;

import com.crazynerve.chatter.gateway.protos.UserSignUpEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;


public class UserSignUpDeserializer implements Deserializer<UserSignUpEvent>
{
    @Override
    public void configure( Map<String, ?> configs, boolean isKey )
    {
        Deserializer.super.configure( configs, isKey );
    }


    @Override
    public UserSignUpEvent deserialize( String s, byte[] bytes )
    {
        try {
            return UserSignUpEvent.parseFrom( bytes );
        } catch ( InvalidProtocolBufferException e ) {
            throw new RuntimeException("Failed to deserialize UserSignUpEvent.", e);
        }
    }


    @Override
    public UserSignUpEvent deserialize( String topic, Headers headers, byte[] data )
    {
        try {
            return UserSignUpEvent.parseFrom( data );
        } catch ( InvalidProtocolBufferException e ) {
            throw new RuntimeException("Failed to deserialize UserSignUpEvent.", e);
        }
    }


    @Override
    public void close()
    {
        Deserializer.super.close();
    }
}
