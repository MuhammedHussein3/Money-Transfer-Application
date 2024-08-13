package com.notification.email;

import com.notification.message.MoneyTransferNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferEmailService extends AbstractEmailService<MoneyTransferNotification> {

    public MoneyTransferEmailService(
            JavaMailSender javaMailSender,
            MoneyTransferContentBuilder contentBuilder,
            @Value("${spring.mail.username}") String fromEmailId
    ){
        super(javaMailSender, contentBuilder, fromEmailId);
    }

    @Override
    public String getRecipientEmail(MoneyTransferNotification notification) {
        return notification.getEmail();
    }

    public void sendMoneyTransferNotification(MoneyTransferNotification notification) {
        sendEmail(notification, "Money Transfer Notification");
    }
}
