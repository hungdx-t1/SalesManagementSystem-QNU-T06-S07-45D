package pika.hungt1.dx.service;

import org.springframework.stereotype.Service;
import pika.hungt1.dx.entity.Product;
import pika.hungt1.dx.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product create(Product product) {
        return repo.save(product);
    }

    public Product update(Long id, Product updated) {
        return repo.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setPrice(updated.getPrice());
            p.setStock(updated.getStock());
            p.setCategory(updated.getCategory());
            return repo.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
