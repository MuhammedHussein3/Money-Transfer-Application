package com.notification.rabbitmq;

import com.notification.email.AccountCreationEmailService;
import com.notification.email.EmailService;
import com.notification.email.MoneyTransferEmailService;
import com.notification.message.AccountCreateNotification;
import com.notification.message.MoneyTransferNotification;
import com.notification.service.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainAccountConsumer {



    private final AccountCreationEmailService accountCreationEmailService;
    private final MoneyTransferEmailService moneyTransferEmailService;


    @RabbitListener(queues = "$$")
    public void moneyTransferNotificationConsume(MoneyTransferNotification moneyTransferNotification) throws ListenerExecutionFailedException, MessagingException{
      moneyTransferEmailService.sendMoneyTransferNotification(moneyTransferNotification);
      log.info(moneyTransferNotification.toString());
  }

  @RabbitListener(queues = "$$")
  public void accountCreationNotificationConsume(AccountCreateNotification accountCreateNotification) throws ListenerExecutionFailedException, MessagingException{

    accountCreationEmailService.sendEmail(accountCreateNotification, accountCreateNotification.getFirstName());
       log.info(accountCreateNotification.toString());
  }


}
