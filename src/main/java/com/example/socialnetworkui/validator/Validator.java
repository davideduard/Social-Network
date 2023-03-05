package com.example.socialnetworkui.validator;

public interface Validator<E> {
    void validate(E entity) throws ValidatorException;
}
