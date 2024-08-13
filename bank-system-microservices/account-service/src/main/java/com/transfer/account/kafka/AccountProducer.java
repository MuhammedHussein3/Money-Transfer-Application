package com.transfer.account.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountProducer {


    private final KafkaTemplate<String, AccountTransactionConfirmation> kafkaTemplate;
    public void sendAccountConfirmationToTransaction(AccountTransactionConfirmation accountTransactionConfirmation){
        log.info("Sending Transaction Confirmation from Account");

        Message<AccountTransactionConfirmation> message = MessageBuilder
                .withPayload(accountTransactionConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "$$")
                .build();

        kafkaTemplate.send(message);
    }
}
