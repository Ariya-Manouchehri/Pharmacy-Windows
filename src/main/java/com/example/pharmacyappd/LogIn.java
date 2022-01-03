package com.example.pharmacyappd;

import com.example.pharmacyappd.model.LoginResponse;
import com.example.pharmacyappd.model.User;
import com.example.pharmacyappd.model.UserAndToken;
import com.example.pharmacyappd.model.UserResponse;
import com.example.pharmacyappd.repository.Repository;
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

import java.util.Objects;


public class LogIn extends Application {
    Stage currentPrimaryStage;
    Scene loginScene;
    Scene forgotPasswordScene;
    Scene codeVerificationScene;
    Scene changePasswordScene;
    Scene managerScene;
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
        Parent managerRoot = FXMLLoader.load(ClassLoader.getSystemResource("fxml/manager.fxml"));

        // scenes
        loginScene = new Scene(loginRoot, 700, 450);
        forgotPasswordScene = new Scene(forgotPasswordRoot, 700, 450);
        codeVerificationScene = new Scene(codeVerificationRoot, 700, 450);
        changePasswordScene = new Scene(changePasswordRoot, 700, 450);
        managerScene = new Scene(managerRoot);

        currentPrimaryStage.setTitle("Pharmacy");
        currentPrimaryStage.getIcons().add(new Image("res/pills.png"));
        currentPrimaryStage.setScene(loginScene);
        currentPrimaryStage.setResizable(false);
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
        Circle circleImage = (Circle) managerRoot.lookup("#circle_image");
        JFXTextField phoneEditText = (JFXTextField) loginRoot.lookup("#phone_edit_text");
        JFXPasswordField passwordEditText = (JFXPasswordField) loginRoot.lookup("#password_edit_text");

        SimpleObjectProperty<Response<LoginResponse>> loginResponse = new SimpleObjectProperty<>();
        loginResponse.addListener((observable, oldValue, newValue) -> {
            if (newValue.isSuccessful()) {
                LoginResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    Platform.runLater(() -> {
                        currentUser = response.getResult().get(0);
                        Image path = new Image("res/icons8-male-user-100.png", false);
                        circleImage.setFill(new ImagePattern(path));
                        currentPrimaryStage.setMaximized(true);
                        currentPrimaryStage.setScene(managerScene);
                    });
                }
                // TODO show error

            }
            // TODO show error
        });

        login.setOnAction(event -> {
            String phone = phoneEditText.getText();
            String password = passwordEditText.getText();
            if (checkStringNumber(phone)) {
                Task onLogin = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        loginResponse.set(repository.login(phone, password));
                        return null;
                    }
                };
                Thread thread = new Thread(onLogin);
                thread.start();
            } else {
                // TODO show error
            }
        });

        Button cancelButtonForgotPassword = (Button) forgotPasswordRoot.lookup("#cancel");
        Button nextButtonForgotPassword = (Button) forgotPasswordRoot.lookup("#next");
        ImageView drugIconCodeVerification = (ImageView) codeVerificationRoot.lookup("#drug_icon");
        cancelButtonForgotPassword.setOnAction(event -> currentPrimaryStage.setScene(loginScene));

        nextButtonForgotPassword.setOnAction(event -> {
            String phone = forgotPasswordPhoneEditText.getText();
            if (checkStringNumber(phone)) {
                forgotPasswordPhone = phone;
                currentPrimaryStage.setScene(codeVerificationScene);
                if (countScene2) {
                    animation(drugIconCodeVerification);
                }
                countScene2 = false;
            } else {
                // TODO show error
            }
        });

        Button cancelButtonCodeVerification = (Button) codeVerificationRoot.lookup("#cancel");
        Button nextButtonCodeVerification = (Button) codeVerificationRoot.lookup("#next");
        ImageView drugIconChangePassword = (ImageView) changePasswordRoot.lookup("#drug_icon");
        cancelButtonCodeVerification.setOnAction(event -> currentPrimaryStage.setScene(forgotPasswordScene));

        SimpleObjectProperty<Response<UserResponse>> findUserResponse = new SimpleObjectProperty<>();
        findUserResponse.addListener((observable, oldValue, newValue) -> {
            if (newValue.isSuccessful()) {
                UserResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    Platform.runLater(() -> {
                        currentPrimaryStage.setScene(changePasswordScene);
                        animation(drugIconChangePassword);
                    });
                }
                // TODO show error

            }
            // TODO show error

        });

        JFXTextField verificationNationalCodeEditText = (JFXTextField) codeVerificationRoot.lookup("#national_code_edit_text");
        nextButtonCodeVerification.setOnAction(event -> {
            String nationalCode = verificationNationalCodeEditText.getText();
            if (checkStringNumber(nationalCode)) {
                Task onReset = new Task() {
                    @Override
                    protected Object call() throws Exception {
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

        SimpleObjectProperty<Response<UserResponse>> changePasswordResponse = new SimpleObjectProperty<>();
        changePasswordResponse.addListener((observable, oldValue, newValue) -> {
            if (newValue.isSuccessful()) {
                UserResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()){
                    Platform.runLater(() -> {
                        currentPrimaryStage.setScene(loginScene);
                    });
                } else {
                    // TODO show error
                }
            } else {
                // TODO show error
            }
        });

        Button changePassword = (Button) changePasswordRoot.lookup("#button");
        JFXTextField newPasswordEditText = (JFXTextField) changePasswordRoot.lookup("#new_password_edit_text");
        JFXTextField confirmPasswordEditText = (JFXTextField) changePasswordRoot.lookup("#confirm_password_edit_text");
        changePassword.setOnAction(event -> {
            if (newPasswordEditText.getText().isEmpty() && confirmPasswordEditText.getText().isEmpty()) {
                // TODO show error
            } else if (newPasswordEditText.getText().isEmpty()) {
                // TODO show error
            } else if (confirmPasswordEditText.getText().isEmpty()) {
                // TODO show error
            } else if (newPasswordEditText.getText().length() < 8) {
                // TODO show error
            } else if (!newPasswordEditText.getText().equals(confirmPasswordEditText.getText())) {
                // TODO show error
            } else {
                User user = Objects.requireNonNull(findUserResponse.getValue().body()).getResult().get(0);
                Task onChangPassword = new Task() {
                    @Override
                    protected Object call() throws Exception {
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

//
//    SimpleObjectProperty<Response<com.example.pharmacyappd.model.LoginResponse>> loginResponse = new SimpleObjectProperty<>();
//    Task task = new Task() {
//        @Override
//        protected Object call() throws Exception {
//            System.out.println("login called");
//            Response<com.example.pharmacyappd.model.LoginResponse> response = repository.login("568", "123456789");
//            System.out.println("login finished");
//            loginResponse.set(response);
//            System.out.println("login response has been set");
//            return null;
//        }
//    };
//
//        new Thread(task).start();

//   loginResponse.addListener((observable, oldValue, newValue) -> {
//           if (newValue!=null){
//           System.out.println(newValue.isSuccessful());
//           }
//           });
