package be.kdg.sa.warehouse.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {
    public static final String TOPIC_EXCHANGE = "truck-exchange";
    public static final String TOPIC_QUEUE_TRUCK = "truck-appointment-queue";

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueueTruck(){
        return new Queue(TOPIC_QUEUE_TRUCK,false);
    }


    @Bean
    public Binding topicBindingTruck(TopicExchange exchange, Queue topicQueueTruck){
        return BindingBuilder.bind(topicQueueTruck).to(exchange).with("truck.appointment.*");

    }
}
