package pika.hungt1.dx.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pika.hungt1.dx.repository.*;
import pika.hungt1.dx.entity.*;
import pika.hungt1.dx.dto.CreateOrderRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class SalesOrderService {
    @Autowired private SalesOrderRepository orderRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private SalesOrderItemRepository itemRepo;
    @Autowired private PaymentRepository paymentRepo;

    public SalesOrder createOrder(CreateOrderRequest req) {
        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        SalesOrder order = new SalesOrder();
        order.setCustomer(customer);
        order.setStatus(SalesOrder.Status.pending);

        List<SalesOrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CreateOrderRequest.Item it : req.getItems()) {
            Product p = productRepo.findById(it.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (p.getStock() < it.getQuantity()) {
                throw new RuntimeException("Not enough stock for product id " + p.getId());
            }
            // reduce stock
            p.setStock(p.getStock() - it.getQuantity());
            productRepo.save(p);

            SalesOrderItem soi = new SalesOrderItem();
            soi.setProduct(p);
            soi.setQuantity(it.getQuantity());
            soi.setUnitPrice(p.getPrice());
            soi.setOrder(order);
            items.add(soi);
            total = total.add(p.getPrice().multiply(new BigDecimal(it.getQuantity())));
        }

        order.setTotalAmount(total);
        order.setItems(items);
        SalesOrder saved = orderRepo.save(order);
        // save items (cascade may have saved them, but ensure)
        itemRepo.saveAll(items);
        return saved;
    }

    public SalesOrder payOrder(Integer orderId, BigDecimal amount, Payment.Method method) {
        SalesOrder order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        paymentRepo.save(payment);

        BigDecimal newTotalPaid = paymentRepo.findAllByOrder(order)
                .stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (newTotalPaid.compareTo(order.getTotalAmount()) >= 0) {
            order.setStatus(SalesOrder.Status.paid);
            orderRepo.save(order);
        }
        return order;
    }
}
