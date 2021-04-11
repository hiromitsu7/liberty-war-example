package com.example.mqsample;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
