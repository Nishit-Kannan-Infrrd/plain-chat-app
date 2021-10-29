package com.crazynerve.chatter.consumer.userchecker.configurations;

import com.crazynerve.chatter.gateway.protos.UserSignUpEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConsumerConfiguration
{
    @Value ("${spring.kafka.bootstrap-servers}") private String bootstrapAddress;

    @Value ("${spring.kafka.consumer.group-id}") private String consumerGroupId;

    @Value ("${spring.kafka.producer.key-deserializer}") private String keyDeserializer;

    @Value ("${spring.kafka.producer.value-deserializer-usersingup}") private String userSignUpValueDeserializer;


    @Bean
    public ConsumerFactory<String, UserSignUpEvent> userSignUpEventConsumerFactory()
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress );
        properties.put( ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId );
        properties.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer );
        properties.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, userSignUpValueDeserializer );

        return new DefaultKafkaConsumerFactory<>( properties );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserSignUpEvent>
    userKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserSignUpEvent> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userSignUpEventConsumerFactory());
        factory.getContainerProperties().setAckMode( ContainerProperties.AckMode.MANUAL );
        return factory;
    }
}
