package com.notification.email;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@AllArgsConstructor
@RequiredArgsConstructor
public abstract class AbstractEmailService<T> {

    private final JavaMailSender javaMailSender;
    private final NotificationContentBuilder<T> notificationContentBuilder;
    private final String fromEmail;


        public void sendEmail(T notification, String subject) {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(getRecipientEmail(notification));
            messageHelper.setSubject(subject);

            String content = notificationContentBuilder.buildContent(notification);
            messageHelper.setText(content, true);

        };

        javaMailSender.send(messagePreparator);
    }

    public abstract String getRecipientEmail(T notification);
}
