package co.edu.uptc.edakafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uptc.edakafka.model.Customer;
import co.edu.uptc.edakafka.service.CustomerEventProducer;
import co.edu.uptc.edakafka.utils.JsonUtils;

@RestController
public class CustomerController {
    @Autowired
    private CustomerEventProducer customerEventProducer;
    private static JsonUtils jsonUtils= new JsonUtils();

    @PostMapping("/addcustomer")
    public String sendMessageAddCustomer(@RequestBody String customer) {
        Customer customerObj = new Customer();
        customerObj = jsonUtils.fromJson(customer, Customer.class);
        customerEventProducer.sendAddCustomerEvent(customerObj);        
        return customerEventProducer.toString();
    }

    @PostMapping("/editcustomer")
    public String sendMessageEditCustomer(@RequestBody String customer) {
        Customer customerObj = new Customer();
        customerObj = jsonUtils.fromJson(customer, Customer.class);
        customerEventProducer.sendEditCustomerEvent(customerObj);        
        return customerEventProducer.toString();
    }
}

