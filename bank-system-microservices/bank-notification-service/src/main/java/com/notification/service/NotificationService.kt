package com.notification.service

import com.notification.entity.Notification

interface NotificationService {

    fun createNotification(notification : Notification)
}