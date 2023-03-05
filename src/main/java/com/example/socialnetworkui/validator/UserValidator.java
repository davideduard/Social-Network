package com.example.socialnetworkui.validator;

import com.example.socialnetworkui.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidatorException {
        String exception = "";

        if (entity.getID() == null) {
            throw new NullPointerException("id cannot be null!\n");
        }

        if (entity.getFirstName() == null) {
            throw new NullPointerException("first name cannot be null!\n");
        }

        if (entity.getLastName() == null) {
            throw new NullPointerException("last name cannot be null!\n");
        }

        if (entity.getPassword() == null) {
            throw new NullPointerException("password cannot be null!\n");
        }


        if (entity.getFirstName().isEmpty()) {
            exception += "first name cannot be empty!\n";
        }

        if (entity.getLastName().isEmpty()) {
            exception += "last name cannot be empty!\n";
        }

        if (!entity.getFirstName().matches("[a-zA-Z]+")) {
            exception += "first name should only contain small or capital letters!\n";
        }

        if (!entity.getLastName().matches("[a-zA-Z]+")) {
            exception += "last name should only contain small or capital letters!\n";
        }

        if (entity.getPassword().isEmpty()) {
            exception += "password cannot be empty!\n";
        }

        if (!exception.isEmpty()) {
            throw new ValidatorException(exception);
        }
    }
}
