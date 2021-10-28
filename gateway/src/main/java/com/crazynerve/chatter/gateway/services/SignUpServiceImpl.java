package com.crazynerve.chatter.gateway.services;

import com.crazynerve.chatter.gateway.common.Events;
import com.crazynerve.chatter.gateway.protos.UserSignUpEvent;
import com.crazynerve.chatter.gateway.protos.common.UserDetails;
import com.crazynerve.chatter.gateway.vos.LoginRequest;
import com.google.protobuf.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Instant;
import java.util.logging.Logger;


@Service
public class SignUpServiceImpl implements SignUpService
{
    private static final Logger LOGGER = Logger.getLogger( SignUpServiceImpl.class.getName() );

    @Value ("${spring.kafka.usersignup.events.topic}") private String userSignUpTopic;

    private KafkaTemplate<String, UserSignUpEvent> userSignUpEventKafkaTemplate;


    @Autowired
    public void setUserSignUpEventKafkaTemplate( KafkaTemplate<String, UserSignUpEvent> userSignUpEventKafkaTemplate )
    {
        this.userSignUpEventKafkaTemplate = userSignUpEventKafkaTemplate;
    }


    @Override
    public void publishUserSignUpEvent( LoginRequest loginRequest )
    {
        LOGGER.info( () -> "Login attempted " + loginRequest );
        UserDetails userDetails = UserDetails.newBuilder().setEmailAddress( loginRequest.getEmailAddress() )
            .setPassword( loginRequest.getPassword() ).build();
        Instant instant = Instant.now();
        UserSignUpEvent userSignUpEvent = UserSignUpEvent.newBuilder().setEvent( Events.USER_SIGN_UP_EVENT )
            .setTimeStamp( Timestamp.newBuilder().setSeconds( instant.getEpochSecond() ).setNanos( instant.getNano() ).build() )
            .setUserDetails( userDetails ).build();
        ListenableFuture<SendResult<String, UserSignUpEvent>> eventFuture = userSignUpEventKafkaTemplate
            .send( userSignUpTopic, userSignUpEvent );
        eventFuture.addCallback( new ListenableFutureCallback<SendResult<String, UserSignUpEvent>>()
        {
            @Override
            public void onFailure( Throwable ex )
            {
                LOGGER.throwing( this.getClass().getName(), "", ex );
            }


            @Override
            public void onSuccess( SendResult<String, UserSignUpEvent> result )
            {
                LOGGER.info( () -> "Message sent " + userSignUpEvent.getEvent() + " with time " + userSignUpEvent.getTimeStamp()
                    + " with offset " + result.getRecordMetadata().offset() );
            }
        } );
    }
}
