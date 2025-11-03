package com.lab.usuarios.lab_usuarios.presentation.controller;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.usuarios.lab_usuarios.application.service.UserService;
import com.lab.usuarios.lab_usuarios.domain.dataTransferObject.ResultDto;
import com.lab.usuarios.lab_usuarios.domain.helper.EmailHelper;
import com.lab.usuarios.lab_usuarios.infraestructure.model.User;
import com.lab.usuarios.lab_usuarios.presentation.mapper.UserMapper;
import com.lab.usuarios.lab_usuarios.presentation.model.UserModel;
import com.lab.usuarios.lab_usuarios.presentation.validator.UserValidator;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping("login")
    public ResultDto<UserModel> Authenticate(@RequestParam String email, @RequestParam String password) {
        if (EmailHelper.isValidEmail(email) == false) {
            return ResultDto.fail("Invalid email format");
        }
        ResultDto<User> result = userService.getUserByCredentials(email, password);
        if (!result.isSuccess()) {

            return ResultDto.fail(result.getErrorMessage());
        }
        return ResultDto.ok(UserMapper.toModel(result.getData()));
    }

    @PostMapping("register")
    public ResultDto<UserModel> registerUser(@Valid @RequestBody UserModel userModel) {

        List<String> validationErrors = userValidator.validate(userModel);
        if (!validationErrors.isEmpty()) {
            return ResultDto.fail(String.join(", ", validationErrors));
        }

        User entity = UserMapper.toEntity(userModel);

        if(entity.getId() == 0) {
           entity.setId(null);
        }

        var result = userService.saveUser(entity);

        return ResultDto.ok(UserMapper.toModel(result.getData()));
    }

    @PostMapping("update/{id}")
    public ResultDto<UserModel> updateUser(@PathVariable Long id, @Valid @RequestBody UserModel userModel) {

        var entity = userService.getUserById(id);
        if (!entity.isSuccess()) {
            return ResultDto.fail(entity.getErrorMessage());
        }

        List<String> validationErrors = userValidator.validate(userModel);
        if (!validationErrors.isEmpty()) {
            return ResultDto.fail(String.join(", ", validationErrors));
        }
        var result = userService.updateUser(id, UserMapper.toEntity(userModel));

        if (!result.isSuccess()) {
            return ResultDto.fail(result.getErrorMessage());
        }

        return ResultDto.ok(UserMapper.toModel(result.getData()));

    }

    @PutMapping("delete/{id}")
    public ResultDto<String> deleteUser(@PathVariable Long id) {
        var result = userService.deleteUser(id);
        if (result.isSuccess()) {
            return ResultDto.ok("User deleted successfully");
        } else {
            return ResultDto.fail(result.getErrorMessage());
        }
    }
    @GetMapping("get-all-users")
    public ResultDto<List<UserModel>> getAllUsers() {
        var result = userService.getAllUsers();
        if (!result.isSuccess()) {
            return ResultDto.fail(result.getErrorMessage());
        }
        List<UserModel> userModels = result.getData().stream()
                .map(UserMapper::toModel)
                .toList();
        return ResultDto.ok(userModels);
    }

    @GetMapping("get-user-by-id/{id}")
    public ResultDto<UserModel> getUserById(@PathVariable Long id) {
        var result = userService.getUserById(id);
        if (!result.isSuccess()) {
            return ResultDto.fail(result.getErrorMessage());
        }
        return ResultDto.ok(UserMapper.toModel(result.getData()));
    }
}
