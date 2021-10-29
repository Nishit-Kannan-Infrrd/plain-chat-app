package com.crazynerve.chatter.consumer.userchecker.consumers;

import com.crazynerve.chatter.gateway.protos.UserSignUpEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class ConsumerServices
{
    private static final Logger LOGGER = Logger.getLogger( ConsumerServices.class.getName() );


    @KafkaListener (topics = "${spring.kafka.usersignup.events.topic}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "userKafkaListenerContainerFactory")
    public void consumeUserSignUpEvents( UserSignUpEvent userSignUpEvent,
        @Header (KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey, @Header (KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header (KafkaHeaders.RECEIVED_PARTITION_ID) String partitionId, Acknowledgment acknowledgment )
    {
        LOGGER.info(
            () -> "User sign up event " + userSignUpEvent + ". Key: " + messageKey + " from topic: " + topic + " at partition: "
                + partitionId );
        acknowledgment.acknowledge();
    }
}
