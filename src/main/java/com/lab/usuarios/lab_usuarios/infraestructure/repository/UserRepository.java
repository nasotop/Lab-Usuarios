 package com.lab.usuarios.lab_usuarios.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.usuarios.lab_usuarios.infraestructure.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
}