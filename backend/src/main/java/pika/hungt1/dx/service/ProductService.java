package pika.hungt1.dx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pika.hungt1.dx.entity.Product;
import pika.hungt1.dx.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    // Tạo mới hoặc cập nhật sản phẩm
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Xóa sản phẩm theo ID
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    // Tìm theo tên sản phẩm
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}