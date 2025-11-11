package pika.hungt1.dx.controller;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pika.hungt1.dx.entity.Customer;
import pika.hungt1.dx.repository.CustomerRepository;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
@NullMarked
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        return customerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Customer saved = customerRepo.save(customer);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Integer id, @RequestBody Customer updated) {
        return customerRepo.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setPhone(updated.getPhone());
                    existing.setEmail(updated.getEmail());
                    existing.setAddress(updated.getAddress());
                    customerRepo.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!customerRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
