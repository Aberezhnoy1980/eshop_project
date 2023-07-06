package ru.aberezhnoy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aberezhnoy.service.dto.OrderStatus;

@RabbitListener(queues = "new.order.queue")
public class OrderReceiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderReceiver.class);

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public OrderReceiver(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitHandler
    public void receive(OrderStatus orderStatus) {
        LOGGER.info("New order received for processing '{}'", orderStatus.getOrderId());

        new Thread(() -> {
            for (OrderStatus.OrderStatusValue status : OrderStatus.OrderStatusValue.values()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Sending next status {} for order {}", status, orderStatus.getOrderId());
                orderStatus.setStatus(status.toString());
                rabbitTemplate.convertAndSend("order.exchange", "processed_order", orderStatus);
            }
        }).start();
    }
}
