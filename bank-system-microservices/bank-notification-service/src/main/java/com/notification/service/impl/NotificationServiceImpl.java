package com.notification.service.impl;

import com.notification.entity.Notification;
import com.notification.repo.NotificationRepository;
import com.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public void createNotification(
            @NotNull Notification notification
    ) {
        repository.save(notification);
    }
}
