package com.dearbella.server.service.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.io.IOException;

public interface FCMService {
    public void sendMessageByTopic(String title, String body, String topic)throws IOException, FirebaseMessagingException;
}
