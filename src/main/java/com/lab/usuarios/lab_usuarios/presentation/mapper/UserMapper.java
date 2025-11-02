package com.lab.usuarios.lab_usuarios.presentation.mapper;

import com.lab.usuarios.lab_usuarios.infraestructure.model.User;
import com.lab.usuarios.lab_usuarios.presentation.model.UserModel;

public  class UserMapper {
    public static UserModel toModel(User user) {
        return UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
    
    public static User toEntity(UserModel userModel) {
        return User.builder()
                .name(userModel.getName())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .role(userModel.getRole())
                .build();
    }
}
