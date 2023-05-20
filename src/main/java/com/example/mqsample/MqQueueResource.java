package com.example.mqsample;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.Session;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/put2queue")
public class MqQueueResource {

    @Resource(name = "jms/qcf1")
    private QueueConnectionFactory factory;

    @Resource(name = "jms/queue1")
    private Queue queue;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response enqueue() {
        try (Connection con = factory.createConnection();) {
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer p = ses.createProducer(queue);
            Message m = ses.createTextMessage("test");
            p.send(m);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }
}
