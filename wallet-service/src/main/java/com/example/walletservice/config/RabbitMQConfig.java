package com.example.walletservice.config;

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
    @Bean public FanoutExchange debitExchange() { return new FanoutExchange("wallet.debit"); }
    @Bean public FanoutExchange creditExchange() { return new FanoutExchange("wallet.credit"); }
    @Bean public FanoutExchange refundExchange() { return new FanoutExchange("wallet.refund"); }

    // Queues
    @Bean public Queue debitQueue() { return new Queue("wallet.debit.request", true); }
    @Bean public Queue creditQueue() { return new Queue("wallet.credit.request", true); }
    @Bean public Queue refundQueue() { return new Queue("wallet.refund.request", true); }

    // Bindings
    @Bean public Binding bindDebit() {
        return BindingBuilder.bind(debitQueue()).to(debitExchange());
    }

    @Bean public Binding bindCredit() {
        return BindingBuilder.bind(creditQueue()).to(creditExchange());
    }

    @Bean public Binding bindRefund() {
        return BindingBuilder.bind(refundQueue()).to(refundExchange());
    }

    // Jackson JSON message converter
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
