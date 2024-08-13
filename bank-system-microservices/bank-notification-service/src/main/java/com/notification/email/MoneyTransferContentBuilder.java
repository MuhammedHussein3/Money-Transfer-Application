package com.notification.email;

import com.notification.message.MoneyTransferNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoneyTransferContentBuilder implements NotificationContentBuilder<MoneyTransferNotification> {

    private final SpringTemplateEngine templateEngine;
    @Override
    public String buildContent(MoneyTransferNotification notification) {
                Context context = new Context();
        context.setVariable("fromAccountId", notification.getFromAccountId());
        context.setVariable("toAccountId", notification.getToAccountId());
        context.setVariable("amount", notification.getAmount());
        context.setVariable("balanceAfterTransaction", notification.getBalanceAfterTransaction());
        context.setVariable("timestamp", notification.getTimestamp());
        return templateEngine.process("money_transfer_notification", context);
    }
}
