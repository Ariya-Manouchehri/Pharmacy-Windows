package com.example.pharmacyappd;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    VBox boarderof_order;

    @FXML
    HBox admin_background;

    @FXML
    AnchorPane drug_boardear;

    @FXML
    AnchorPane background_anchorpane;

    @FXML
    ImageView gender;

    public managerController() throws IOException {
    }

    public void button(ActionEvent event) {
        if (event.getSource() == profile) {
            label.setText("Profile");
            label.setStyle("-fx-background-color: #d78f00");
            background_anchorpane.setVisible(true);
            boarderof_order.setVisible(false);
            drug_boardear.setVisible(false);
            admin_background.setVisible(false);
        } else if (event.getSource() == orders) {
            label.setText("Orders");
            label.setStyle("-fx-background-color: #092ea2");
            boarderof_order.setVisible(true);
            drug_boardear.setVisible(false);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);

        } else if (event.getSource() == drugs) {
            label.setText("Drugs");
            label.setStyle("-fx-background-color: #b60000");
            drug_boardear.setVisible(true);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);
            boarderof_order.setVisible(false);

        } else if (event.getSource() == users) {
            label.setText("Users");
            label.setStyle("-fx-background-color: #0e6704");
            admin_background.setVisible(true);
            background_anchorpane.setVisible(false);
            boarderof_order.setVisible(false);
            drug_boardear.setVisible(false);

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

        final Node[] drug = new Node[10];
//        for (int i = 0; i < drug.length; i++) {
//            try {
//                final int j = i;
//                drug[i] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/item.fxml"));
//                drug[i].setOnMouseEntered(event -> drug[j].setStyle("-fx-background-color: #ffffff"));
//                drug[i].setOnMouseExited(event -> drug[j].setStyle("-fx-background-color: #d32222"));
//                drug[i].setOnMousePressed(event -> drug[j].setStyle("-fx-background-color: #ffffff"));
//                list_of_drug.getItems().add(drug[i]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        Node[] order = new Node[10];
//        for (int i = 0; i < order.length; i++) {
//            try {
//                final int j = i;
//                if(i%2 == 0){
//                    gender.setImage(new Image("res/male.png"));
//                }else if(i%2 != 0){
//                    gender.setImage(new Image("res/female.png"));
//                }
//                order[i] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/orderitem.fxml"));
//                order[i].setOnMouseEntered(event -> order[j].setStyle("-fx-background-color: #ffffff"));
//                order[i].setOnMouseExited(event -> order[j].setStyle("-fx-background-color: #d32222"));
//                order[i].setOnMousePressed(event -> order[j].setStyle("-fx-background-color: #099cef"));
//                order[i].setOnMouseClicked(event -> order[j].setStyle("-fx-background-color: #6ee842"));
//                orders_list.getChildren().add(order[i]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        final Node[] persons = new Node[13];
//        for (int i = 0; i < persons.length; i += 3) {
//            HBox box = new HBox();
//            if (persons.length - i == 1) {
//                persons[i] = new Button("Person" + i);
//                box.getChildren().addAll(persons[i]);
//            } else if (persons.length - i == 2) {
//                persons[i] = new Button("Person" + i);
//                persons[i + 1] = new Button("Person" + i + 1);
//                box.getChildren().addAll(persons[i], persons[i + 1]);
//            } else if (persons.length - i >= 3) {
//                persons[i] = new Button("Person" + i);
//                persons[i + 1] = new Button("Person" + i + 1);
//                persons[i + 2] = new Button("Person" + i + 2);
//                box.getChildren().addAll(persons[i], persons[i + 1], persons[i + 2]);
//            }
//            list_of_persons.getItems().add(box);
//        }
//    }
    }
}