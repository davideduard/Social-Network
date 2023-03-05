package com.example.socialnetworkui.repository;

import com.example.socialnetworkui.domain.Request;
import com.example.socialnetworkui.validator.Validator;
import kotlin.Pair;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestsDb extends AbstractDbRepository <Pair<String,String>, Request>{
    public RequestsDb(String url, String user, String password, Validator<Request> validator, String tableName) {
        super(url, user, password, validator, tableName);
    }

    @Override
    Request extractEntity(ResultSet set) throws SQLException {
        String sentFrom = set.getString("sent_from");
        String sentTo = set.getString("sent_to");
        Date sentDate = set.getDate("sent_date");
        int status = set.getInt("status");

        Pair pair = new Pair(sentFrom, sentTo);
        Request request = new Request(pair, sentDate.toLocalDate(), status);
        return request;
    }

    @Override
    PreparedStatement saveEntity(Request entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getID().getFirst());
        preparedStatement.setString(2, entity.getID().getSecond());
        preparedStatement.setDate(3, Date.valueOf(entity.getSentDate()));
        return preparedStatement;
    }
}