package co.edu.uptc.edakafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import co.edu.uptc.edakafka.model.Order;
import co.edu.uptc.edakafka.utils.JsonUtils;

@Service
public class OrderEventConsumer {
    @Autowired
    private OrderService orderService;
    private JsonUtils jsonUtils = new JsonUtils();

    @KafkaListener(topics = "order_events", groupId = "order_group")
    public void processOrder(String message, @Header(KafkaHeaders.RECEIVED_KEY) String action) {
        Order order = jsonUtils.fromJson(message, Order.class);
        
        switch(action) {
        case "ADD":
            orderService.save(order);
            System.out.println("[CONSUMER] Orden guardada en DB: " + order.getOrderid());
            break;
        case "EDIT":
            orderService.save(order); // Save sirve para ambos en JPA
            System.out.println("[CONSUMER] Orden procesada: " + order.getOrderid());
            break;
        case "DELETE":
            orderService.deleteById(order.getOrderid());
            System.out.println("[CONSUMER] Orden eliminada: " + order.getOrderid());
            break;
        }
    }
}
