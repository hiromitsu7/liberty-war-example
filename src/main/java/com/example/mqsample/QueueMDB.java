package com.example.mqsample;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "QueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue1"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue") })
public class QueueMDB implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(QueueMDB.class);

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