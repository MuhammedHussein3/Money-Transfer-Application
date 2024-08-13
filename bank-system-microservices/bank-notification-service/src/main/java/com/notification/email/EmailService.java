package com.notification.email;

import com.notification.message.AccountCreateNotification;
import com.notification.message.MoneyTransferNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmailId;

//    public void sendAccountCreationNotification(AccountCreateNotification notification) {
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//            messageHelper.setFrom(fromEmailId);
//            messageHelper.setTo(notification.getEmail());
//            messageHelper.setSubject("Welcome to Our Bank, " + notification.getFirstName() + "!");
//
//            String content = buildEmailContent1(notification);
//            messageHelper.setText(content, true);
//
//            // Attach a logo or any other resources if needed
//            // messageHelper.addInline("logo", new ClassPathResource("path/to/logo.png"));
//        };
//
//        javaMailSender.send(messagePreparator);
//    }
//    public void sendMoneyTransferNotification(MoneyTransferNotification notification) {
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//            messageHelper.setFrom(fromEmailId);
//            messageHelper.setTo(notification.getEmail());
//            messageHelper.setSubject("Money Transfer Notification");
//
//            String content = buildEmailContent2(notification);
//            messageHelper.setText(content, true);
//        };
//
//        javaMailSender.send(messagePreparator);
//    }
//
//    private String buildEmailContent2(MoneyTransferNotification notification) {
//        Context context = new Context();
//        context.setVariable("fromAccountId", notification.getFromAccountId());
//        context.setVariable("toAccountId", notification.getToAccountId());
//        context.setVariable("amount", notification.getAmount());
//        context.setVariable("balanceAfterTransaction", notification.getBalanceAfterTransaction());
//        context.setVariable("timestamp", notification.getTimestamp());
//
//        return templateEngine.process("money_transfer_notification", context);
//    }
//
//    private String buildEmailContent1(AccountCreateNotification notification) {
//        Context context = new Context();
//        context.setVariable("firstName", notification.getFirstName());
//        context.setVariable("lastName", notification.getLastName());
//        context.setVariable("accountType", notification.getAccountType());
//        context.setVariable("balance", notification.getBalance());
//
//        return templateEngine.process("account_creation_email", context);
//    }
}

