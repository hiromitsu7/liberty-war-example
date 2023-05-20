package com.example.mqsample;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnectionFactory;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/put2topic")
public class MqTopicResource {

    @Resource(name = "jms/tcf1")
    private TopicConnectionFactory factory;

    @Resource(name = "jms/topic1")
    private Topic topic;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response enqueue() {

        if (topic == null) {
            throw new IllegalArgumentException("topic is null");
        }

        try (Connection con = factory.createConnection();) {
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer p = ses.createProducer(topic);
            Message m = ses.createTextMessage("test");
            p.send(m);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }
}
