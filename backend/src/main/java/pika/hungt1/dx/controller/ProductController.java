package pika.hungt1.dx.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import pika.hungt1.dx.repository.ProductRepository;
import pika.hungt1.dx.entity.Product;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired private ProductRepository productRepository;

    @GetMapping
    public List<Product> all() { return productRepository.findAll(); }

    @GetMapping("/{id}")
    public Product get(@PathVariable Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return productRepository.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product in) {
        Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        p.setName(in.getName()); p.setPrice(in.getPrice()); p.setStock(in.getStock()); p.setCategory(in.getCategory());
        return productRepository.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }
}