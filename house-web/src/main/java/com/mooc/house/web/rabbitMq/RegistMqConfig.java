package com.mooc.house.web.rabbitMq;

import com.mooc.house.common.constants.MQMsg;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;


public class RegistMqConfig {

    /**
     * 创建队列
     *
     * @return
     */
    @Bean
    public Queue queueMessage() {
        return new Queue(MQMsg.queue_to_mysql);
    }

    /**
     * 创建队列
     *
     * @return
     */
    @Bean
    public Queue queueMessages() {
        return new Queue(MQMsg.queue_to_email);
    }

    /**
     * 创建交换机
     *
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MQMsg.EXCHANGE);
    }

    /**
     * 将队列绑定到交换机上
     *
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("regist.#");
    }

    /**
     * 将队列绑定到交换机上
     *
     * @param queueMessages
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("regist.#");
    }


}
