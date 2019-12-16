package org.dromara.myth.demo.springcloud.order.configuration;

import org.dromara.myth.rabbitmq.service.RabbitmqSendServiceImpl;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxi
 * @date 2019-09-26 21:55
 */
@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean(name = "inventory")
    public Queue inventory(){

        return new Queue("inventory");
    }

    @Bean(name = "account")
    public Queue account(){

        return new Queue("account");
    }

    @Bean(name = "inventoryBinging")
    public Binding inventoryBinging(DirectExchange directExchange, @Qualifier("inventory") Queue inventory){
        return BindingBuilder.bind(inventory).to(directExchange).withQueueName();
    }

    @Bean(name = "accountBinging")
    public Binding accountBinging(DirectExchange directExchange, @Qualifier("account") Queue account){
        return BindingBuilder.bind(account).to(directExchange).withQueueName();
    }

    @Bean
    public RabbitmqSendServiceImpl amqpTemplate(AmqpTemplate amqpTemplate){

        RabbitmqSendServiceImpl rabbitmqSendService = new RabbitmqSendServiceImpl();
        rabbitmqSendService.setAmqpTemplate(amqpTemplate);
        return rabbitmqSendService;
    }

}
