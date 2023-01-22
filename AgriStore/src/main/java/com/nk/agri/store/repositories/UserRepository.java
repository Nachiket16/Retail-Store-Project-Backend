package com.nk.agri.store.repositories;

import com.nk.agri.store.controller.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    //This is the costume findBy method
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email,String password);

    List<User> findByNameContaining(String keyword);
}
