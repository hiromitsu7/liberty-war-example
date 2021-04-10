package com.example.mqsample;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "TopicMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic1"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class TopicMDB implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(TopicMDB.class);

    @Timed(name = "TopicMDBOnMessageTime")
    @Counted(absolute = true)
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                logger.info(text);
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}