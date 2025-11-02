package com.lab.usuarios.lab_usuarios.presentation.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lab.usuarios.lab_usuarios.presentation.model.UserModel;


@Component
public class UserValidator {

    public List<String> validate(UserModel userModel) {
        List<String> errors = new java.util.ArrayList<String>();

        if (userModel == null)
            errors.add( "Model cannot be null");
        if (userModel.getRole() == null)
            errors.add("Role cannot be null");

        if (!userModel.getRole().matches("ADMIN|USER"))
             errors.add("Invalid Role: " + userModel.getRole());

        return errors;

    }

}
