package com.example.inventory_service.repository;

import com.example.inventory_service.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {
}
