package pika.hungt1.dx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pika.hungt1.dx.dto.PaymentOrderItems;
import pika.hungt1.dx.entity.Customer;
import pika.hungt1.dx.entity.Payment;
import pika.hungt1.dx.entity.SalesOrder;
import pika.hungt1.dx.repository.PaymentRepository;
import pika.hungt1.dx.repository.SalesOrderRepository;
import pika.hungt1.dx.dto.OrderItemDTO;
import pika.hungt1.dx.dto.CustomerDTO;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired private PaymentRepository paymentRepo;
    @Autowired private SalesOrderRepository orderRepo;

    @GetMapping
    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Integer id) {
        return paymentRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestParam Integer orderId, @RequestBody Payment payment) {
        SalesOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        payment.setOrder(order);
        Payment saved = paymentRepo.save(payment);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!paymentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        paymentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/details")
    public List<PaymentOrderItems> getPaymentDetails() {
        return paymentRepo.findAll().stream().map(p -> {
            SalesOrder order = p.getOrder();

            // map Customer → CustomerDTO
            Customer customerEntity = order.getCustomer();
            CustomerDTO customerDTO = new CustomerDTO(
                    customerEntity.getId(),
                    customerEntity.getName(),
                    customerEntity.getPhone(),
                    customerEntity.getEmail(),
                    customerEntity.getAddress()
            );

            // map Order Items → OrderItemDTO
            List<OrderItemDTO> items = order.getItems().stream()
                    .map(item -> new OrderItemDTO(
                            item.getId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getUnitPrice()
                    ))
                    .toList();

            // map Payment + Order → PaymentOrderItems DTO
            return new PaymentOrderItems(
                    p.getId(),
                    p.getAmount(),
                    p.getPaymentMethod(),
                    p.getPaidAt(),
                    order.getId(),
                    order.getTotalAmount(),
                    order.getStatus().name(),
                    customerDTO,
                    items
            );
        }).toList();
    }

}
