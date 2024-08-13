package com.notification.repo

import com.notification.entity.Notification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : MongoRepository<Notification, String>{
}