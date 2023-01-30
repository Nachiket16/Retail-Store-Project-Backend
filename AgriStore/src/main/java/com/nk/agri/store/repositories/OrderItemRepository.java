package com.nk.agri.store.repositories;

import com.nk.agri.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
}
