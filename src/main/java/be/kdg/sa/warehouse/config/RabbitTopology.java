package be.kdg.sa.warehouse.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {
    public static final String DIRECT_EXCHANGE = "land-exchange";
    public static final String DIRECT_QUEUE_DELIVERY = "delivery-queue";
    public static final String DIRECT_QUEUE_FIFO_DELIVERY = "fifo-delivery-queue";
    public static final String DIRECT_QUEUE_MATERIAL = "material-queue";
    public static final String DIRECT_QUEUE_SELLER = "seller-queue";

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Queue directQueueDelivery(){
        return new Queue(DIRECT_QUEUE_DELIVERY,false);
    }


    @Bean
    public Binding directBindingDelivery(DirectExchange exchange, Queue directQueueDelivery){
        return BindingBuilder.bind(directQueueDelivery).to(exchange).with("delivery.queue");

    }

    @Bean
    public Queue directQueueMaterial(){
        return new Queue(DIRECT_QUEUE_MATERIAL,false);
    }

    @Bean
    public Binding directBindingMaterial(DirectExchange exchange, Queue directQueueMaterial){
        return BindingBuilder.bind(directQueueMaterial).to(exchange).with("material.queue");

    }

    @Bean
    public Queue directQueueSeller(){
        return new Queue(DIRECT_QUEUE_SELLER,false);
    }


    @Bean
    public Binding directBindingSeller(DirectExchange exchange, Queue directQueueSeller){
        return BindingBuilder.bind(directQueueSeller).to(exchange).with("seller.queue");

    }

    @Bean
    public Queue directQueueFifo(){
        return new Queue(DIRECT_QUEUE_FIFO_DELIVERY,false);
    }


    @Bean
    public Binding directBindingsFifo(DirectExchange exchange, Queue directQueueFifo){
        return BindingBuilder.bind(directQueueFifo).to(exchange).with("fifo.queue");

    }
}
