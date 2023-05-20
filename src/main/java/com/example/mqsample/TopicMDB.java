package com.example.mqsample;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "TopicMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic1"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic") })
public class TopicMDB implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(TopicMDB.class);

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                logger.info(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}