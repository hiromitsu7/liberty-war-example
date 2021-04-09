package com.example.mqsample;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "QueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue1"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
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