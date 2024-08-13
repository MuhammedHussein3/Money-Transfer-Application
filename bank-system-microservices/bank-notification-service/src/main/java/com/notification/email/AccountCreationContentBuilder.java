package com.notification.email;

import com.notification.message.AccountCreateNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class AccountCreationContentBuilder implements NotificationContentBuilder<AccountCreateNotification>{

    private final SpringTemplateEngine templateEngine;
    @Override
    public String buildContent(AccountCreateNotification notification) {
        Context context = new Context();
        context.setVariable("firstName", notification.getFirstName());
        context.setVariable("lastName", notification.getLastName());
        context.setVariable("accountType", notification.getAccountType());
        context.setVariable("balance", notification.getBalance());

        return templateEngine.process("account_creation_email", context);
    }
}
