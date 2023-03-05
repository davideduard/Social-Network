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
import com.example.socialnetworkui.validator.*;
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

public class SignupController {
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color ,-fx-outer-border;";
    private final UiUserDataValidator validator = new UiUserDataValidator();
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
    private Text errorText;
    @FXML
    private Text mandatoryText;
    @FXML
    private Pane leftPane;
    @FXML
    private ImageView loginImage;
    @FXML
    private ImageView closeBtnImg;
    @FXML
    private Text signupText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Line line1;
    @FXML
    private Line line2;
    @FXML
    private Line line3;
    @FXML
    private Line line4;
    @FXML
    private Line line5;
    @FXML
    private Text welcomeText;
    @FXML
    private Text loginQuestion;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpBtn;

    @FXML
    public void initialize() {
        transitions();
        button_init();

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
    public void closeApp() {
        Stage stage = (Stage) closeBtnImg.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initObjects() {

        signupText.setOpacity(0);
        usernameText.setOpacity(0);
        firstNameText.setOpacity(0);
        lastNameText.setOpacity(0);
        passwordField.setOpacity(0);
        confirmPasswordField.setOpacity(0);
        line1.setOpacity(0);
        line2.setOpacity(0);
        line3.setOpacity(0);
        line4.setOpacity(0);
        line5.setOpacity(0);
        welcomeText.setOpacity(1);
        loginQuestion.setOpacity(0);
        loginButton.setOpacity(0);
        signUpBtn.setOpacity(0);
        mandatoryText.setOpacity(0);

        usernameText.setFocusTraversable(false);

    }

    @FXML
    public void button_init() {
        signUpBtn.setStyle(IDLE_BUTTON_STYLE);
        signUpBtn.setOnMouseEntered(e -> signUpBtn.setStyle(HOVERED_BUTTON_STYLE));
        signUpBtn.setOnMouseExited(e -> signUpBtn.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    public void transitions() {
        initObjects();
        fadeIn();
    }

    @FXML
    public void fadeIn() {
        FadeTransition welcomeTransition = new FadeTransition(Duration.millis(600));
        welcomeTransition.setNode(welcomeText);
        welcomeTransition.setFromValue(0);
        welcomeTransition.setToValue(1);

        FadeTransition signupTransition = new FadeTransition(Duration.millis(600));
        signupTransition.setNode(signupText);
        signupTransition.setFromValue(0);
        signupTransition.setToValue(1);

        FadeTransition usernameTransition = new FadeTransition(Duration.millis(300));
        usernameTransition.setNode(usernameText);
        usernameTransition.setFromValue(0);
        usernameTransition.setToValue(1);

        FadeTransition line1Transition = new FadeTransition(Duration.millis(300));
        line1Transition.setNode(line1);
        line1Transition.setFromValue(0);
        line1Transition.setToValue(0.2);

        ParallelTransition usernameParallel = new ParallelTransition(usernameTransition, line1Transition);

        FadeTransition firstNameTransition = new FadeTransition(Duration.millis(300));
        firstNameTransition.setNode(firstNameText);
        firstNameTransition.setFromValue(0);
        firstNameTransition.setToValue(1);

        FadeTransition line2Transition = new FadeTransition(Duration.millis(300));
        line2Transition.setNode(line2);
        line2Transition.setFromValue(0);
        line2Transition.setToValue(0.2);

        ParallelTransition firstNameParallel = new ParallelTransition(firstNameTransition, line2Transition);

        FadeTransition lastNameTransition = new FadeTransition(Duration.millis(300));
        lastNameTransition.setNode(lastNameText);
        lastNameTransition.setFromValue(0);
        lastNameTransition.setToValue(1);

        FadeTransition line3Transition = new FadeTransition(Duration.millis(300));
        line3Transition.setNode(line3);
        line3Transition.setFromValue(0);
        line3Transition.setToValue(0.2);

        ParallelTransition lastNameParallel = new ParallelTransition(lastNameTransition, line3Transition);

        FadeTransition passwordTransition = new FadeTransition(Duration.millis(300));
        passwordTransition.setNode(passwordField);
        passwordTransition.setFromValue(0);
        passwordTransition.setToValue(1);

        FadeTransition line4Transition = new FadeTransition(Duration.millis(300));
        line4Transition.setNode(line4);
        line4Transition.setFromValue(0);
        line4Transition.setToValue(0.2);

        ParallelTransition passwordParallel = new ParallelTransition(passwordTransition, line4Transition);

        FadeTransition confirmPasswordTransition = new FadeTransition(Duration.millis(300));
        confirmPasswordTransition.setNode(confirmPasswordField);
        confirmPasswordTransition.setFromValue(0);
        confirmPasswordTransition.setToValue(1);

        FadeTransition line5Transition = new FadeTransition(Duration.millis(300));
        line5Transition.setNode(line5);
        line5Transition.setFromValue(0);
        line5Transition.setToValue(0.2);

        ParallelTransition confirmPasswordParallel = new ParallelTransition(confirmPasswordTransition, line5Transition);

        FadeTransition buttonTransition = new FadeTransition(Duration.millis(300));
        buttonTransition.setNode(signUpBtn);
        buttonTransition.setFromValue(0);
        buttonTransition.setToValue(1);

        FadeTransition questionTransition = new FadeTransition(Duration.millis(1000));
        questionTransition.setNode(loginQuestion);
        questionTransition.setFromValue(0);
        questionTransition.setToValue(1);

        FadeTransition labelTransition = new FadeTransition(Duration.millis(1000));
        labelTransition.setNode(loginButton);
        labelTransition.setFromValue(0);
        labelTransition.setToValue(1);

        FadeTransition mandatoryTextTransition = new FadeTransition(Duration.millis(1000));
        mandatoryTextTransition.setNode(mandatoryText);
        mandatoryTextTransition.setFromValue(0);
        mandatoryTextTransition.setToValue(0.5);

        ParallelTransition parallelTransition = new ParallelTransition(questionTransition, labelTransition, mandatoryTextTransition);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));

        SequentialTransition seq = new SequentialTransition(pauseTransition, signupTransition,
                usernameParallel,
                firstNameParallel,
                lastNameParallel,
                passwordParallel,
                confirmPasswordParallel,
                buttonTransition,
                parallelTransition);
        seq.play();
    }

    @FXML
    public void fadeOut(String fxmlFile) {
        FadeTransition buttonTransition = new FadeTransition(Duration.millis(600));
        buttonTransition.setNode(signUpBtn);
        buttonTransition.setFromValue(1);
        buttonTransition.setToValue(0);

        FadeTransition textTransition = new FadeTransition(Duration.millis(600));
        textTransition.setNode(loginQuestion);
        textTransition.setFromValue(1);
        textTransition.setToValue(0);

        FadeTransition loginTransition = new FadeTransition(Duration.millis(600));
        loginTransition.setNode(loginButton);
        loginTransition.setFromValue(1);
        loginTransition.setToValue(0);

        FadeTransition mandatoryTextTransition = new FadeTransition(Duration.millis(600));
        mandatoryTextTransition.setNode(mandatoryText);
        mandatoryTextTransition.setFromValue(0.5);
        mandatoryTextTransition.setToValue(0);

        FadeTransition errFade = new FadeTransition(Duration.millis(600));
        errFade.setNode(errorText);
        errFade.setFromValue(errorText.getOpacity());
        errFade.setToValue(0);

        ParallelTransition questionParallel = new ParallelTransition(loginTransition, textTransition, buttonTransition, mandatoryTextTransition, errFade);

        FadeTransition confirmPasswordTransition = new FadeTransition(Duration.millis(300));
        confirmPasswordTransition.setNode(confirmPasswordField);
        confirmPasswordTransition.setFromValue(1);
        confirmPasswordTransition.setToValue(0);

        FadeTransition line5Transition = new FadeTransition(Duration.millis(300));
        line5Transition.setNode(line5);
        line5Transition.setFromValue(0.2);
        line5Transition.setToValue(0);

        ParallelTransition confirmParalel = new ParallelTransition(confirmPasswordTransition, line5Transition);

        FadeTransition passwordTransition = new FadeTransition(Duration.millis(300));
        passwordTransition.setNode(passwordField);
        passwordTransition.setFromValue(1);
        passwordTransition.setToValue(0);

        FadeTransition line4Transition = new FadeTransition(Duration.millis(300));
        line4Transition.setNode(line4);
        line4Transition.setFromValue(0.2);
        line4Transition.setToValue(0);

        ParallelTransition passwordParallel = new ParallelTransition(passwordTransition, line4Transition);

        FadeTransition lastNameTransition = new FadeTransition(Duration.millis(300));
        lastNameTransition.setNode(lastNameText);
        lastNameTransition.setFromValue(1);
        lastNameTransition.setToValue(0);

        FadeTransition line3Transition = new FadeTransition(Duration.millis(300));
        line3Transition.setNode(line3);
        line3Transition.setFromValue(0.2);
        line3Transition.setToValue(0);

        ParallelTransition lastNameParallel = new ParallelTransition(lastNameTransition, line3Transition);

        FadeTransition firstNameTransition = new FadeTransition(Duration.millis(300));
        firstNameTransition.setNode(firstNameText);
        firstNameTransition.setFromValue(1);
        firstNameTransition.setToValue(0);

        FadeTransition line2Transition = new FadeTransition(Duration.millis(300));
        line2Transition.setNode(line2);
        line2Transition.setFromValue(0.2);
        line2Transition.setToValue(0);

        ParallelTransition firstNameParallel = new ParallelTransition(line2Transition, firstNameTransition);

        FadeTransition usernameTransition = new FadeTransition(Duration.millis(300));
        usernameTransition.setNode(usernameText);
        usernameTransition.setFromValue(1);
        usernameTransition.setToValue(0);

        FadeTransition line1Transition = new FadeTransition(Duration.millis(300));
        line1Transition.setNode(line1);
        line1Transition.setFromValue(0.2);
        line1Transition.setToValue(0);

        ParallelTransition usernameParallel = new ParallelTransition(line1Transition, usernameTransition);

        FadeTransition signUpTextTransition = new FadeTransition(Duration.millis(300));
        signUpTextTransition.setNode(signupText);
        signUpTextTransition.setFromValue(1);
        signUpTextTransition.setToValue(0);


        SequentialTransition seq = new SequentialTransition(questionParallel,
                confirmParalel,
                passwordParallel,
                lastNameParallel,
                firstNameParallel,
                usernameParallel,
                signUpTextTransition);
        seq.play();

        seq.setOnFinished(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 800, 500);
                scene.getStylesheets().add("file:sheets/interfaceSheet.css");
                Stage stage = (Stage) signUpBtn.getScene().getWindow();

                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void scale() {

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400));
        fadeTransition.setNode(leftPane);
        fadeTransition.setFromValue(0.87);
        fadeTransition.setToValue(0);

        FadeTransition fadeTransitionImage = new FadeTransition(Duration.millis(400));
        fadeTransitionImage.setNode(loginImage);
        fadeTransitionImage.setFromValue(1);
        fadeTransitionImage.setToValue(0);

        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition,
                fadeTransitionImage);

        fadeOut("interface.fxml");
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(2100));

        SequentialTransition seq = new SequentialTransition(pauseTransition, parallelTransition);
        seq.play();
    }

    @FXML
    public void changeToLogin() {
        fadeOut("login.fxml");
    }

    @FXML
    public void continueButtonAction() {
        mandatoryText.setVisible(true);
        if (usernameText.getText().isEmpty()) {
            line1.setStroke(Paint.valueOf("#dc3e3e"));
            line1.setOpacity(0.7);
        } else {
            line1.setStroke(Paint.valueOf("#5a5a5a"));
            line1.setOpacity(0.2);
        }

        if (firstNameText.getText().isEmpty()){
            line2.setStroke(Paint.valueOf("#dc3e3e"));
            line2.setOpacity(0.7);
        } else {
            line2.setStroke(Paint.valueOf("#5a5a5a"));
            line2.setOpacity(0.2);
        }

        if (lastNameText.getText().isEmpty()){
            line3.setStroke(Paint.valueOf("#dc3e3e"));
            line3.setOpacity(0.7);
        } else {
            line3.setStroke(Paint.valueOf("#5a5a5a"));
            line3.setOpacity(0.2);
        }

        if (passwordField.getText().isEmpty()){
            line4.setStroke(Paint.valueOf("#dc3e3e"));
            line4.setOpacity(0.7);
        } else {
            line4.setStroke(Paint.valueOf("#5a5a5a"));
            line4.setOpacity(0.2);
        }

        if (confirmPasswordField.getText().isEmpty()){
            line5.setStroke(Paint.valueOf("#dc3e3e"));
            line5.setOpacity(0.7);
        } else {
            line5.setStroke(Paint.valueOf("#5a5a5a"));
            line5.setOpacity(0.2);
        }

        if (!usernameText.getText().isEmpty() && !firstNameText.getText().isEmpty() && !lastNameText.getText().isEmpty() && !confirmPasswordField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            try {
                validator.validate(usernameText.getText(), firstNameText.getText(), lastNameText.getText(), passwordField.getText(), confirmPasswordField.getText());
                if (Objects.equals(passwordField.getText(), confirmPasswordField.getText())){
                    String passwd = passwordField.getText();
                    Security security = new Security();
                    passwd = security.encrypt(passwd);

                    User newUser = new User(usernameText.getText(), firstNameText.getText(), lastNameText.getText(), passwd);
                    userValidator.validate(newUser);
                    if (finalService.getUserService().searchService(usernameText.getText()) != null){
                        errorText.setText("username is already in use");
                        //return;
                    }
                    finalService.getUserService().addService(newUser);
                    finalService.setUser(newUser);
                    scale();
                }
            } catch (ValidatorException ex){
                mandatoryText.setVisible(false);
                errorText.setText(ex.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
