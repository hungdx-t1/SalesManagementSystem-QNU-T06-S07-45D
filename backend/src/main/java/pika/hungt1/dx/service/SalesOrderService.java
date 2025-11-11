package pika.hungt1.dx.service;

import pika.hungt1.dx.dto.OrderRequest;
import pika.hungt1.dx.entity.*;
import pika.hungt1.dx.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesOrderService {
    private final SalesOrderRepository orderRepo;
    private final SalesOrderItemRepository itemRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public SalesOrderService(
            SalesOrderRepository orderRepo,
            SalesOrderItemRepository itemRepo,
            CustomerRepository customerRepo,
            ProductRepository productRepo
    ) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public SalesOrder createOrder(OrderRequest request) {
        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        SalesOrder order = SalesOrder.builder()
                .customer(customer)
                .status(SalesOrder.OrderStatus.PENDING)
                .build();
        order = orderRepo.save(order);

        BigDecimal total = BigDecimal.ZERO;
        List<SalesOrderItem> items = new ArrayList<>();

        for (OrderRequest.OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepo.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            // Trừ tồn kho
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepo.save(product);

            SalesOrderItem item = SalesOrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(itemReq.getUnitPrice())
                    .build();

            itemRepo.save(item);
            items.add(item);

            total = total.add(itemReq.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        order.setItems(items);
        order.setTotalAmount(total);
        return orderRepo.save(order);
    }

    public List<SalesOrder> getAll() {
        return orderRepo.findAll();
    }
}

