package pika.hungt1.dx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.dto.CreateOrderRequest;
import pika.hungt1.dx.entity.Payment;
import pika.hungt1.dx.entity.SalesOrder;
import pika.hungt1.dx.repository.SalesOrderRepository;
import pika.hungt1.dx.service.SalesOrderService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class SalesOrderController {

    @Autowired private SalesOrderRepository orderRepo;
    @Autowired private SalesOrderService orderService;

    @GetMapping
    public List<SalesOrder> getAll() {
        return orderRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getById(@PathVariable Integer id) {
        return orderRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalesOrder> create(@RequestBody CreateOrderRequest req) {
        return ResponseEntity.ok(orderService.createOrder(req));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<SalesOrder> pay(
            @PathVariable Integer id,
            @RequestParam BigDecimal amount,
            @RequestParam Payment.Method method
    ) {
        return ResponseEntity.ok(orderService.payOrder(id, amount, method));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
