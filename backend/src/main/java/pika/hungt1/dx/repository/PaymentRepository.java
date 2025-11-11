package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.entity.Payment;
import pika.hungt1.dx.entity.SalesOrder;

import java.util.Optional;

@NullMarked
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findAllByOrder(SalesOrder order);
}
