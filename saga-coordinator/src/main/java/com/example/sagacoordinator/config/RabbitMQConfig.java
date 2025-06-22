package com.example.sagacoordinator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // ------------ Exchanges ------------
    @Bean
    public FanoutExchange debitExchange() {
        return new FanoutExchange("wallet.debit");
    }

    @Bean
    public FanoutExchange creditExchange() {
        return new FanoutExchange("wallet.credit");
    }

    @Bean
    public FanoutExchange refundExchange() {
        return new FanoutExchange("wallet.refund");
    }

    @Bean
    public FanoutExchange transferStatusExchange() {
        return new FanoutExchange("transfer.status");
    }

    // ------------ Queues ------------
    @Bean public Queue walletDebitedQueue()       { return new Queue("wallet.debited", true); }
    @Bean public Queue walletCreditedQueue()      { return new Queue("wallet.credited", true); }
    @Bean public Queue walletDebitFailedQueue()   { return new Queue("wallet.debit.failed", true); }
    @Bean public Queue walletCreditFailedQueue()  { return new Queue("wallet.credit.failed", true); }
    @Bean public Queue walletRefundedQueue()      { return new Queue("wallet.refunded", true); }

    // ------------ Bindings ------------
    @Bean public Binding bindWalletDebited() {
        return BindingBuilder.bind(walletDebitedQueue()).to(debitExchange());
    }

    @Bean public Binding bindWalletCredited() {
        return BindingBuilder.bind(walletCreditedQueue()).to(creditExchange());
    }

    @Bean public Binding bindWalletDebitFailed() {
        return BindingBuilder.bind(walletDebitFailedQueue()).to(debitExchange());
    }

    @Bean public Binding bindWalletCreditFailed() {
        return BindingBuilder.bind(walletCreditFailedQueue()).to(creditExchange());
    }

    @Bean public Binding bindWalletRefunded() {
        return BindingBuilder.bind(walletRefundedQueue()).to(refundExchange());
    }

    // ------------ JSON Converter ------------
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
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
