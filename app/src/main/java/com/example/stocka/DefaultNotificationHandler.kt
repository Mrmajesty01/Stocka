package com.example.stocka

import javax.inject.Inject

class DefaultNotificationHandler @Inject constructor() : NotificationHandler {
    override fun showNotification(content: String, title: String) {
        // Implement your notification logic here
    }
}