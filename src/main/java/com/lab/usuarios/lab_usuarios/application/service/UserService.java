package com.lab.usuarios.lab_usuarios.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.usuarios.lab_usuarios.application.interfaces.IUserService;
import com.lab.usuarios.lab_usuarios.domain.dataTransferObject.ResultDto;
import com.lab.usuarios.lab_usuarios.infraestructure.model.User;
import com.lab.usuarios.lab_usuarios.infraestructure.repository.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultDto<List<User>> getAllUsers() {
        return ResultDto.ok(userRepository.findAll());
    }

    @Override
    public ResultDto<User> saveUser(User user) {
        return ResultDto.ok(userRepository.save(user));
    }

    @Override
    public ResultDto<User> getUserById(Long id) {
        return userRepository.findById(id)
                .map(ResultDto::ok)
                .orElseGet(() -> ResultDto.fail("User not found"));
    }

    @Override
    public ResultDto<Void> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResultDto.ok(null);
        } else {
            return ResultDto.fail("User not found");
        }
    }

    @Override
    public ResultDto<List<User>> getUserByRole(String role) {
        return ResultDto.ok(userRepository.findByRole(role.toUpperCase()));
    }

    @Override
    public ResultDto<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setRole(updatedUser.getRole());
                    return ResultDto.ok(userRepository.save(user));
                })
                .orElseGet(() -> ResultDto.fail("User not found"));
    }

    @Override
    public ResultDto<User> getUserByCredentials(String email, String password) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return ResultDto.ok(user);
            }
        }
        return ResultDto.fail("Invalid credentials");
    }

    @Override
    public ResultDto<String> recoverPassword(String email) {

        if (email == null || email.isBlank()) {
            return ResultDto.fail("Email is required");
        }

        var user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (user.isEmpty()) {
            return ResultDto.fail("User not found");
        }

        String token = UUID.randomUUID().toString();

        return ResultDto.ok("Recovery instructions sent");
    }

}
