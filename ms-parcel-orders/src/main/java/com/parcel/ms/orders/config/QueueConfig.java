package com.parcel.ms.orders.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    private final String dlqName;
    private final String orderCanceledQueueName;
    private final String orderAssignedQueueName;
    private final String orderOnTheWayQueueName;
    private final String orderDeliveredQueueName;

    private final String DLX_EXCHANGE_MESSAGES;

    @Autowired
    public QueueConfig(
            @Value("${queue.order.order-ex}") String dlqName,
            @Value("${queue.order.onway-q}") String orderOnTheWayQueueName,
            @Value("${queue.order.canceled-q}") String orderCanceledQueueName,
            @Value("${queue.order.assigned-q}") String orderAssignedQueueName,
            @Value("${queue.order.delivered-q}") String orderDeliveredQueueName
            ) {
        DLX_EXCHANGE_MESSAGES  = dlqName + ".dlx";

        this.dlqName = dlqName;
        this.orderCanceledQueueName = orderCanceledQueueName;
        this.orderAssignedQueueName = orderAssignedQueueName;
        this.orderOnTheWayQueueName = orderOnTheWayQueueName;
        this.orderDeliveredQueueName = orderDeliveredQueueName;
    }

    @Bean
    public Queue queueCanceled() {
        return QueueBuilder.durable(orderCanceledQueueName)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    @Bean
    public Queue queueDelivered() {
        return QueueBuilder.durable(orderDeliveredQueueName)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    @Bean
    public Queue queueAssigned() {
        return QueueBuilder.durable(orderAssignedQueueName)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    @Bean
    public Queue queueOnWay() {
        return QueueBuilder.durable(orderOnTheWayQueueName)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    @Bean
    public Binding bindingOrderCanceled(DirectExchange exchange) {
        return BindingBuilder.bind(queueCanceled()).to(exchange).with(queueCanceled().getName());
    }

    @Bean
    public Binding bindingOrderDelivered(DirectExchange exchange) {
        return BindingBuilder.bind(queueDelivered()).to(exchange).with(queueDelivered().getName());
    }

    @Bean
    public Binding bindingOrderAssigned(DirectExchange exchange) {
        return BindingBuilder.bind(queueAssigned()).to(exchange).with(queueAssigned().getName());
    }

    @Bean
    public Binding bindingOrderOnWay(DirectExchange exchange) {
        return BindingBuilder.bind(queueOnWay()).to(exchange).with(queueOnWay().getName());
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(DLX_EXCHANGE_MESSAGES);
    }

    @Bean
    @Qualifier("dlq")
    public Queue dlq() {
        return new Queue(dlqName);
    }

    @Bean
    @Qualifier("dlqExchange")
    public DirectExchange dlqExchange() {
        return new DirectExchange(DLX_EXCHANGE_MESSAGES);
    }

    @Bean
    public Binding dlqBinding(
            @Qualifier("dlq") Queue dlq,
            @Qualifier("dlqExchange") DirectExchange dlqExchange
    ){
        return BindingBuilder.bind(dlq).to(dlqExchange).with(dlqName);
    }

}
