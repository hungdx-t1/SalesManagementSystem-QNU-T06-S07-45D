package pika.hungt1.dx.controller;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.entity.Category;
import pika.hungt1.dx.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*") // Cho phép gọi từ frontend
@NullMarked
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả category
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    // Lấy category theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tạo mới category
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(saved);
    }

    // Cập nhật category
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category updated) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    categoryRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Xóa category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
