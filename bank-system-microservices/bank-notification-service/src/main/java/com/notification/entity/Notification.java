package com.notification.entity;

import com.notification.dto.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private LocalDateTime notificationTime;
    private NotificationType notificationType;
    private String details;
}