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
import com.example.socialnetworkui.utilities.Security;
import com.example.socialnetworkui.validator.FriendshipValidator;
import com.example.socialnetworkui.validator.RequestValidator;
import com.example.socialnetworkui.validator.UserValidator;
import com.example.socialnetworkui.validator.Validator;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import kotlin.Pair;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginController {
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
            "postgres", "root",requestValidator, "requests");
    Service<String, User> userService = new Service<>(userRepository);
    Service<Integer, Friendship> friendshipService = new Service<>(friendshipRepository);
    Service<Pair<String, String>, Request> requestService = new Service<>(requestReposiyory);
    FinalService finalService = FinalService.getInstance(userService, friendshipService, requestService);

    @FXML
    private Text wrongCombo;
    @FXML
    private ImageView loginImage;
    @FXML
    private ImageView closeBtnImg;
    @FXML
    private Text loginText;
    @FXML
    private Text loginQuestion;
    @FXML
    private Button signUpButton;
    @FXML
    private Button continueButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Line line1;
    @FXML
    private Line line2;

    public void closeApp() {
        Stage stage = (Stage) closeBtnImg.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        init_objects();
        init_button();
        fadeIn();

        Image img = new Image("file:images/loginImg2.png");
        loginImage.setImage(img);

        Rectangle clip = new Rectangle(336, 407);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        loginImage.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = loginImage.snapshot(parameters, null);
        loginImage.setImage(image);

        closeBtnImg.setImage(new Image("file:images/close.png"));
    }

    @FXML
    public void init_objects() {
        loginText.setOpacity(0);
        loginQuestion.setOpacity(0);
        signUpButton.setOpacity(0);
        continueButton.setOpacity(0);
        usernameField.setOpacity(0);
        passwordField.setOpacity(0);
        line1.setOpacity(0);
        line2.setOpacity(0);
        wrongCombo.setVisible(false);

        usernameField.setVisible(false);
        usernameField.setFocusTraversable(false);
        passwordField.setVisible(false);
        passwordField.setFocusTraversable(false);
    }

    @FXML
    private void init_button() {
        continueButton.setStyle(IDLE_BUTTON_STYLE);
        continueButton.setOnMouseEntered(e -> continueButton.setStyle(HOVERED_BUTTON_STYLE));
        continueButton.setOnMouseExited(e -> continueButton.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    private void fadeOut(String fxmlScene) {
        FadeTransition questionTransition = new FadeTransition(Duration.millis(600));
        questionTransition.setNode(loginQuestion);
        questionTransition.setFromValue(1);
        questionTransition.setToValue(0);

        FadeTransition signUpTransition = new FadeTransition(Duration.millis(600));
        signUpTransition.setNode(signUpButton);
        signUpTransition.setFromValue(1);
        signUpTransition.setToValue(0);

        FadeTransition buttonTransition = new FadeTransition(Duration.millis(600));
        buttonTransition.setNode(continueButton);
        buttonTransition.setFromValue(1);
        buttonTransition.setToValue(0);

        FadeTransition errTransition = new FadeTransition(Duration.millis(600));
        errTransition.setNode(wrongCombo);
        errTransition.setFromValue(wrongCombo.getOpacity());
        errTransition.setToValue(0);

        ParallelTransition questionParallel = new ParallelTransition(signUpTransition, questionTransition, buttonTransition, errTransition);

        FadeTransition passwordTransition = new FadeTransition(Duration.millis(300));
        passwordTransition.setNode(passwordField);
        passwordTransition.setFromValue(1);
        passwordTransition.setToValue(0);

        FadeTransition line2Transition = new FadeTransition(Duration.millis(300));
        line2Transition.setNode(line2);
        line2Transition.setFromValue(0.2);
        line2Transition.setToValue(0);

        ParallelTransition passwordParallel = new ParallelTransition(line2Transition, passwordTransition);

        FadeTransition usernameTransition = new FadeTransition(Duration.millis(300));
        usernameTransition.setNode(usernameField);
        usernameTransition.setFromValue(1);
        usernameTransition.setToValue(0);

        FadeTransition line1Transition = new FadeTransition(Duration.millis(300));
        line1Transition.setNode(line1);
        line1Transition.setFromValue(0.2);
        line1Transition.setToValue(0);

        ParallelTransition usernameParallel = new ParallelTransition(line1Transition, usernameTransition);

        FadeTransition loginTextTransition = new FadeTransition(Duration.millis(300));
        loginTextTransition.setNode(loginText);
        loginTextTransition.setFromValue(1);
        loginTextTransition.setToValue(0);

        SequentialTransition seq = new SequentialTransition(questionParallel,
                passwordParallel,
                usernameParallel,
                loginTextTransition);
        seq.play();

        seq.setOnFinished(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlScene));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 800, 500);
                scene.getStylesheets().add("file:sheets/interfaceSheet.css");
                Stage stage = (Stage) signUpButton.getScene().getWindow();

                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void changeToSignUp() {
        fadeOut("signup.fxml");
    }

    @FXML
    public void fadeIn() {
        FadeTransition loginTextTransition = new FadeTransition(Duration.millis(1000));
        loginTextTransition.setNode(loginText);
        loginTextTransition.setFromValue(0);
        loginTextTransition.setToValue(1);

        usernameField.setVisible(true);
        FadeTransition usernameTransition = new FadeTransition(Duration.millis(300));
        usernameTransition.setNode(usernameField);
        usernameTransition.setFromValue(0);
        usernameTransition.setToValue(1);

        FadeTransition line1Transition = new FadeTransition(Duration.millis(300));
        line1Transition.setNode(line1);
        line1Transition.setFromValue(0);
        line1Transition.setToValue(0.2);

        ParallelTransition usernameParallel = new ParallelTransition(usernameTransition, line1Transition);

        passwordField.setVisible(true);
        FadeTransition passwordTransition = new FadeTransition(Duration.millis(300));
        passwordTransition.setNode(passwordField);
        passwordTransition.setFromValue(0);
        passwordTransition.setToValue(1);

        FadeTransition line2Transition = new FadeTransition(Duration.millis(300));
        line2Transition.setNode(line2);
        line2Transition.setFromValue(0);
        line2Transition.setToValue(0.2);

        ParallelTransition passwordParallel = new ParallelTransition(passwordTransition, line2Transition);

        FadeTransition buttonTransition = new FadeTransition(Duration.millis(300));
        buttonTransition.setNode(continueButton);
        buttonTransition.setFromValue(0);
        buttonTransition.setToValue(1);

        FadeTransition questionTransition = new FadeTransition(Duration.millis(1000));
        questionTransition.setNode(loginQuestion);
        questionTransition.setFromValue(0);
        questionTransition.setToValue(1);

        FadeTransition signUpTransition = new FadeTransition(Duration.millis(1000));
        signUpTransition.setNode(signUpButton);
        signUpTransition.setFromValue(0);
        signUpTransition.setToValue(1);

        ParallelTransition questionParallel = new ParallelTransition(questionTransition, signUpTransition);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));

        SequentialTransition seq = new SequentialTransition(pauseTransition, loginTextTransition,
                usernameParallel,
                passwordParallel,
                buttonTransition,
                questionParallel);
        seq.play();
    }

    public void scale() {
        FadeTransition fadeTransitionImage = new FadeTransition(Duration.millis(350));
        fadeTransitionImage.setNode(loginImage);
        fadeTransitionImage.setFromValue(1);
        fadeTransitionImage.setToValue(0);

        fadeOut("interface.fxml");
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1200));

        SequentialTransition seq = new SequentialTransition(pauseTransition, fadeTransitionImage);
        seq.play();
    }

    @FXML
    public void continueButtonAction() throws NoSuchAlgorithmException {
        if (usernameField.getText().isEmpty()) {
            line1.setStroke(Paint.valueOf("#dc3e3e"));
            line1.setOpacity(0.7);
        } else {
            line1.setStroke(Paint.valueOf("#5a5a5a"));
            line1.setOpacity(0.2);
        }

        if (passwordField.getText().isEmpty()) {
            line2.setStroke(Paint.valueOf("#dc3e3e"));
            line2.setOpacity(0.7);
        } else {
            line2.setStroke(Paint.valueOf("#5a5a5a"));
            line2.setOpacity(0.2);
        }

        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            User user = finalService.getUserService().searchService(usernameField.getText());
            if (user != null) {
                String passwd = passwordField.getText();
                Security security = new Security();
                passwd = security.encrypt(passwd);

                if (Objects.equals(user.getPassword(), passwd)) {
                    finalService.setUser(user);
                    scale();
                } else {
                    wrongCombo.setVisible(true);
                }
            } else {
                wrongCombo.setVisible(true);
            }
        } else {
            wrongCombo.setVisible(false);
        }
    }

}
