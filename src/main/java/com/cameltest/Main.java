package com.cameltest;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addComponent("activeMQ",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        camelContext.addRoutes(new QueueRoute());
        camelContext.start();
        Thread.sleep(4_000);
        camelContext.close();
    }
}
