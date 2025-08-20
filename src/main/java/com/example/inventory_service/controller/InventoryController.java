// package com.example.inventory_service.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.web.bind.annotation.*;

// import java.util.*;

// @RestController
// @RequestMapping("/api/inventory")
// public class InventoryController {

//     private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

//      // Temporary in-memory storage
//     private Map<Integer, Map<String, Object>> inventory = new HashMap<>();
//     private int nextId = 1; // auto increment ID

    

//     // Get all items
//     @GetMapping
//     public Collection<Map<String, Object>> getAllItems() {
//         logger.info("Fetching all inventory items");
//         return inventory.values();
//     }

//      // Get single item
//     @GetMapping("/{id}")
//     public Map<String, Object> getItem(@PathVariable int id) {
//         logger.info("Fetching inventory item with ID: {}", id);
//         return inventory.getOrDefault(id, Map.of("error", "Item not found"));
//     }

//     // Create new item
//     @PostMapping
//     public Map<String, Object> createItem(@RequestBody Map<String, Object> request) {
//         request.put("id", nextId);
//         inventory.put(nextId, request);
//         logger.info("Item created: {}", request);
//         nextId++;
//         return request;
//     }

//     // Update existing item
//     @PutMapping("/{id}")
//     public Map<String, Object> updateItem(@PathVariable int id, @RequestBody Map<String, Object> request) {
//         if (!inventory.containsKey(id)) {
//             logger.warn("Attempt to update non-existing item with ID: {}", id);
//             return Map.of("error", "Item not found");
//         }
//         request.put("id", id);
//         inventory.put(id, request);
//         logger.info("Item updated: {}", request);
//         return request;
//     }

//     // Delete item
//     @DeleteMapping("/{id}")
//     public Map<String, String> deleteItem(@PathVariable int id) {
//         if (inventory.remove(id) != null) {
//             logger.info("Item deleted with ID: {}", id);
//             return Map.of("message", "Item deleted successfully");
//         } else {
//             logger.warn("Attempt to delete non-existing item with ID: {}", id);
//             return Map.of("error", "Item not found");
//         }
//     }


// }


package com.example.inventory_service.controller;

import com.example.inventory_service.model.InventoryItem;
import com.example.inventory_service.repository.InventoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryRepository repo;

    public InventoryController(InventoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Inventory Service is running with MySQL!";
    }

    @GetMapping
    public List<InventoryItem> getAllItems() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public InventoryItem getItem(@PathVariable Integer id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public InventoryItem createItem(@RequestBody InventoryItem item) {
        return repo.save(item);
    }

    @PutMapping("/{id}")
    public InventoryItem updateItem(@PathVariable Integer id, @RequestBody InventoryItem updated) {
        return repo.findById(id).map(item -> {
            item.setName(updated.getName());
            item.setQty(updated.getQty());
            item.setDescription(updated.getDescription());
            return repo.save(item);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Integer id) {
        repo.deleteById(id);
        return "Item deleted";
    }
}
