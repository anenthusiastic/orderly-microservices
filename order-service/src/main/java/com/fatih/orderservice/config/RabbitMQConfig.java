package com.fatih.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${order.queue.name}")
    private String queue;

    @Value("${order.exchange.name}")
    private String exchange;

    @Value("${order.routing.key}")
    private String routingKey;

    @Bean
    public Queue orderQueue() {
        return new Queue(queue);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(routingKey);
    }
}
