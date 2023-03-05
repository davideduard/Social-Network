package com.example.socialnetworkui.validator;

import java.util.Objects;

public class UiUserDataValidator {
    public void validate(String id, String firstName, String lastName, String password, String confirmPassword) throws ValidatorException {
        if (!id.matches("[a-zA-Z._]+")) {
            throw new ValidatorException("username can only contain small and capital letters, as well as (.) and (_) symbols!");
        }
        if (!firstName.matches("[a-zA-Z]+")) {
            throw new ValidatorException("first name should only contain small or capital letters!");
        }
        if (!lastName.matches("[a-zA-Z]+")) {
            throw new ValidatorException("last name should only contain small or capital letters!");
        }
        if (!Objects.equals(password, confirmPassword)){
            throw new ValidatorException("password fields do not match!");
        }
    }
}
