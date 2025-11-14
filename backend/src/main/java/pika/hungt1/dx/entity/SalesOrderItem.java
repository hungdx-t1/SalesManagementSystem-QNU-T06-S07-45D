package pika.hungt1.dx.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "sales_order_items")
@Data @NoArgsConstructor @AllArgsConstructor
public class SalesOrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private SalesOrder order;


    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(nullable=false)
    private Integer quantity;

    @Column(name="unit_price", nullable=false)
    private BigDecimal unitPrice;
}

