package pika.hungt1.dx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import pika.hungt1.dx.entity.Payment; // để lấy Payment.Method

@Data
@AllArgsConstructor
public class PaymentOrderItems {
    private Integer paymentId;
    private BigDecimal amount;
    private Payment.Method paymentMethod;
    private Instant paidAt;

    private Integer orderId;
    private BigDecimal orderTotal;
    private String orderStatus;

    private CustomerDTO customer;
    private List<OrderItemDTO> items;
}

