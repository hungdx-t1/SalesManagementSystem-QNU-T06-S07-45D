package pika.hungt1.dx.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "customers")
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=100)
    private String name;

    @Column(unique=true, length=20)
    private String phone;

    @Column(length=100)
    private String email;

    @Column(columnDefinition="TEXT")
    private String address;

    @Column(name="created_at")
    private Instant createdAt = Instant.now();
}
