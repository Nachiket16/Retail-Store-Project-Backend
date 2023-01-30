package com.nk.agri.store.repositories;

import com.nk.agri.store.entities.Cart;
import com.nk.agri.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {


    Optional<Cart> findByUser(User user);

}
