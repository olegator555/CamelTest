package com.cameltest;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class ContextConfigurator {
    private String queueName;
    private String brokerURL;

    public ContextConfigurator(String queueName, String brokerURL) {
        this.queueName = queueName;
        this.brokerURL = brokerURL;
    }

    public ContextConfigurator() {
    }
    public void configure(QueueRoute queueRoute) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        if(queueName != null && brokerURL != null) {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
            camelContext.addComponent(queueName,
                    JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        }

        camelContext.addRoutes(queueRoute);
        camelContext.start();
        Thread.sleep(4_000);
        camelContext.close();
    }
}
