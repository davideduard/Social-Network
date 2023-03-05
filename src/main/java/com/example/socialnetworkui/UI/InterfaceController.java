package com.example.socialnetworkui.UI;

import com.example.socialnetworkui.HelloApplication;
import com.example.socialnetworkui.domain.Friendship;
import com.example.socialnetworkui.domain.Request;
import com.example.socialnetworkui.domain.User;
import com.example.socialnetworkui.repository.FriendshipsDb;
import com.example.socialnetworkui.repository.Repository;
import com.example.socialnetworkui.repository.RequestsDb;
import com.example.socialnetworkui.repository.UserDb;
import com.example.socialnetworkui.service.FinalService;
import com.example.socialnetworkui.service.Service;
import com.example.socialnetworkui.service.ServiceException;
import com.example.socialnetworkui.validator.FriendshipValidator;
import com.example.socialnetworkui.validator.RequestValidator;
import com.example.socialnetworkui.validator.UserValidator;
import com.example.socialnetworkui.validator.Validator;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import kotlin.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class InterfaceController {
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color ,-fx-outer-border;";
    private final Validator<User> userValidator = new UserValidator();
    private final Validator<Friendship> friendshipValidator = new FriendshipValidator();
    private final Validator<Request> requestValidator = new RequestValidator();
    private final Repository<String, User> userRepository = new UserDb("jdbc:postgresql://localhost:5434/SocialNetwork",
            "postgres", "root", userValidator, "users");
    private final Repository<Integer, Friendship> friendshipRepository = new FriendshipsDb("jdbc:postgresql://localhost:5434/SocialNetwork",
            "postgres", "root", friendshipValidator, "friendships");
    private final Repository<Pair<String, String>, Request> requestReposiyory = new RequestsDb("jdbc:postgresql://localhost:5434/SocialNetwork",
            "postgres", "root", requestValidator, "requests");
    private final Service<String, User> userService = new Service<>(userRepository);
    private final Service<Integer, Friendship> friendshipService = new Service<>(friendshipRepository);
    private final Service<Pair<String, String>, Request> requestService = new Service<>(requestReposiyory);
    FinalService finalService = FinalService.getInstance(userService, friendshipService, requestService);

    @FXML
    private ImageView messagesImage;
    @FXML
    private ImageView rejectImage;
    @FXML
    private Button rejectButton;
    @FXML
    private ImageView acceptImage;
    @FXML
    private Button acceptButton;
    @FXML
    private ListView<String> notificationsListView;
    @FXML
    private ImageView removeFriendImage;
    @FXML
    private Button removeFriendButton;
    @FXML
    private ListView<String> usersList;
    @FXML
    private ImageView addFriendImage;
    @FXML
    private Button addFriendButton;
    @FXML
    private Line leftLine;
    @FXML
    private Line rightLine;
    @FXML
    private Text friendsText;
    @FXML
    private ListView<String> friendList;
    @FXML
    private ImageView notificationMenuImage;
    @FXML
    private Pane notificationMenu;
    @FXML
    private ImageView notificationImage;
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView logoutImage;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button profileButton;
    @FXML
    private Pane darkPane;
    @FXML
    private AnchorPane slideMenu;
    @FXML
    private Button settingsButton;
    @FXML
    private ImageView slideMenuButton;
    @FXML
    private ImageView settingsImage;
    @FXML
    private ImageView menuButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Text welcomeTitle;
    @FXML
    private ImageView closeBtnImg;
    @FXML
    private Text welcomeText;

    @FXML
    public void initialize() {
        init_objects();
        fadeIn();
        button_init();
        loadListView();
        loadUsers();
        loadNotifications();
    }

    private void init_objects() {
        closeBtnImg.setImage(new Image("file:images/close.png"));
        menuButton.setImage(new Image("file:images/menu.png"));
        settingsImage.setImage(new Image("file:images/settings.png"));
        slideMenuButton.setImage(new Image("file:images/menu.png"));
        profileImage.setImage(new Image("file:images/avatar.png"));
        logoutImage.setImage(new Image("file:images/logout.png"));
        notificationImage.setImage(new Image("file:images/notification.png"));
        notificationMenuImage.setImage(new Image("file:images/notification.png"));
        addFriendImage.setImage(new Image("file:images/addUser.png"));
        removeFriendImage.setImage(new Image("file:images/removeFriend.png"));
        acceptImage.setImage(new Image("file:images/accept.png"));
        rejectImage.setImage(new Image("file:images/reject.png"));
        messagesImage.setImage(new Image("file:images/messages.png"));

        welcomeText.setOpacity(0);
        welcomeTitle.setOpacity(0);
        searchBar.setOpacity(0);
        searchBar.setFocusTraversable(false);
        searchBar.setVisible(false);
        menuButton.setVisible(false);
        menuButton.setOpacity(0);
        welcomeText.setText("Welcome, " + finalService.getUser().getFirstName() + "!");
        welcomeTitle.setText("Welcome, " + finalService.getUser().getFirstName() + "!");
        darkPane.setVisible(false);
        darkPane.setOpacity(0);

        slideMenu.setVisible(false);
        notificationImage.setVisible(false);
        notificationImage.setOpacity(0);

        friendList.setOpacity(0);
        friendList.setVisible(false);

        leftLine.setOpacity(0);
        rightLine.setOpacity(0);
        friendsText.setOpacity(0);

        addFriendButton.setVisible(false);
        addFriendImage.setOpacity(0);

        removeFriendButton.setVisible(false);
        removeFriendImage.setOpacity(0);

        messagesImage.setVisible(false);
        messagesImage.setOpacity(0);


    }

    @FXML
    public void button_init() {
        settingsButton.setStyle(IDLE_BUTTON_STYLE);
        settingsButton.setOnMouseEntered(e -> settingsButton.setStyle(HOVERED_BUTTON_STYLE));
        settingsButton.setOnMouseExited(e -> settingsButton.setStyle(IDLE_BUTTON_STYLE));

        profileButton.setStyle(IDLE_BUTTON_STYLE);
        profileButton.setOnMouseEntered(e -> profileButton.setStyle(HOVERED_BUTTON_STYLE));
        profileButton.setOnMouseExited(e -> profileButton.setStyle(IDLE_BUTTON_STYLE));

        logoutButton.setStyle(IDLE_BUTTON_STYLE);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(HOVERED_BUTTON_STYLE));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(IDLE_BUTTON_STYLE));

        addFriendButton.setStyle(IDLE_BUTTON_STYLE);
        addFriendButton.setOnMouseEntered(e -> addFriendButton.setStyle(HOVERED_BUTTON_STYLE));
        addFriendButton.setOnMouseExited(e -> addFriendButton.setStyle(IDLE_BUTTON_STYLE));

        removeFriendButton.setStyle(IDLE_BUTTON_STYLE);
        removeFriendButton.setOnMouseEntered(e -> removeFriendButton.setStyle(HOVERED_BUTTON_STYLE));
        removeFriendButton.setOnMouseExited(e -> removeFriendButton.setStyle(IDLE_BUTTON_STYLE));

        acceptButton.setStyle(IDLE_BUTTON_STYLE);
        acceptButton.setOnMouseEntered(e -> acceptButton.setStyle(HOVERED_BUTTON_STYLE));
        acceptButton.setOnMouseExited(e -> acceptButton.setStyle(IDLE_BUTTON_STYLE));

        rejectButton.setStyle(IDLE_BUTTON_STYLE);
        rejectButton.setOnMouseEntered(e -> rejectButton.setStyle(HOVERED_BUTTON_STYLE));
        rejectButton.setOnMouseExited(e -> rejectButton.setStyle(IDLE_BUTTON_STYLE));
    }

    private void fadeIn() {
        FadeTransition welcomeTransition = new FadeTransition(Duration.seconds(2));
        welcomeTransition.setNode(welcomeText);
        welcomeTransition.setFromValue(0);
        welcomeTransition.setToValue(0.5);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.4));

        FadeTransition welcomeOutTransition = new FadeTransition(Duration.seconds(2));
        welcomeOutTransition.setNode(welcomeText);
        welcomeOutTransition.setFromValue(0.5);
        welcomeOutTransition.setToValue(0);

        FadeTransition welcomeTitleTransition = new FadeTransition(Duration.millis(400));
        welcomeTitleTransition.setNode(welcomeTitle);
        welcomeTitleTransition.setFromValue(0);
        welcomeTitleTransition.setToValue(0.8);

        searchBar.setVisible(true);
        FadeTransition searchBarTransition = new FadeTransition(Duration.millis(400));
        searchBarTransition.setNode(searchBar);
        searchBarTransition.setFromValue(0);
        searchBarTransition.setToValue(1);

        menuButton.setVisible(true);
        FadeTransition menuButtonTransition = new FadeTransition(Duration.millis(400));
        menuButtonTransition.setNode(menuButton);
        menuButtonTransition.setFromValue(0);
        menuButtonTransition.setToValue(0.6);

        notificationImage.setVisible(true);
        FadeTransition notificationTransition = new FadeTransition(Duration.millis(400));
        notificationTransition.setNode(notificationImage);
        notificationTransition.setFromValue(0);
        notificationTransition.setToValue(0.6);

        friendList.setVisible(true);
        FadeTransition friendListTransition = new FadeTransition(Duration.millis(400));
        friendListTransition.setNode(friendList);
        friendListTransition.setFromValue(0);
        friendListTransition.setToValue(1);

        FadeTransition leftLineTransition = new FadeTransition(Duration.millis(400));
        leftLineTransition.setNode(leftLine);
        leftLineTransition.setFromValue(0);
        leftLineTransition.setToValue(0.5);

        FadeTransition righLineTransition = new FadeTransition(Duration.millis(400));
        righLineTransition.setNode(rightLine);
        righLineTransition.setFromValue(0);
        righLineTransition.setToValue(0.5);

        FadeTransition friendsTextTransition = new FadeTransition(Duration.millis(400));
        friendsTextTransition.setNode(friendsText);
        friendsTextTransition.setFromValue(0);
        friendsTextTransition.setToValue(0.5);

        addFriendButton.setVisible(true);
        FadeTransition addFriendTransition = new FadeTransition(Duration.millis(400));
        addFriendTransition.setNode(addFriendImage);
        addFriendTransition.setFromValue(0);
        addFriendTransition.setToValue(0.7);

        removeFriendButton.setVisible(true);
        FadeTransition removeFriendTransition = new FadeTransition(Duration.millis(400));
        removeFriendTransition.setNode(removeFriendImage);
        removeFriendTransition.setFromValue(0);
        removeFriendTransition.setToValue(0.7);

        messagesImage.setVisible(true);
        FadeTransition messagesTransition = new FadeTransition(Duration.millis(400));
        messagesTransition.setNode(messagesImage);
        messagesTransition.setFromValue(0);
        messagesTransition.setToValue(0.6);

        ParallelTransition loadScene = new ParallelTransition(welcomeTitleTransition,
                searchBarTransition,
                menuButtonTransition,
                notificationTransition,
                friendListTransition,
                leftLineTransition,
                righLineTransition,
                friendsTextTransition,
                addFriendTransition,
                removeFriendTransition,
                messagesTransition);

        SequentialTransition sequentialTransition = new SequentialTransition(welcomeTransition,
                pauseTransition,
                welcomeOutTransition,
                loadScene);
        sequentialTransition.play();
    }

    @FXML
    private void showMenu() {
        if (slideMenu.isVisible()) {
            notificationImage.setVisible(true);
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(230));
            fadeOutTransition.setNode(darkPane);
            fadeOutTransition.setFromValue(0.55);
            fadeOutTransition.setToValue(0);
            fadeOutTransition.play();

            TranslateTransition slideTransition = new TranslateTransition(Duration.millis(230));
            slideTransition.setNode(slideMenu);
            slideTransition.setFromX(0);
            slideTransition.setToX(-3);

            ParallelTransition slideParallel = new ParallelTransition(fadeOutTransition, slideTransition);
            slideParallel.play();
            slideParallel.setOnFinished(event -> {
                darkPane.setVisible(false);
                slideMenu.setVisible(false);
            });
        } else {
            darkPane.setVisible(true);
            slideMenu.setVisible(true);
            slideMenu.setOpacity(1);
            slideMenuButton.setOpacity(0.6);
            notificationImage.setVisible(false);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(230));
            fadeInTransition.setNode(darkPane);
            fadeInTransition.setFromValue(0);
            fadeInTransition.setToValue(0.55);

            TranslateTransition slideTransition = new TranslateTransition(Duration.millis(230));
            slideTransition.setNode(slideMenu);
            slideTransition.setFromX(-3);
            slideTransition.setToX(0);

            ParallelTransition slideParallel = new ParallelTransition(slideTransition, fadeInTransition);
            slideParallel.play();
        }
    }

    @FXML
    private void showNotifications() {
        if (notificationMenu.isVisible()) {
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(230));
            fadeOutTransition.setNode(darkPane);
            fadeOutTransition.setFromValue(0.55);
            fadeOutTransition.setToValue(0);
            fadeOutTransition.play();

            TranslateTransition slideTransition = new TranslateTransition(Duration.millis(230));
            slideTransition.setNode(notificationMenu);
            slideTransition.setFromX(0);
            slideTransition.setToX(-3);

            ParallelTransition slideParallel = new ParallelTransition(fadeOutTransition, slideTransition);
            slideParallel.play();
            slideParallel.setOnFinished(event -> {
                darkPane.setVisible(false);
                notificationMenu.setVisible(false);

            });
        } else {
            darkPane.setVisible(true);
            notificationMenu.setVisible(true);
            notificationMenu.setOpacity(1);
            notificationMenuImage.setOpacity(0.6);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(230));
            fadeInTransition.setNode(darkPane);
            fadeInTransition.setFromValue(0);
            fadeInTransition.setToValue(0.55);

            TranslateTransition slideTransition = new TranslateTransition(Duration.millis(230));
            slideTransition.setNode(notificationMenu);
            slideTransition.setFromX(-3);
            slideTransition.setToX(0);

            ParallelTransition slideParallel = new ParallelTransition(slideTransition, fadeInTransition);
            slideParallel.play();
        }
    }

    @FXML
    private void logOut() {
        showMenu();
        FadeTransition titleOut = new FadeTransition(Duration.millis(400));
        titleOut.setNode(welcomeTitle);
        titleOut.setFromValue(0.8);
        titleOut.setToValue(0);

        FadeTransition searchBarTransitionOut = new FadeTransition(Duration.millis(400));
        searchBarTransitionOut.setNode(searchBar);
        searchBarTransitionOut.setFromValue(1);
        searchBarTransitionOut.setToValue(0);


        FadeTransition menuButtonTransitionOut = new FadeTransition(Duration.millis(400));
        menuButtonTransitionOut.setNode(menuButton);
        menuButtonTransitionOut.setFromValue(0.6);
        menuButtonTransitionOut.setToValue(0.0);


        FadeTransition notificationTransitionOut = new FadeTransition(Duration.millis(400));
        notificationTransitionOut.setNode(notificationImage);
        notificationTransitionOut.setFromValue(0.6);
        notificationTransitionOut.setToValue(0);

        FadeTransition searchBarOut = new FadeTransition(Duration.millis(400));
        searchBarOut.setNode(searchBar);
        searchBarOut.setFromValue(0.52);
        searchBarOut.setToValue(0);

        FadeTransition rightLineOut = new FadeTransition(Duration.millis(400));
        rightLineOut.setNode(rightLine);
        rightLineOut.setFromValue(1);
        rightLineOut.setToValue(0);

        FadeTransition leftLineOut = new FadeTransition(Duration.millis(400));
        leftLineOut.setNode(leftLine);
        leftLineOut.setFromValue(1);
        leftLineOut.setToValue(0);

        FadeTransition friendsTextOut = new FadeTransition(Duration.millis(400));
        friendsTextOut.setNode(friendsText);
        friendsTextOut.setFromValue(1);
        friendsTextOut.setToValue(0);

        FadeTransition friendListOut = new FadeTransition(Duration.millis(400));
        friendListOut.setNode(friendList);
        friendListOut.setFromValue(1);
        friendListOut.setToValue(0);

        FadeTransition addFriendButtonOut = new FadeTransition(Duration.millis(400));
        addFriendButtonOut.setNode(addFriendButton);
        addFriendButtonOut.setFromValue(1);
        addFriendButtonOut.setToValue(0);

        FadeTransition removeFriendButtonOut = new FadeTransition(Duration.millis(400));
        removeFriendButtonOut.setNode(removeFriendButton);
        removeFriendButtonOut.setFromValue(1);
        removeFriendButtonOut.setToValue(0);

        ParallelTransition removeScene = new ParallelTransition(titleOut,
                searchBarTransitionOut,
                menuButtonTransitionOut,
                notificationTransitionOut,
                searchBarOut,
                rightLineOut,
                leftLineOut,
                friendsTextOut,
                friendListOut,
                addFriendButtonOut,
                removeFriendButtonOut);

        removeScene.play();
        searchBar.setVisible(false);
        menuButton.setVisible(false);
        notificationImage.setVisible(false);

        welcomeText.setText("See you soon, " + finalService.getUser().getFirstName() + "!");

        FadeTransition welcomeTransition = new FadeTransition(Duration.seconds(1));
        welcomeTransition.setNode(welcomeText);
        welcomeTransition.setFromValue(0);
        welcomeTransition.setToValue(0.5);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.4));

        FadeTransition welcomeOutTransition = new FadeTransition(Duration.seconds(1));
        welcomeOutTransition.setNode(welcomeText);
        welcomeOutTransition.setFromValue(0.5);
        welcomeOutTransition.setToValue(0);

        SequentialTransition seq = new SequentialTransition(removeScene,
                welcomeTransition,
                pauseTransition,
                welcomeOutTransition);

        seq.play();

        seq.setOnFinished(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 800, 500);
                Stage stage = (Stage) welcomeTitle.getScene().getWindow();

                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void closeApp() {
        Stage stage = (Stage) closeBtnImg.getScene().getWindow();
        stage.close();
    }

    private void loadListView() {
        Map<Integer, Friendship> friendships = friendshipService.getAll();

        for (Friendship friendship : friendships.values()) {
            if (Objects.equals(friendship.getId_user2(), finalService.getUser().getID())) {
                User current = userService.searchService(friendship.getId_user1());
                String row = current.getFirstName() + " " + current.getLastName() + " (@" + current.getID() + ")";
                friendList.getItems().add(row);
            }
            if (Objects.equals(friendship.getId_user1(), finalService.getUser().getID())) {
                User current = userService.searchService(friendship.getId_user2());
                String row = current.getFirstName() + " " + current.getLastName() + " (@" + current.getID() + ")";
                friendList.getItems().add(row);
            }
        }
    }

    private void loadUsers() {
        Map<String, User> users = userService.getAll();
        for (User user : users.values()) {
            if (!Objects.equals(user.getID(), finalService.getUser().getID())) {
                String row = user.getFirstName() + " " + user.getLastName();
                usersList.getItems().add(row);
            }
        }
    }

    private void loadNotifications() {
        Map<Pair<String, String>, Request> requests = requestService.getAll();
        for (Request request : requests.values()) {
            if (request.getStatus() == 0 && Objects.equals(request.getID().getSecond(), finalService.getUser().getID())) {
                String row = request.getID().getFirst() + " has\nsent you friend request!";
                notificationsListView.getItems().add(row);
            }
        }
    }

    @FXML
    private void showUserSuggestions() {
        usersList.setVisible(true);
    }

    private void deselectUser() {
        usersList.getSelectionModel().clearSelection();
    }
    @FXML
    private void hideUserSuggestions() {
        deselectUser();
        usersList.setVisible(false);
    }

    @FXML
    private void deselectFriend() {
        friendList.getSelectionModel().clearSelection();
    }


    @FXML
    private void onSelectedUser() {
        String user = usersList.getSelectionModel().getSelectedItem();
        if (user != null) {
            searchBar.setText(user);
            usersList.setVisible(false);
        }
    }

    @FXML
    private void onSelectedFriend() {
        String user = friendList.getSelectionModel().getSelectedItem();
        if (user != null) {
            int begin = user.indexOf("@");
            int end = user.indexOf(")");
            String user_id = user.substring(begin + 1, end);
            searchBar.setText(user_id);
        }
    }

    @FXML
    private void deleteFriend(ActionEvent event) {
        String user_id = searchBar.getText();
        if (user_id != null) {
            try {
                int id = finalService.getFriendshipId(user_id, finalService.getUser().getID());
                finalService.getFriendshipService().removeService(id);
                friendList.getItems().clear();
                loadListView();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void sendFriendRequest(ActionEvent event) {
        if (!searchBar.getText().isEmpty()) {
            try {
                int end = searchBar.getText().indexOf(" ");
                String firstName = searchBar.getText().substring(0, end);
                String lastName = searchBar.getText().substring(end + 1);
                String id = finalService.getUserId(firstName, lastName);
                Pair<String, String> pair = new Pair(finalService.getUser().getID(), id);
                Request newRequest = new Request(pair, LocalDate.now(), 0);
                finalService.getRequestService().addService(newRequest);
            } catch (ServiceException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    @FXML
    private void acceptRequest() {
        String notification = notificationsListView.getSelectionModel().getSelectedItem();
        if (notification != null) {
            try {
                int end = notification.indexOf(" ");
                String username = notification.substring(0, end);
                Friendship friendship = new Friendship(0, finalService.getUser().getID(), username, LocalDate.now());
                finalService.addFriend(friendship);

                Pair<String, String> pair = new Pair<>(username, finalService.getUser().getID());
                finalService.getRequestService().removeService(pair);
                notificationsListView.getItems().clear();
                loadNotifications();
                friendList.getItems().clear();
                loadListView();

            } catch (ServiceException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void rejectRequest() {
        String notification = notificationsListView.getSelectionModel().getSelectedItem();
        if (notification != null) {
            int end = notification.indexOf(" ");
            String username = notification.substring(0, end);

            Pair<String, String> pair = new Pair<>(username, finalService.getUser().getID());
            finalService.getRequestService().removeService(pair);
            notificationsListView.getItems().clear();
            loadNotifications();
        }
    }
}
