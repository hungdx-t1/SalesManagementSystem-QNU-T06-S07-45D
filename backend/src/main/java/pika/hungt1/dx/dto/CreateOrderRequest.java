package pika.hungt1.dx.dto;

import lombok.*;
import java.util.List;

@Data
public class CreateOrderRequest {
    private Integer customerId;
    private List<Item> items;

    @Data
    public static class Item {
        private Integer productId;
        private Integer quantity;
    }
}
