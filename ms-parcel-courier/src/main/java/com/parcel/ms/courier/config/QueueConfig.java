package com.parcel.ms.courier.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class QueueConfig {

    private final String dlqName;
    private final String queueName;

    @Autowired
    public QueueConfig(
            @Value("${queue.courier.canceled-q}") String queueName,
            @Value("${queue.courier.canceled-ex}") String dlqName
    ) {
        this.queueName = queueName;
        this.dlqName = dlqName;
    }

    @Bean
    @Primary
    public Queue queue(
            @Qualifier("dlqExchange")DirectExchange dlqExchange
    ) {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlqExchange.getName())
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    @Bean
    @Primary
    public DirectExchange exchange() {
        return new DirectExchange(queueName + ".dlx");
    }

    @Bean
    @Qualifier("dlq")
    public Queue dlq() {
        return new Queue(dlqName);
    }

    @Bean
    @Qualifier("dlqExchange")
    public DirectExchange dlqExchange() {
        return new DirectExchange(dlqName + ".dlx");
    }

    @Bean
    public Binding dlqBinding(
            @Qualifier("dlq") Queue dlq,
            @Qualifier("dlqExchange") DirectExchange dlqExchange
    ){
        return BindingBuilder.bind(dlq).to(dlqExchange).with(dlqName);
    }

    @Bean
    public Binding binding(
            Queue queue,
            DirectExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

}
