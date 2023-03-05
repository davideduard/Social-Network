package com.example.socialnetworkui.repository;

import com.example.socialnetworkui.domain.Friendship;
import com.example.socialnetworkui.validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipsDb extends AbstractDbRepository<Integer, Friendship> {
    public FriendshipsDb(String url, String user, String password, Validator<Friendship> validator, String tableName) {
        super(url, user, password, validator, tableName);
    }

    @Override
    Friendship extractEntity(ResultSet set) throws SQLException {
        Integer id = set.getInt("id_friendship");
        String first_id =  set.getString("id_user1");
        String second_id =  set.getString("id_user2");
        Date date = set.getDate("friends_from");

        return new Friendship(id, first_id, second_id, date.toLocalDate());
    }

    @Override
    PreparedStatement saveEntity(Friendship entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getId_user1());
        preparedStatement.setString(2, entity.getId_user2());
        preparedStatement.setDate(3, Date.valueOf(entity.getFriendsFrom()));

        return preparedStatement;
    }
}
