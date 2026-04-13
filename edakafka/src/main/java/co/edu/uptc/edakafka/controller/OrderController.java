package co.edu.uptc.edakafka.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import co.edu.uptc.edakafka.model.Order;
import co.edu.uptc.edakafka.service.OrderEventProducer;
import co.edu.uptc.edakafka.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderEventProducer producer;

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public String addOrder(@RequestBody Order order) {
        producer.sendOrderEvent(order, "ADD");
        return "Evento ADD enviado para la orden: " + order.getOrderid();
    }

    @PutMapping("/edit")
    public String editOrder(@RequestBody Order order) {
        producer.sendOrderEvent(order, "EDIT");
        return "Evento EDIT enviado para la orden: " + order.getOrderid();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        Order order = new Order();
        order.setOrderid(id);
        producer.sendOrderEvent(order, "DELETE");
        return "Evento DELETE enviado para la orden: " + id;
    }

    @GetMapping("/find/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id).orElse(null);
    }

    @GetMapping("/findall")
    public List<Order> findAll() {
        return orderService.findAll();
    }
}