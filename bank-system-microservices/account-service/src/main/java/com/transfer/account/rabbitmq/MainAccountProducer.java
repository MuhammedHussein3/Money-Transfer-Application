package com.transfer.account.rabbitmq;


import com.transfer.account.message.AccountCreateNotification;
import com.transfer.account.message.MoneyTransferNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainAccountProducer {

    public final RabbitTemplate rabbitTemplate;

    public void sendMoneyTransferMessage(
            MoneyTransferNotification moneyTransferNotification
    ) {
        rabbitTemplate.convertAndSend("$$", moneyTransferNotification);
    }

    public void sendAccountCreationMessage(
          AccountCreateNotification notification
    ) {
        rabbitTemplate.convertAndSend("$$", notification);
    }
}

