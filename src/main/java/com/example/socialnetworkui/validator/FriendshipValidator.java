package com.example.socialnetworkui.validator;

import com.example.socialnetworkui.domain.Friendship;

import java.util.Objects;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidatorException {
        if (Objects.equals(entity.getId_user1(), entity.getId_user2()))
            throw new ValidatorException("you cannot add yourself as a friend!");
    }
}