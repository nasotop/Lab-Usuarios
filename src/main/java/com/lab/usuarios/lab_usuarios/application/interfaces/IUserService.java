package com.lab.usuarios.lab_usuarios.application.interfaces;

import java.util.List;

import com.lab.usuarios.lab_usuarios.domain.dataTransferObject.ResultDto;
import com.lab.usuarios.lab_usuarios.infraestructure.model.User;

public interface IUserService {
    ResultDto<List<User>> getAllUsers();
    ResultDto<User> saveUser(User user);
    ResultDto<User> getUserById(Long id);
    ResultDto<Void> deleteUser(Long id);
    ResultDto<User> updateUser(Long id, User updatedUser);
    ResultDto<User> getUserByCredentials(String email, String password);
    ResultDto<String> recoverPassword(String email);
    ResultDto<List<User>> getUserByRole(String role);
}
