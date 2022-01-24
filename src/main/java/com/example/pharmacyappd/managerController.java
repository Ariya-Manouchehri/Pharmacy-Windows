package com.example.pharmacyappd;

import com.example.pharmacyappd.model.*;
import com.example.pharmacyappd.repository.Repository;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class managerController implements Initializable {
    Stage stage_activity;
    Parent add_drug_Root;
    Parent remove_drug_Root;
    Parent remove_order_Root;
    Parent add_order_Root;

    Scene add_drug_scene;
    Scene remove_drug_scene;
    Scene remove_order_scene;
    Scene add_order_scene;

    @FXML
    Label total_label;
    @FXML
    JFXButton inc_drug_button;
    @FXML
    JFXButton dec_drug_button;
    @FXML
    TextField drug_inv;
    @FXML
    JFXComboBox<String> drug_company;
    @FXML
    JFXComboBox<String> drug_requires_doctor;
    @FXML
    JFXComboBox<String> drug_category;
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
    ComboBox<String> drug_name;
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
    @FXML
    JFXButton add_drug;
    @FXML
    JFXButton remove_drug;
    @FXML
    TextField name;
    @FXML
    TextField sex;
    @FXML
    TextField birthday;
    @FXML
    TextField degree;
    @FXML
    TextField lastname;
    @FXML
    TextField nationcode;
    @FXML
    TextField job_side;


    Repository repository = Repository.Companion.getInstance();

    SimpleObjectProperty<Response<MedsAllResponse>> medsResponse = new SimpleObjectProperty<>();
    SimpleObjectProperty<Response<OrdersResponse>> ordersResponse = new SimpleObjectProperty<>();
    SimpleObjectProperty<Response<CategoryAllResponse>> categoryResponse = new SimpleObjectProperty<>();
    SimpleObjectProperty<Response<PharmsResponse>> pharmsResponse = new SimpleObjectProperty<>();
    SimpleObjectProperty<Response<CompaniesResponse>> companyResponse = new SimpleObjectProperty<>();
    SimpleObjectProperty<Response<EmployeeProfileResponse>> profileResponse = new SimpleObjectProperty<>();

    ObservableList<String> categories;
    ObservableList<String> companies;
    ObservableList<String> needDoctorList = FXCollections.observableList(List.of("Yes", "NO"));
    ObservableList<String> pharms;

    int currentDrugIndex = 0;

    public void button(ActionEvent event) {
        if (event.getSource() == profile) {
            label.setText("Profile");
            label.setStyle("-fx-background-color: #d78f00");
            background_anchorpane.setVisible(true);
            order_boarder.setVisible(false);
            drug_boardear.setVisible(false);
            admin_background.setVisible(false);
            loadProfile();
            welcome.setVisible(false);
        } else if (event.getSource() == orders) {
            label.setText("Orders");
            label.setStyle("-fx-background-color: #092ea2");
            order_boarder.setVisible(true);
            drug_boardear.setVisible(false);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);
            loadOrders();
            loadAddOrder();
            loadRemoveOrder();
            welcome.setVisible(false);
        } else if (event.getSource() == drugs) {
            label.setText("Drugs");
            label.setStyle("-fx-background-color: #b60000");
            drug_boardear.setVisible(true);
            admin_background.setVisible(false);
            background_anchorpane.setVisible(false);
            order_boarder.setVisible(false);
            loadDrugs();
            loadAddDrug();
            loadRemoveDrug();
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
        drug_expiration_date.setEditable(set);
        drug_price.setEditable(set);
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
        Pharm pharm;
        for (int i = 0; i < pharms.size(); i++) {
            if (pharms.get(i).matches(drug_name.getValue().toString())) {
                pharm = Objects.requireNonNull(pharmsResponse.getValue().body()).getResult().get(i);
                break;
            }
        }
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

    private void loadAddOrder() {
        try {
            add_order_Root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/add_drug.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add_order_scene = new Scene(add_order_Root, 600, 480);
        stage_activity = new Stage();
        add_drug.setOnAction(event -> {
            stage_activity.setScene(add_order_scene);
            stage_activity.show();
        });
    }

    private void loadRemoveOrder() {
        try {
            remove_order_Root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/add_drug.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        remove_order_scene = new Scene(remove_order_Root, 600, 280);
        stage_activity = new Stage();
        remove_drug.setOnAction(event -> {
            stage_activity.setScene(remove_order_scene);
            stage_activity.show();
        });
    }

    private void loadAddDrug() {
        try {
            add_drug_Root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/add_drug.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add_drug_scene = new Scene(add_drug_Root, 600, 280);
        stage_activity = new Stage();
        add_drug.setOnAction(event -> {
            stage_activity.setScene(add_drug_scene);
            stage_activity.show();
            ComboBox new_drug_requires = (ComboBox) add_drug_Root.lookup("#new_drug_requires");
            ComboBox new_drug_name = (ComboBox) add_drug_Root.lookup("#new_drug_name");
            TextField new_drug_Expiration_date = (TextField) add_drug_Root.lookup("#new_drug_Expiration_date");
            ComboBox new_drug_name_of_company = (ComboBox) add_drug_Root.lookup("#new_drug_name_of_company");
            ComboBox new_drug_category = (ComboBox) add_drug_Root.lookup("#new_drug_category");
            TextField new_drug_Price = (TextField) add_drug_Root.lookup("#new_drug_Price");
            TextField new_drug_inv = (TextField) add_drug_Root.lookup("#new_drug_inv");
            Button save_add_drug = (Button) add_drug_Root.lookup("#save_add_drug");
            Button cancel_add_drug = (Button) add_drug_Root.lookup("#Cancel_add_drug");
            JFXButton new_drug_inc = (JFXButton) add_drug_Root.lookup("#new_drug_inc");

            SimpleStringProperty inventory = new SimpleStringProperty("1");
            new_drug_category.setItems(categories);
            new_drug_name_of_company.setItems(companies);
            new_drug_requires.setItems(needDoctorList);
            new_drug_name.setItems(pharms);
            new_drug_inv.textProperty().bindBidirectional(inventory);

            new_drug_inc.setOnAction(event1 -> {
                int inv = 0;
                try {
                  inv = Integer.parseInt(inventory.getValue());
                } catch (NumberFormatException ignored){}
                inv++;
                inventory.setValue("" + inv);
            });

            save_add_drug.setOnAction(event1 -> {
                int categoryInd = findIndex(categories, new_drug_category.getValue().toString());
                int companyInd = findIndex(companies, new_drug_name_of_company.getValue().toString());
                int doctorInd = findIndex(needDoctorList, new_drug_requires.getValue().toString());
                int pharmNameInd = findIndex(pharms, new_drug_name.getValue().toString());

                int cat_id = Objects.requireNonNull(categoryResponse.getValue().body()).getResult().get(categoryInd).getCategory().getId();
                int comp_id = Objects.requireNonNull(companyResponse.getValue().body()).getResult().get(companyInd).getId();
                boolean needDoctor = doctorInd == 0;
                int pharm_id = Objects.requireNonNull(pharmsResponse.getValue().body()).getResult().get(pharmNameInd).getId();
                String exp_date = new_drug_Expiration_date.getText();
                int price = -1;
                int inv = -1;

                try {
                    inv = Integer.parseInt(inventory.getValue());
                } catch (NumberFormatException ignored){}
                try {
                    price = Integer.parseInt(new_drug_Price.getText());
                } catch (NumberFormatException e){}
                if (price >= 0 && inv >= 0){
                    SimpleObjectProperty<Response<NewMedResponse>> newMedResponse = new SimpleObjectProperty<>();
                    int finalPrice = price;
                    int finalInv = inv;
                    Task addNewMed = new Task() {
                        @Override
                        protected Object call() {
                            newMedResponse.set(repository.addNewMed(pharm_id, comp_id, exp_date, finalPrice, "a", finalInv));
                            System.out.println("exited repository");
                            return null;
                        }
                    };
                    Thread addNewMedThread = new Thread(addNewMed);
                    addNewMedThread.start();

                    newMedResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                        if (newValue.isSuccessful()){
                            NewMedResponse response = newValue.body();
                            assert response != null;
                            if (response.getStatus()){
                                loadDrugs();
                                stage_activity.close();
                            } else {
                                System.out.println(response.getMessage());
                            }
                        } else {
                            System.out.println(newValue.errorBody());
                        }
                    }));

                }
            });

            cancel_add_drug.setOnAction(event1 -> stage_activity.close());
        });
    }

    private int findIndex(List<String> list, String item){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).matches(item)) return i;
        }
        return -1;
    }

    private void loadRemoveDrug() {
        try {
            remove_drug_Root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/remove_drug.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        remove_drug_scene = new Scene(remove_drug_Root, 600, 280);
        stage_activity = new Stage();
        remove_drug.setOnAction(event -> {
            stage_activity.setScene(remove_drug_scene);
            stage_activity.show();
        });
    }

    private void loadOrders() {
        Task getOrders = new Task() {
            @Override
            protected Object call() {
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
                            nodes[i].setOnMouseClicked(event -> {
                                Parent details = null;
                                try {
                                    details = FXMLLoader.load(ClassLoader.getSystemResource("fxml/show_ditails_of_order.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                assert details != null;
                                Scene detailsScene = new Scene(details);
                                Stage detailsStage = new Stage();
                                detailsStage.setScene(detailsScene);
                                detailsStage.show();
                                TextField detailsFirstName = (TextField) details.lookup("#details_order_name");
                                ListView listView = (ListView) details.lookup("#details_order_listview_of_drug");
                                SimpleObjectProperty<Response<OrderContentResponse>> orderContentResponse = new SimpleObjectProperty<>();
                                Task getOrderContent = new Task() {
                                    @Override
                                    protected Object call() throws Exception {
                                        orderContentResponse.set(repository.getOrderContent(order.getOrder().getId()));
                                        return null;
                                    }
                                };
                                Thread getOrderContentThread = new Thread(getOrderContent);
                                getOrderContentThread.start();

                                orderContentResponse.addListener((observable1, oldValue1, newValue1) -> {
                                    Platform.runLater(() -> {
                                        if (newValue1.isSuccessful()){
                                            OrderContentResponse response1 = newValue1.body();
                                            assert response1 != null;
                                            if (response1.getStatus()){
                                                List<OrderContent> result = response1.getResult();
                                                Node[] node = new Node[result.size()];
                                                for (int i1 = 0; i1 < result.size(); i1++) {
                                                    try {
                                                        node[i1] = FXMLLoader.load(ClassLoader.getSystemResource("fxml/item.fxml"));
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            } else {

                                            }
                                        } else {

                                        }
                                    });
                                });

                            });
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

    private void loadProfile() {
        Task getProfile = new Task() {
            @Override
            protected Object call() throws Exception {
                profileResponse.set(repository.getProfile());
                return null;
            }
        };
        Thread getProfileThread = new Thread(getProfile);
        getProfileThread.start();

        profileResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                EmployeeProfileResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    List<EmployeeProfile> users = response.getResult();
                    EmployeeProfile user = users.get(0);

                    name.setText(user.getReferred().getFirst_name());
                    sex.setText(user.getReferred().getGender());
                    birthday.setText(user.getReferred().getBirthday());
                    degree.setText(user.getReferred().getDegree());
                    lastname.setText(user.getReferred().getLast_name());
                    nationcode.setText(user.getReferred().getNat_num());
                    phone_number.setText(user.getReferred().getPhone());
                    job_side.setText(user.getReferred().getJob());
                    address.setText(user.getReferred().getAddress());
                } else {

                }
            } else {

            }
        }));
    }

    private void loadDrugs() {
        loadCompany();
        loadCategory();
        loadPharms();
        drug_requires_doctor.setItems(needDoctorList);
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
                            try {
                                medItemImage.setImage(new Image(medInfo.getImage()));
                            } catch (Exception e){
                                e.printStackTrace();
                            };
                            medItemPane.setOnMouseClicked(event -> {
                                drug_name.setValue(medInfo.getPharm().getName());
                                drug_expiration_date.setText(medInfo.getMed().getExp_date().substring(0, 9));
                                drug_company.setValue(medInfo.getCompany().getName());
                                drug_category.setValue(medInfo.getCategory().getName());
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

    private void loadPharms() {
        Task getPharms = new Task() {
            @Override
            protected Object call() throws Exception {
                pharmsResponse.set(repository.getPharms());
                return null;
            }
        };
        Thread getPharmsThread = new Thread(getPharms);
        getPharmsThread.start();
        pharmsResponse.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.isSuccessful()) {
                    PharmsResponse response = newValue.body();
                    assert response != null;
                    if (response.getStatus()) {
                        List<String> list = response.getResult().stream()
                                .map(Pharm::getName)
                                .collect(Collectors.toList());
                        pharms = FXCollections.observableList(list);
                        drug_name.setItems(pharms);
                    } else {

                    }
                } else {

                }
            });
        });
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

        categoryResponse.addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.isSuccessful()) {
                CategoryAllResponse response = newValue.body();
                assert response != null;
                if (response.getStatus()) {
                    List<String> list = response.getResult().stream()
                            .map(categoryAndImage -> categoryAndImage.getCategory().getName())
                            .collect(Collectors.toList());
                    categories = FXCollections.observableList(list);
                    drug_category.setItems(categories);
                } else {

                }
            } else {

            }
        }));
    }

    private void loadCompany() {
        Task getCompany = new Task() {
            @Override
            protected Object call() throws Exception {
                companyResponse.set(repository.getCompanies());
                return null;
            }
        };
        Thread getCompanyThread = new Thread(getCompany);
        getCompanyThread.start();

        companyResponse.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.isSuccessful()) {
                    CompaniesResponse response = newValue.body();
                    assert response != null;
                    if (response.getStatus()) {
                        List<String> list = response.getResult().stream()
                                .map(company -> company.getName())
                                .collect(Collectors.toList());
                        companies = FXCollections.observableList(list);
                        drug_company.setItems(companies);
                    } else {

                    }
                } else {

                }
            });
        });
    }
}