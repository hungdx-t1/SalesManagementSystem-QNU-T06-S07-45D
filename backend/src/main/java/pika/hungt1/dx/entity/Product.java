package pika.hungt1.dx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=100)
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column(nullable=false)
    private BigDecimal price;

    @Column(nullable=false)
    private Integer stock = 0;

    @Column(name="created_at")
    private Instant createdAt = Instant.now();
}
