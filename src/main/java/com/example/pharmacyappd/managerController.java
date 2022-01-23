package com.example.pharmacyappd;

import com.example.pharmacyappd.model.*;
import com.example.pharmacyappd.repository.Repository;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class managerController implements Initializable {

    @FXML
    Label total_label;
    @FXML
    JFXButton inc_drug_button;
    @FXML
    JFXButton dec_drug_button;
    @FXML
    TextField drug_inv;
    @FXML
    TextField drug_company;
    @FXML
    ComboBox drug_requires_doctor;
    @FXML
    ComboBox drug_category;
    @FXML
    JFXTextArea drug_guide;
    @FXML
    JFXTextArea drug_usage;
    @FXML
    JFXTextArea drug_keeping;
    @FXML
    TextField drug_price;
    @FXML
    TextField drug_expiration_date;
    @FXML
    ComboBox drug_name;
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
    @FXML
    JFXButton cancel_edit_drug_information;
    @FXML
    JFXButton save_edit_drug_information;
    @FXML
    JFXButton delete_current_drug;

    Repository repository = Repository.Companion.getInstance();

    SimpleObjectProperty<Response<MedsAllResponse>> medsResponse = new SimpleObjectProperty<>();

    SimpleObjectProperty<Response<OrdersResponse>> ordersResponse = new SimpleObjectProperty<>();

    SimpleObjectProperty<Response<CategoryAllResponse>> categoryResponse = new SimpleObjectProperty<>();

    List<String> categories = new ArrayList<>();
    List<String> needDoctorList = List.of("Yes", "No");

    int currentDrugIndex = 0;

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
            loadOrders();
            welcome.setVisible(false);
        } else if (event.getSource() == drugs) {
            label.setText("Drugs");
            label.setStyle("-fx-background-color: #b60000");
            drug_boardear.setVisible(true);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);
            order_boarder.setVisible(false);
            loadDrugs();
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

    public void edit_information(Boolean set) {
        inc_drug_button.setVisible(set);
        dec_drug_button.setVisible(set);
        drug_inv.setVisible(set);
        delete_current_drug.setVisible(set);
        save_edit_drug_information.setVisible(set);
        cancel_edit_drug_information.setVisible(set);
        drug_name.setEditable(set);
        drug_expiration_date.setEditable(set);
        drug_price.setEditable(set);
        drug_company.setEditable(set);
        drug_requires_doctor.setEditable(set);
        drug_category.setEditable(set);
        drug_guide.setEditable(set);
        drug_usage.setEditable(set);
        drug_keeping.setEditable(set);
    }

    public void edit_drug_inforamtion() {
        edit_information(true);
    }

    public void save_edit_drug_information() {
        edit_information(false);
        MedAllInfo medInfo = Objects.requireNonNull(medsResponse.getValue().body()).getResult().get(currentDrugIndex);
        updateCompany(drug_company.getText());
//        drug_name.getText();
//        drug_price.setText(String.valueOf(medInfo.getMed().getPrice()));
//        drug_expiration_date.setText(medInfo.getMed().getExp_date());
//        drug_company.setText(medInfo.getCompany().getName());
//        drug_category.setText(medInfo.getCategory().getName());
//        drug_requires_doctor.setText(medInfo.getPharm().getNeed_dr() ? "Yes" : "No");
//        drug_guide.setText(medInfo.getPharm().getGuide());
//        drug_keeping.setText(medInfo.getPharm().getKeeping());
//        drug_usage.setText(medInfo.getPharm().getUsage());
//        total_label.setText("" + medInfo.getMed().getInv());
//        drug_inv.setText(String.valueOf(medInfo.getMed().getInv()));
    }

    private void updateCompany(String companyName) {

    }

    public void cancel_edit_drug_information() {
        edit_information(false);
    }

    public void remove_all_drug() {
        Alert remove_all_drug_alert = new Alert(Alert.AlertType.INFORMATION);
        remove_all_drug_alert.setX(600);
        remove_all_drug_alert.setY(400);
        remove_all_drug_alert.setTitle("Remove all drug");
        remove_all_drug_alert.setHeaderText("");
        remove_all_drug_alert.setContentText("Are you sure?\nAfter do that you have empty list of drug.");
        remove_all_drug_alert.getButtonTypes().clear();
        remove_all_drug_alert.getButtonTypes().add(ButtonType.YES);
        remove_all_drug_alert.getButtonTypes().add(ButtonType.NO);

        remove_all_drug_alert.showAndWait();
        if (remove_all_drug_alert.getResult() == ButtonType.YES) {
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    private void loadOrders() {
        Task getOrders = new Task() {
            @Override
            protected Object call() throws Exception {
                ordersResponse.set(repository.getOrders());
                return null;
            }
        };
        Thread getOrdersThread = new Thread(getOrders);
        getOrdersThread.start();

        ordersResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                OrdersResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    List<OrderAndPatient> orderAndPatients = response.getResult();
                    Node[] nodes = new Node[orderAndPatients.size()];
                    orders_list.getChildren().removeAll(orders_list.getChildren());
                    for (int i = 0; i < orderAndPatients.size(); i++) {
                        try {
                            nodes[i] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/orderItem.fxml"));
                            OrderAndPatient order = orderAndPatients.get(i);
                            Label nationalCode = (Label) nodes[i].lookup("#order_item_national_number");
                            Label firstname = (Label) nodes[i].lookup("#order_item_firstname");
                            Label lastname = (Label) nodes[i].lookup("#order_item_lastname");
                            nationalCode.setText(order.getPatient().getNat_num());
                            firstname.setText(order.getPatient().getFirst_name());
                            lastname.setText(order.getPatient().getLast_name());
                            if (order.getOrder().getPaid() && order.getOrder().getDelivered()) {
                                nodes[i].setStyle("-fx-background-color: #7a7a7a");
                            } else if (order.getOrder().getPaid() && !order.getOrder().getDelivered()) {
                                nodes[i].setStyle("-fx-background-color: #ff8c8c");
                            } else if (!order.getOrder().getPaid() && !order.getOrder().getDelivered()) {
                                nodes[i].setStyle("-fx-background-color: #a1ff9f");
                            }
                            orders_list.getChildren().add(nodes[i]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                }
            } else {

            }
        }));
    }

    private void loadDrugs() {
        loadCompany();
        loadCategory();
        drug_requires_doctor.setItems(FXCollections.observableList(needDoctorList));
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
                    List<MedAllInfo> result = response.getResult();
                    Node[] Node = new Node[result.size()];
                    list_of_drug.getItems().removeAll(list_of_drug.getItems());
                    List<String> meds = result.stream()
                            .map(medAllInfo -> medAllInfo.getPharm().getName())
                            .collect(Collectors.toList());
                    drug_name.setItems(FXCollections.observableList(meds));
                    for (int i = 0; i < result.size(); i++) {
                        try {
                            Node[i] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/item.fxml"));
                            MedAllInfo medInfo = result.get(i);
                            final int finalI = i;
                            Node[i].setOnMouseEntered(event -> Node[finalI].setStyle("-fx-background-color: #ffffff"));
                            Node[i].setOnMouseExited(event -> Node[finalI].setStyle("-fx-background-color: #d32222"));
                            Node[i].setOnMousePressed(event -> Node[finalI].setStyle("-fx-background-color: #ffffff"));
                            Label medItemName = (Label) Node[i].lookup("#med_item_name");
                            Label medItemPrice = (Label) Node[i].lookup("#med_item_price");
                            ImageView medItemImage = (ImageView) Node[i].lookup("#med_item_image");
                            Pane medItemPane = (Pane) Node[i].lookup("#med_item_pane");

                            medItemName.setText(medInfo.getPharm().getName());
                            medItemPrice.setText(String.valueOf(medInfo.getMed().getPrice()));
                            medItemImage.setImage(new Image(medInfo.getImage()));
                            medItemPane.setOnMouseClicked(event -> {
                                drug_name.setValue(meds.get(finalI));
                                drug_expiration_date.setText(medInfo.getMed().getExp_date().substring(0, 9));
                                drug_company.setText(medInfo.getCompany().getName());
                                drug_category.setValue(categories.get(finalI));
                                drug_requires_doctor.setValue(medInfo.getPharm().getNeed_dr() ? "Yes" : "No");
                                drug_guide.setText(medInfo.getPharm().getGuide());
                                drug_keeping.setText(medInfo.getPharm().getKeeping());
                                drug_usage.setText(medInfo.getPharm().getUsage());
                                total_label.setText("" + medInfo.getMed().getInv());
                                drug_inv.setText(String.valueOf(medInfo.getMed().getInv()));
                                inc_drug_button.setOnMouseClicked(event1 -> {
                                    //TODO add after edit button added
                                });
                                dec_drug_button.setOnMouseClicked(event1 -> {
                                    //TODO add after edit button added
                                });
                                drug_inv.setOnInputMethodTextChanged(event1 -> {
                                    //TODO add after edit button added
                                });

                            });
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
    }

    private void loadCategory() {
        Task getCategory = new Task() {
            @Override
            protected Object call() throws Exception {
                categoryResponse.set(repository.getCategories());
                return null;
            }
        };
        Thread getCategoryThread = new Thread(getCategory);
        getCategoryThread.start();

        categoryResponse.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.isSuccessful()) {
                    CategoryAllResponse response = newValue.body();
                    assert response != null;
                    if (response.getStatus()) {
                        categories = response.getResult().stream()
                                .map(categoryAndImage -> categoryAndImage.getCategory().getName())
                                .collect(Collectors.toList());
                        drug_category.setItems(FXCollections.observableList(categories));
                    } else {

                    }
                } else {

                }
            });
        });
    }

    private void loadCompany() {

    }
}