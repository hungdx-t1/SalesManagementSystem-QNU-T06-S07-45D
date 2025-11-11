package pika.hungt1.dx.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pika.hungt1.dx.entity.Supplier;

@NullMarked
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
