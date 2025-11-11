package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import pika.hungt1.dx.entity.SalesOrderItem;

@NullMarked
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
}
