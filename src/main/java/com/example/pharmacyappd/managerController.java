package com.example.pharmacyappd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class managerController implements Initializable {

    @FXML
    ListView<Node> list_of_persons;

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
    ListView<Node> list_of_drug = null;

    @FXML
    AnchorPane drug_boarder;

    public void button(ActionEvent event) {
        if (event.getSource() == profile) {
            label.setText("Profile");
            label.setStyle("-fx-background-color: #d78f00");
        } else if (event.getSource() == orders) {
            label.setText("Orders");
            label.setStyle("-fx-background-color: #092ea2");

        } else if (event.getSource() == drugs) {
            label.setText("Drugs");
            label.setStyle("-fx-background-color: #b60000");

        } else if (event.getSource() == users) {
            label.setText("Users");
            label.setStyle("-fx-background-color: #0e6704");
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
        final Node[] Node = new Node[10];
        for (int i = 0; i < Node.length; i++) {
            try {
                final int j = i;
                Node[i] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/item.fxml"));
                Node[i].setOnMouseEntered(event -> Node[j].setStyle("-fx-background-color: #ffffff"));
                Node[i].setOnMouseExited(event -> Node[j].setStyle("-fx-background-color: #fa4e4e"));
                Node[i].setOnMousePressed(event -> Node[j].setStyle("-fx-background-color: #ffffff"));
                list_of_drug.getItems().add(Node[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final Node[] persons = new Node[13];
        for (int i = 0; i < persons.length; i += 3) {
            HBox box = new HBox();
            if (persons.length - i == 1) {
                persons[i] = new Button("Person" + i);
                box.getChildren().addAll(persons[i]);
            } else if (persons.length - i == 2) {
                persons[i] = new Button("Person" + i);
                persons[i + 1] = new Button("Person" + i + 1);
                box.getChildren().addAll(persons[i], persons[i + 1]);
            } else if (persons.length - i >= 3) {
                persons[i] = new Button("Person" + i);
                persons[i + 1] = new Button("Person" + i + 1);
                persons[i + 2] = new Button("Person" + i + 2);
                box.getChildren().addAll(persons[i], persons[i + 1], persons[i + 2]);
            }
            list_of_persons.getItems().add(box);
        }
    }
}
