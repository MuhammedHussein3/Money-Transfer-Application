package com.transfer.account.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaAccountConfig {


    @Bean
    public NewTopic accountTopic(){
        return TopicBuilder
                .name("$$$$$")
                .build();
    }

}
