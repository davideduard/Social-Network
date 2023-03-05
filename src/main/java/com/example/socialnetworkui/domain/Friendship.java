package com.example.socialnetworkui.domain;
import java.time.LocalDate;
import java.util.Objects;

public class Friendship extends Entity<Integer> {
    private String username_user1;
    private String username_user2;
    private final LocalDate friendsFrom;

    public Friendship(Integer integer, String username_user1, String username_user2, LocalDate friendsFrom) {
        super(integer);
        this.username_user1 = username_user1;
        this.username_user2 = username_user2;
        this.friendsFrom = friendsFrom;
    }

    public String getId_user1() {
        return username_user1;
    }

    public void setId_user1(String username_user1) {
        this.username_user1 = username_user1;
    }

    public String getId_user2() {
        return username_user2;
    }

    public void setId_user2(String username_user2) {
        this.username_user2 = username_user2;
    }

    public LocalDate getFriendsFrom() {
        return friendsFrom;
    }


    @Override
    public String toString() {
        return "Friendship{" +
                "id_user1=" + username_user1 +
                ", id_user2=" + username_user2 +
                ", friendsFrom=" + friendsFrom +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(username_user1, that.username_user1) && Objects.equals(username_user2, that.username_user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username_user1, username_user2);
    }
}