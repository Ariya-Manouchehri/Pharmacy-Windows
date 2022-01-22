package com.example.pharmacyappd;

import com.example.pharmacyappd.model.MedInfo;
import com.example.pharmacyappd.model.MedsAllResponse;
import com.example.pharmacyappd.repository.Repository;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import retrofit2.Response;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class managerController implements Initializable {

    @FXML
    ListView<Node> list_of_persons;

    @FXML
    JFXButton Cancel_drug;

    @FXML
    Button profile;

    @FXML
    Button orders;

    @FXML
    Button drugs;

    @FXML
    Button users;

    @FXML
    Label label;

    @FXML
    Circle circle_image;

    @FXML
    Button save;

    @FXML
    Button Cancel;

    @FXML
    TextField phone_number;

    @FXML
    TextArea address;

    @FXML
    TextField password;

    @FXML
    ListView<Node> list_of_drug;

    @FXML
    VBox orders_list = null;

    @FXML
    AnchorPane order_boarder;

    @FXML
    HBox admin_background;

    @FXML
    AnchorPane drug_boardear;

    @FXML
    AnchorPane background_anchorpane;

    @FXML
    AnchorPane welcome;
    @FXML
    ImageView gender;

    Repository repository = Repository.Companion.getInstance();

    SimpleObjectProperty<Response<MedsAllResponse>> medsResponse = new SimpleObjectProperty<>();

    public void button(ActionEvent event) {
        if (event.getSource() == profile) {
            label.setText("Profile");
            label.setStyle("-fx-background-color: #d78f00");
            background_anchorpane.setVisible(true);
            order_boarder.setVisible(false);
            drug_boardear.setVisible(false);
            admin_background.setVisible(false);
            welcome.setVisible(false);
        } else if (event.getSource() == orders) {
            label.setText("Orders");
            label.setStyle("-fx-background-color: #092ea2");
            order_boarder.setVisible(true);
            drug_boardear.setVisible(false);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);
welcome.setVisible(false);
        } else if (event.getSource() == drugs) {
            label.setText("Drugs");
            label.setStyle("-fx-background-color: #b60000");
            drug_boardear.setVisible(true);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);
            order_boarder.setVisible(false);
welcome.setVisible(false);
        } else if (event.getSource() == users) {
            label.setText("Users");
            label.setStyle("-fx-background-color: #0e6704");
            admin_background.setVisible(true);
            background_anchorpane.setVisible(false);
            order_boarder.setVisible(false);
            drug_boardear.setVisible(false);
welcome.setVisible(false);
        }
    }

    public void editable() {
        save.setVisible(true);
        Cancel.setVisible(true);
        phone_number.setStyle("-fx-background-color: #53d0b2");
        phone_number.setEditable(true);

        address.setStyle("-fx-control-inner-background: #53d0b2");
        address.setEditable(true);

        password.setStyle("-fx-background-color: #fa6f6f");
        password.setEditable(true);
    }

    public void save() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setX(600);
        alert.setY(400);
        alert.setTitle("Save changes");
        alert.setHeaderText("");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);

        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("intialize manager controller");
        Task getMeds = new Task() {
            @Override
            protected Object call() {
                medsResponse.set(repository.getAllMeds());
                return null;
            }
        };

        Thread getMedsThread = new Thread(getMeds);
        getMedsThread.start();

        medsResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                MedsAllResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    List<MedInfo> result = response.getResult();
                    Node[] Node = new Node[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        try {
                            Node[i] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/item.fxml"));
                            final int finalI = i;
                            Node[i].setOnMouseEntered(event -> Node[finalI].setStyle("-fx-background-color: #ffffff"));
                            Node[i].setOnMouseExited(event -> Node[finalI].setStyle("-fx-background-color: #d32222"));
                            Node[i].setOnMousePressed(event -> Node[finalI].setStyle("-fx-background-color: #ffffff"));
                            Label medItemName = (Label) Node[i].lookup("#med_item_name");
                            Label medItemPrice = (Label) Node[i].lookup("#med_item_price");
                            ImageView medItemImage = (ImageView) Node[i].lookup("#med_item_image");

                            medItemName.setText(result.get(i).getPharm().getName());
                            medItemPrice.setText(String.valueOf(result.get(i).getMed().getPrice()));
                            medItemImage.setImage(new Image(result.get(i).getImage()));
                            list_of_drug.getItems().add(Node[i]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println(response.getMessage());
                }
            } else {
                try {
                    assert newValue.errorBody() != null;
                    System.out.println(newValue.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
//
//        final Node[] persons = new Node[1000];
//        for (int i = 0; i < persons.length; i += 3) {
//            HBox box = new HBox();
//            if (persons.length - i == 1) {
//                persons[i] = new Button("Person" + i);
//                box.getChildren().addAll(persons[i]);
//            } else if (persons.length - i == 2) {
//                persons[i] = new Button("Person" + i);
//                persons[i + 1] = new Button("Person" + (i + 1));
//                box.getChildren().addAll(persons[i], persons[i + 1]);
//            } else if (persons.length - i >= 3) {
//                persons[i] = new Button("Person" + i);
//                persons[i + 1] = new Button("Person" + (i + 1));
//                persons[i + 2] = new Button("Person" + (i + 2));
//                box.getChildren().addAll(persons[i], persons[i + 1], persons[i + 2]);
//            }
//            list_of_persons.getItems().add(box);
//        }
    }
}