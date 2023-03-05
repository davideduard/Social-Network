package com.example.socialnetworkui.repository;

import com.example.socialnetworkui.domain.User;
import com.example.socialnetworkui.validator.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDb extends AbstractDbRepository<String, User> {
    public UserDb(String url, String user, String password, Validator<User> validator, String tableName) {
        super(url, user, password, validator, tableName);
    }

    @Override
    User extractEntity(ResultSet set) throws SQLException {
        String first_name = set.getString("first_name");
        String last_name = set.getString("last_name");
        String username = set.getString("id_user");
        String password = set.getString("password");

        return new User(username, first_name, last_name, password);

    }

    @Override
    PreparedStatement saveEntity(User entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setString(4, entity.getID());

        return preparedStatement;
    }
}
