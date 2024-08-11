package com.bank.transaction.kafka;


import com.bank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountTransactionConsumer {

    private final TransactionService service;

    @KafkaListener(topics = "$$$$$")
    public void consumeAccountTransaction(AccountTransactionConfirmation accountTransactionConfirmation){
        service.createTransaction(accountTransactionConfirmation);
    }
}
