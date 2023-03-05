package com.example.socialnetworkui.domain;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<String> {
    private String firstName;
    private String lastName;
    private String password;
    private final List<User> friends = new LinkedList<>();

    public User(String id, String firstName, String lastName, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<User> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        StringBuilder friend_list = new StringBuilder();
        for (User each : friends) {
            friend_list.append(each.getID());
            friend_list.append(", ");
        }
        return "ID: " + this.getID() + " -> First Name: " + this.getFirstName() + " -> Last Name: "
                + this.getLastName() + " -> Friends: " + friend_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, password, friends);
    }
}

