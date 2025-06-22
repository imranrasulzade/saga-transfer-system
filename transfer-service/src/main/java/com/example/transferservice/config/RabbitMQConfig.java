package com.example.transferservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchanges
    @Bean public FanoutExchange transferStatusExchange() {
        return new FanoutExchange("transfer.status");
    }

    @Bean public FanoutExchange notificationExchange() {
        return new FanoutExchange("notification");
    }

    // Queues
    @Bean public Queue successQueue() { return new Queue("transfer.success", true); }
    @Bean public Queue failedQueue() { return new Queue("transfer.failed", true); }

    // Bindings
    @Bean public Binding bindSuccess() {
        return BindingBuilder.bind(successQueue()).to(transferStatusExchange());
    }

    @Bean public Binding bindFailed() {
        return BindingBuilder.bind(failedQueue()).to(transferStatusExchange());
    }

    // JSON converter
    @Bean
    public MessageConverter jsonConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
