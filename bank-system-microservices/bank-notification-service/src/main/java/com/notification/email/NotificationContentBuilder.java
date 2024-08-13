package com.notification.email;

public interface NotificationContentBuilder<T> {
    String buildContent(T notification);
}
