package com.nk.agri.store.repositories;

import com.nk.agri.store.controller.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
