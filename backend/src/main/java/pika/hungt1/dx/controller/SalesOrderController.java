package pika.hungt1.dx.controller;

import pika.hungt1.dx.dto.OrderRequest;
import pika.hungt1.dx.entity.SalesOrder;
import pika.hungt1.dx.service.SalesOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class SalesOrderController {

    private final SalesOrderService service;

    public SalesOrderController(SalesOrderService service) {
        this.service = service;
    }

    @PostMapping
    public SalesOrder create(@RequestBody OrderRequest request) {
        return service.createOrder(request);
    }

    @GetMapping
    public List<SalesOrder> getAll() {
        return service.getAll();
    }
}

