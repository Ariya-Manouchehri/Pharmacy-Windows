package com.example.pharmacyappd;

import com.example.pharmacyappd.model.LoginResponse;
import com.example.pharmacyappd.model.User;
import com.example.pharmacyappd.model.UserAndToken;
import com.example.pharmacyappd.model.UserResponse;
import com.example.pharmacyappd.repository.Repository;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;


public class LogIn extends Application {
    Stage currentPrimaryStage;
    Stage new_activity;

    Scene loginScene;
    Scene forgotPasswordScene;
    Scene codeVerificationScene;
    Scene changePasswordScene;
    Scene managerScene;
    Scene remove_drug_scene;
    Scene add_drug_scene;
    UserAndToken currentUser;
    boolean countScene1 = true;
    boolean countScene2 = true;
    String forgotPasswordPhone;
    Repository repository = Repository.Companion.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentPrimaryStage = primaryStage;

        // FXML loaders
        Parent loginRoot = FXMLLoader.load(ClassLoader.getSystemResource("fxml/login.fxml"));
        Parent forgotPasswordRoot = FXMLLoader.load(ClassLoader.getSystemResource("fxml/forgot_password.fxml"));
        Parent codeVerificationRoot = FXMLLoader.load(ClassLoader.getSystemResource("fxml/code_verification.fxml"));
        Parent changePasswordRoot = FXMLLoader.load(ClassLoader.getSystemResource("fxml/change_password.fxml"));
        Parent remove_drug_Root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/remove_drug.fxml"));
        Parent add_drug_Root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/add_drug.fxml"));

        // scenes
        loginScene = new Scene(loginRoot, 700, 450);
        forgotPasswordScene = new Scene(forgotPasswordRoot, 700, 450);
        codeVerificationScene = new Scene(codeVerificationRoot, 700, 450);
        changePasswordScene = new Scene(changePasswordRoot, 700, 450);
        remove_drug_scene = new Scene(remove_drug_Root, 600, 280);
        add_drug_scene = new Scene(add_drug_Root, 600, 280);

        currentPrimaryStage.setTitle("Pharmacy");
        currentPrimaryStage.getIcons().add(new Image("res/pills.png"));
        currentPrimaryStage.setScene(loginScene);
        currentPrimaryStage.setResizable(false);
        new_activity = new Stage();
        ImageView drugIconLogin = (ImageView) loginRoot.lookup("#drug_icon");
        animation(drugIconLogin);
        currentPrimaryStage.show();


        JFXTextField forgotPasswordPhoneEditText = (JFXTextField) forgotPasswordRoot.lookup("#phone_edit_text");
        Label ForgotPassword = (Label) loginRoot.lookup("#forgot_password");
        ImageView drugIconForgotPassword = (ImageView) forgotPasswordRoot.lookup("#drug_icon");
        ForgotPassword.setOnMouseClicked(event -> {
            currentPrimaryStage.setScene(forgotPasswordScene);
            if (countScene1) {
                animation(drugIconForgotPassword);
            }
            countScene1 = false;

        });

        Button login = (Button) loginRoot.lookup("#button");
        JFXTextField phoneEditText = (JFXTextField) loginRoot.lookup("#phone_edit_text");
        JFXPasswordField passwordEditText = (JFXPasswordField) loginRoot.lookup("#password_edit_text");
        Label loginErrorLabel = (Label) loginRoot.lookup("#error_label");
        SimpleObjectProperty<Response<LoginResponse>> loginResponse = new SimpleObjectProperty<>();
        loginResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                LoginResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    currentUser = response.getResult().get(0);
                    repository.setAccessToken(currentUser.getToken());
                    loginErrorLabel.setText("");
                    Image path = new Image("res/icons8-male-user-100.png", false);
                    Parent managerRoot = null;
                    try {
                        managerRoot = FXMLLoader.load(ClassLoader.getSystemResource("fxml/manager.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert managerRoot != null;
                    managerScene = new Scene(managerRoot, 400, 400);
                    Circle circleImage = (Circle) managerRoot.lookup("#circle_image");
                    circleImage.setFill(new ImagePattern(path));

                    JFXButton remove_drug_button = (JFXButton) managerRoot.lookup("#remove_drug");
                    remove_drug_button.setOnAction(event -> {
                        new_activity.setScene(remove_drug_scene);
                        new_activity.show();
                    });

                    JFXButton add_drug_button = (JFXButton) managerRoot.lookup("#add_drug");
                    add_drug_button.setOnAction(event -> {
                        new_activity.setScene(add_drug_scene);
                        new_activity.show();
                    });

                    currentPrimaryStage.setScene(managerScene);
                    currentPrimaryStage.setMaximized(true);
                } else {
                    loginErrorLabel.setText("User Not Found");
                }
            } else
                loginErrorLabel.setText("Connection Error");
        }));

        login.setOnAction(event -> {
            String phone = phoneEditText.getText();
            String password = passwordEditText.getText();
            if (checkStringNumber(phone) && !password.isEmpty()) {
                Task onLogin = new Task() {
                    @Override
                    protected Object call() {
                        loginResponse.set(repository.login(phone, password));
                        return null;
                    }
                };
                Thread thread = new Thread(onLogin);
                thread.start();
            } else if (phone.isEmpty() && password.isEmpty()) {
                loginErrorLabel.setText("Enter your phone and password");
            } else if (phone.isEmpty()) {
                loginErrorLabel.setText("Enter your phone");
            } else if (password.isEmpty()) {
                loginErrorLabel.setText("Enter your password");
            } else {
                loginErrorLabel.setText("Enter a valid phone");
            }
        });

        Button cancelButtonForgotPassword = (Button) forgotPasswordRoot.lookup("#cancel");
        Button nextButtonForgotPassword = (Button) forgotPasswordRoot.lookup("#next");
        ImageView drugIconCodeVerification = (ImageView) codeVerificationRoot.lookup("#drug_icon");
        cancelButtonForgotPassword.setOnAction(event -> currentPrimaryStage.setScene(loginScene));
        Label forgotPasswordErrorLabel = (Label) forgotPasswordRoot.lookup("#error_label");

        nextButtonForgotPassword.setOnAction(event -> {
            String phone = forgotPasswordPhoneEditText.getText();
            if (checkStringNumber(phone)) {
                forgotPasswordErrorLabel.setText("");
                forgotPasswordPhone = phone;
                currentPrimaryStage.setScene(codeVerificationScene);
                if (countScene2) {
                    animation(drugIconCodeVerification);
                }
                countScene2 = false;
            } else if (phone.isEmpty()) {
                forgotPasswordErrorLabel.setText("Enter your phone");
            } else {
                forgotPasswordErrorLabel.setText("Enter a valid phone");
            }
        });

        Button cancelButtonCodeVerification = (Button) codeVerificationRoot.lookup("#cancel");
        Button nextButtonCodeVerification = (Button) codeVerificationRoot.lookup("#next");
        ImageView drugIconChangePassword = (ImageView) changePasswordRoot.lookup("#drug_icon");
        cancelButtonCodeVerification.setOnAction(event -> currentPrimaryStage.setScene(forgotPasswordScene));

        SimpleObjectProperty<Response<UserResponse>> findUserResponse = new SimpleObjectProperty<>();
        findUserResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                UserResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    currentPrimaryStage.setScene(changePasswordScene);
                    animation(drugIconChangePassword);
                }
                // TODO show error
            }
            // TODO show error
        }));

        JFXTextField verificationNationalCodeEditText = (JFXTextField) codeVerificationRoot.lookup("#national_code_edit_text");
        nextButtonCodeVerification.setOnAction(event -> {
            String nationalCode = verificationNationalCodeEditText.getText();
            if (checkStringNumber(nationalCode)) {
                Task onReset = new Task() {
                    @Override
                    protected Object call() {
                        findUserResponse.set(repository.findUser(forgotPasswordPhone, nationalCode));
                        return null;
                    }
                };
                Thread thread = new Thread(onReset);
                thread.start();
            } else {
                // TODO show error
            }

        });

        Label changePasswordErrorLabel = (Label) changePasswordRoot.lookup("#error_label");
        SimpleObjectProperty<Response<UserResponse>> changePasswordResponse = new SimpleObjectProperty<>();
        changePasswordResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                UserResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    changePasswordErrorLabel.setText("");
                    currentPrimaryStage.setScene(loginScene);
                } else {
                    changePasswordErrorLabel.setText("Something went wrong");
                }
            } else {
                changePasswordErrorLabel.setText("Connection Error");
            }
        }));

        Button changePassword = (Button) changePasswordRoot.lookup("#button");
        JFXTextField newPasswordEditText = (JFXTextField) changePasswordRoot.lookup("#new_password_edit_text");
        JFXTextField confirmPasswordEditText = (JFXTextField) changePasswordRoot.lookup("#confirm_password_edit_text");
        changePassword.setOnAction(event -> {
            if (newPasswordEditText.getText().isEmpty() && confirmPasswordEditText.getText().isEmpty()) {
                changePasswordErrorLabel.setText("Enter your new password");
            } else if (newPasswordEditText.getText().isEmpty()) {
                changePasswordErrorLabel.setText("Enter your new password");
            } else if (confirmPasswordEditText.getText().isEmpty()) {
                changePasswordErrorLabel.setText("Enter your password again");
            } else if (newPasswordEditText.getText().length() < 8) {
                changePasswordErrorLabel.setText("Password should be at least 8 character");
            } else if (!newPasswordEditText.getText().equals(confirmPasswordEditText.getText())) {
                changePasswordErrorLabel.setText("Your passwords don't match each other");
            } else {
                User user = Objects.requireNonNull(findUserResponse.getValue().body()).getResult().get(0);
                Task onChangPassword = new Task() {
                    @Override
                    protected Object call() {
                        changePasswordResponse.set(repository.resetPassword(user.getId(), newPasswordEditText.getText()));
                        return null;
                    }
                };
                Thread thread = new Thread(onChangPassword);
                thread.start();
            }
        });
    }

    public boolean checkStringNumber(String number) {
        if (number.length() <= 0) return false;
        for (char c : number.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public void animation(ImageView enter) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1));
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1));
        translateTransition.setToY(-10);
        scaleTransition.setToY(1.2);
        scaleTransition.setToX(1.2);
        translateTransition.setAutoReverse(true);
        scaleTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setNode(enter);
        scaleTransition.setNode(enter);
        translateTransition.play();
        scaleTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}