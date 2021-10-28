package com.crazynerve.chatter.gateway.configurations;

import com.crazynerve.chatter.gateway.protos.UserSignUpEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig
{
    @Value( "${spring.kafka.bootstrap-servers}" )
    private String bootstrapAddress;

    @Value( "${spring.kafka.producer.key-serializer}" )
    private String keySerializer;

    @Value( "${spring.kafka.producer.value-serializer-usersingup}" )
    private String userSignUpValueSerializer;

    @Bean
    public ProducerFactory<String, UserSignUpEvent> signUpEventProducerFactory(){
        Map<String, Object> properties = new HashMap<>();
        properties.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress );
        properties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer );
        properties.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, userSignUpValueSerializer );
        return new DefaultKafkaProducerFactory<>( properties );
    }

    @Bean
    public KafkaTemplate<String, UserSignUpEvent> signUpEventKafkaTemplate(){
        return new KafkaTemplate<>( signUpEventProducerFactory() );
    }
}
