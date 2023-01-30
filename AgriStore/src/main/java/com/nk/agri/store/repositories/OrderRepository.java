package com.nk.agri.store.repositories;


import com.nk.agri.store.entities.Order;
import com.nk.agri.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);

}
