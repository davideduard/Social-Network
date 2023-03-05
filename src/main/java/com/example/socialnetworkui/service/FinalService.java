package com.example.socialnetworkui.service;

import com.example.socialnetworkui.domain.Friendship;
import com.example.socialnetworkui.domain.Request;
import com.example.socialnetworkui.domain.User;
import kotlin.Pair;

import java.util.Objects;

public class FinalService {
    private final Service<String, User> userService;
    private final Service<Integer, Friendship> friendshipService;
    private final Service<Pair<String, String>, Request> requestService;
    private static FinalService service = null;
    private User user = null;

    private FinalService(Service<String, User> userService, Service<Integer, Friendship> friendshipService, Service<Pair<String, String>, Request> requestService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.requestService = requestService;
    }

    public static FinalService getInstance(Service<String, User> userService, Service<Integer, Friendship> friendshipService, Service<Pair<String, String>, Request> requestService){
        if (service == null){
            service = new FinalService(userService, friendshipService, requestService);
        }
        return service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Service<String, User> getUserService() {
        return userService;
    }

    public Service<Integer, Friendship> getFriendshipService() {
        return friendshipService;
    }

    public Service<Pair<String, String>, Request> getRequestService() { return requestService;}

    public void addFriend(Friendship friendship) {
        if (friendship == null){
            throw new ServiceException("entity cannot be null!");
        }

        if (this.userService.searchService(friendship.getId_user1()) == null){
            throw new ServiceException("The user you want to connect to doesn't exist!");
        }

        if (this.userService.searchService(friendship.getId_user2()) == null){
            throw new ServiceException("The user you are trying to add doesn't exist!");
        }

        if (friendship.getId_user2().compareTo(friendship.getId_user1()) < 0) {
            String aux_id = friendship.getId_user1();
            friendship.setId_user1(friendship.getId_user2());
            friendship.setId_user2(aux_id);
        }

        for (Friendship each : friendshipService.getAll().values()) {
            if (Objects.equals(each.getId_user1(), friendship.getId_user1()) && Objects.equals(each.getId_user2(), friendship.getId_user2())) {
                throw new ServiceException("You are already friends with this user!");
            }
        }

        this.friendshipService.addService(friendship);
    }

    public void removeFriend(Friendship friendship){
        if (friendship == null){
            throw new ServiceException("entity cannot be null!");
        }

        if (friendship.getId_user2().compareTo(friendship.getId_user1()) < 0) {
            String aux_id = friendship.getId_user1();
            friendship.setId_user1(friendship.getId_user2());
            friendship.setId_user2(aux_id);
        }
        if (this.userService.searchService(friendship.getId_user1()) == null){
            throw new ServiceException("The user you want to connect to doesn't exist!");
        }

        if (this.userService.searchService(friendship.getId_user2()) == null){
            throw new ServiceException("The user you are trying to remove doesn't exist!");
        }

        for (Friendship each : friendshipService.getAll().values()){
            if (Objects.equals(each.getId_user1(), friendship.getId_user1()) && Objects.equals(each.getId_user2(), friendship.getId_user2())){
                friendshipService.removeService(each.getID());
                return;
            }
        }

        throw new ServiceException("You are not friends with this user!");
    }

    public int getFriendshipId(String user1, String user2){
        int id = -1;
        for (Friendship each : friendshipService.getAll().values()){
            if ((Objects.equals(each.getId_user1(), user1) || Objects.equals(each.getId_user2(), user1))
                    && (Objects.equals(each.getId_user1(), user2) || Objects.equals(each.getId_user2(), user2))){
                id = each.getID();
                return id;
            }
        }
        return id;
    }

    public String getUserId(String firstName, String lastName){
        for (User each : userService.getAll().values()){
            if (Objects.equals(each.getLastName(), lastName) && Objects.equals(each.getFirstName(), firstName)){
                return  each.getID();
            }
        }
        return null;
    }

}

