package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.entity.Customer;

@NullMarked
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
