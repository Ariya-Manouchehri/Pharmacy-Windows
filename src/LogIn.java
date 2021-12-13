import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LogIn extends Application {
    Stage CurrentPrimaryStage;
    Scene sceneLogin;
    Scene scenePassword1;
    Scene scenePassword2;
    Scene scenePassword3;
    int countscen1 = 0;
    int countscen2 = 0;
    int countscen3 = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CurrentPrimaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Parent scenePassword1root = FXMLLoader.load(getClass().getResource("forgotpassword1.fxml"));
        Parent scenePassword2root = FXMLLoader.load(getClass().getResource("forgotpassword2.fxml"));
        Parent scenePassword3root = FXMLLoader.load(getClass().getResource("forgotpassword3.fxml"));
        sceneLogin = new Scene(root, 550, 550);
        scenePassword1 = new Scene(scenePassword1root, 550, 550);
        scenePassword2 = new Scene(scenePassword2root, 550, 550);
        scenePassword3 = new Scene(scenePassword3root, 550, 550);


        CurrentPrimaryStage.setTitle("Pharmacy");
        CurrentPrimaryStage.setScene(sceneLogin);
        CurrentPrimaryStage.setResizable(false);
        CurrentPrimaryStage.show();

        Label ForgotPassword = (Label) root.lookup("#forgot_password");
        ImageView email = (ImageView) scenePassword1root.lookup("#email_icon");
        ForgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                countscen1 += 1;
                CurrentPrimaryStage.setScene(scenePassword1);
                if (countscen1 == 1) {
                    animation(email);
                }
            }
        });

        Button cancelButton = (Button) scenePassword1root.lookup("#cancel");
        Button nextButton = (Button) scenePassword1root.lookup("#next");
        ImageView enterCode = (ImageView) scenePassword2root.lookup("#entercode_icon");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CurrentPrimaryStage.setScene(sceneLogin);
            }
        });
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                countscen2 += 1;
                CurrentPrimaryStage.setScene(scenePassword2);
                if (countscen2 == 1) {
                    animation(enterCode);
                }
            }
        });

        Button cancelButton2 = (Button) scenePassword2root.lookup("#cancel");
        Button nextButton2 = (Button) scenePassword2root.lookup("#next");
        ImageView rePassword = (ImageView) scenePassword3root.lookup("#repassword");

        cancelButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CurrentPrimaryStage.setScene(scenePassword1);
            }
        });
        nextButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CurrentPrimaryStage.setScene(scenePassword3);
                animation(rePassword);
            }
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
