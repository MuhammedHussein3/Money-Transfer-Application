package com.notification.email;

import com.notification.message.AccountCreateNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationEmailService extends AbstractEmailService<AccountCreateNotification> {

    public AccountCreationEmailService(JavaMailSender javaMailSender,
                                       AccountCreationContentBuilder contentBuilder,
                                       @Value("${spring.mail.username}") String fromEmailId) {
        super(javaMailSender, contentBuilder, fromEmailId);
    }

    @Override
    public String getRecipientEmail(AccountCreateNotification notification) {
        return notification.getEmail();
    }

    public void sendAccountCreationNotification(AccountCreateNotification notification) {
        sendEmail(notification, "Welcome to Our Bank, " + notification.getFirstName() + "!");
    }
}
