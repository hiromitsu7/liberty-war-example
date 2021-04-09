package com.example.mqsample;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
