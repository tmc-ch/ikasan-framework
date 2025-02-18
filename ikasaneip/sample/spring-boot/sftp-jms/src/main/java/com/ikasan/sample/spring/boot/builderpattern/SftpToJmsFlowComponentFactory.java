package com.ikasan.sample.spring.boot.builderpattern;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.ikasan.builder.BuilderFactory;
import org.ikasan.component.endpoint.jms.spring.producer.SpringMessageProducerConfiguration;
import org.ikasan.endpoint.sftp.consumer.SftpConsumerConfiguration;
import org.ikasan.spec.component.endpoint.Consumer;
import org.ikasan.spec.component.endpoint.Producer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import jakarta.jms.ConnectionFactory;

@Configuration
public class SftpToJmsFlowComponentFactory
{
    @Resource
    private BuilderFactory builderFactory;

    @Value("${jms.provider.url}")
    private String brokerUrl;

    @Bean(name = "sftpConsumerConfiguration")
    @ConfigurationProperties(prefix = "sftp.to.jms.flow.sftp.consumer")
    public SftpConsumerConfiguration sftpConsumerConfiguration()
    {
        return new SftpConsumerConfiguration();
    }

    @Bean
    public Consumer sftpConsumer(@Qualifier("sftpConsumerConfiguration") SftpConsumerConfiguration sftpConsumerConfiguration)
    {
        return builderFactory.getComponentBuilder()
                             .sftpConsumer()
                             .setConfiguration(sftpConsumerConfiguration)
                             .setConfiguredResourceId("configuredResourceId")
                             .setScheduledJobGroupName("SftpToLogFlow")
                             .setScheduledJobName("SftpConsumer").build();
    }

    @Bean(name = "jmsProducerConfiguration")
    @ConfigurationProperties(prefix = "sftp.to.jms.flow.jms.producer")
    public SpringMessageProducerConfiguration jmsProducerConfiguration()
    {
        return new SpringMessageProducerConfiguration();
    }


    @Bean
    public Producer jmsProducer(@Qualifier("jmsProducerConfiguration") SpringMessageProducerConfiguration jmsProducerConfiguration)
    {
        ConnectionFactory producerConnectionFactory = new ActiveMQXAConnectionFactory(brokerUrl);

        return builderFactory.getComponentBuilder().jmsProducer().setConnectionFactory(producerConnectionFactory)
                             .setConfiguration(jmsProducerConfiguration)
                             .setConfiguredResourceId("sftpJmsProducer").build();
    }

}
