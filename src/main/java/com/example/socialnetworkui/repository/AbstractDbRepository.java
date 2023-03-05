package com.example.socialnetworkui.repository;

import com.example.socialnetworkui.domain.Entity;
import com.example.socialnetworkui.validator.Validator;
import kotlin.Pair;

import java.sql.*;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDbRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    protected String url;
    protected String user;
    protected String password;
    protected String tableName;

    public AbstractDbRepository(String url, String user, String password, Validator<E> validator, String tableName) {
        super(validator);
        this.url = url;
        this.user = user;
        this.password = password;
        this.tableName = tableName;
        loadAll();
    }

    abstract E extractEntity(ResultSet set) throws SQLException;

    abstract PreparedStatement saveEntity(E entity, PreparedStatement preparedStatement) throws SQLException;

    private void loadAll() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("select * from " + tableName);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                E extracted = extractEntity(resultSet);
                super.add(extracted);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(E entity) {
        String query = "";
        if (Objects.equals(tableName, "users"))
            query = "Insert into users(first_name, last_name, password, id_user) values(?,?,?,?)";
        if (Objects.equals(tableName, "friendships"))
            query = "Insert into friendships(id_user1, id_user2, friends_from) values (?,?,?)";
        if (Objects.equals(tableName, "requests"))
            query = "Insert into requests(sent_from, sent_to, sent_date) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            saveEntity(entity, statement);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete(ID id) {
        String query = "";
        if (Objects.equals(tableName, "users"))
            query = "Delete from users Where id_user = ?";
        if (Objects.equals(tableName, "friendships"))
            query = "Delete from friendships Where id_friendship = ?";
        if (Objects.equals(tableName, "requests"))
            query = "Delete from requests Where sent_from = ? and sent_to = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (Objects.equals(tableName, "friendships"))
                statement.setInt(1, (Integer) id);
            if (Objects.equals(tableName, "requests")){
                statement.setString(1, ((Pair<String, String>) id).getFirst());
                statement.setString(2, ((Pair<String, String>) id).getSecond());
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public E search(ID id) {
        String query = "Select * from users where id_user = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, (String) id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                E user = extractEntity(resultSet);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public E add(E entity) {
        E ent = super.add(entity);
        if (ent == null) {
            save(entity);
        }
        return ent;
    }

    @Override
    public E remove(ID id) {
        E ent = super.remove(id);
        if (ent != null) {
            delete(id);
        }
        return ent;
    }

    @Override
    public Map<ID, E> getEntities() {
        super.entities.clear();
        loadAll();
        return super.getEntities();
    }
}
