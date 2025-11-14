package pika.hungt1.dx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Integer id;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
