package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pika.hungt1.dx.entity.ImportOrder;
import pika.hungt1.dx.entity.Supplier;

import java.util.List;

@Repository
@NullMarked
public interface ImportOrderRepository extends JpaRepository<ImportOrder, Long> {
    List<ImportOrder> findBySupplier(Supplier supplier);
}
