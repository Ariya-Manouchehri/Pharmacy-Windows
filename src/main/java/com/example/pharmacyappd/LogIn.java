package com.example.pharmacyappd;

import com.example.pharmacyappd.repository.Repository;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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


public class LogIn extends Application {
    Stage currentPrimaryStage;
    Scene loginScene;
    Scene forgotPasswordScene;
    Scene codeVerificationScene;
    Scene changePasswordScene;
    Scene managerScene;
    boolean countScene1 = true;
    boolean countScene2 = true;
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
        managerScene = new Scene(managerRoot, 1050, 630);

        currentPrimaryStage.setTitle("Pharmacy");
        currentPrimaryStage.getIcons().add(new Image("res/pills.png"));
        currentPrimaryStage.setScene(loginScene);
        currentPrimaryStage.setResizable(false);
        ImageView drugIconLogin = (ImageView) loginRoot.lookup("#drug_icon");
        animation(drugIconLogin);
        currentPrimaryStage.show();

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
        login.setOnAction(event -> {
            currentPrimaryStage.setResizable(true);
            Image path = new Image("res/icons8-male-user-100.png", false);
            circleImage.setFill(new ImagePattern(path));
            currentPrimaryStage.setScene(managerScene);
            currentPrimaryStage.setX(250);
            currentPrimaryStage.setY(100);
        });

        Button cancelButtonForgotPassword = (Button) forgotPasswordRoot.lookup("#cancel");
        Button nextButtonForgotPassword = (Button) forgotPasswordRoot.lookup("#next");
        ImageView drugIconCodeVerification = (ImageView) codeVerificationRoot.lookup("#drug_icon");
        cancelButtonForgotPassword.setOnAction(event -> currentPrimaryStage.setScene(loginScene));
        nextButtonForgotPassword.setOnAction(event -> {
            currentPrimaryStage.setScene(codeVerificationScene);
            if (countScene2) {
                animation(drugIconCodeVerification);
            }
            countScene2 = false;
        });

        Button cancelButtonCodeVerification = (Button) codeVerificationRoot.lookup("#cancel");
        Button nextButtonCodeVerification = (Button) codeVerificationRoot.lookup("#next");
        ImageView drugIconChangePassword = (ImageView) changePasswordRoot.lookup("#drug_icon");
        cancelButtonCodeVerification.setOnAction(event -> currentPrimaryStage.setScene(forgotPasswordScene));
        nextButtonCodeVerification.setOnAction(event -> {
            currentPrimaryStage.setScene(changePasswordScene);
            animation(drugIconChangePassword);
        });

        Button changePassword = (Button) changePasswordRoot.lookup("#button");
        changePassword.setOnAction(event -> {
        });
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
//    SimpleObjectProperty<Response<LoginResponse>> loginResponse = new SimpleObjectProperty<>();
//    Task task = new Task() {
//        @Override
//        protected Object call() throws Exception {
//            System.out.println("login called");
//            Response<LoginResponse> response = repository.login("568", "123456789");
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
