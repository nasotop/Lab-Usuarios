package com.lab.usuarios.lab_usuarios.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.usuarios.lab_usuarios.infraestructure.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(String role);
}