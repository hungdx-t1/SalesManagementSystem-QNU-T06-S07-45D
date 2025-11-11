package pika.hungt1.dx.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import pika.hungt1.dx.service.SalesOrderService;
import pika.hungt1.dx.dto.CreateOrderRequest;
import pika.hungt1.dx.entity.SalesOrder;
import pika.hungt1.dx.entity.Payment;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
public class SalesOrderController {
    @Autowired private SalesOrderService orderService;

    @PostMapping
    public SalesOrder create(@RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req);
    }

    @PostMapping("/{id}/pay")
    public SalesOrder pay(@PathVariable Integer id, @RequestParam BigDecimal amount, @RequestParam Payment.Method method) {
        return orderService.payOrder(id, amount, method);
    }
}
