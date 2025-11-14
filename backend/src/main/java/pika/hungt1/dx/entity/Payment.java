package pika.hungt1.dx.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name = "payments")
@Data @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private SalesOrder order;

    @Column(nullable=false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Method paymentMethod = Method.cash;

    @Column(name="paid_at")
    private Instant paidAt = Instant.now();

    public enum Method { cash, bank_transfer, ewallet }
}
