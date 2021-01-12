package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.xml.AddContact;
import sample.xml.Popup;
import sample.xml.UpdateContact;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static ArrayList<AddContact.MyUser> myUsersFilter = new ArrayList<AddContact.MyUser>();
    public static ArrayList<AddContact.MyUser> myUsersFilterSearchtext = new ArrayList<AddContact.MyUser>();
    public TableView tb_main_content;
    public ChoiceBox sample_groupNames_choiceBox;
    public TextField sample_search_txtfield;
    public Button
            sample_remove_btn, sample_update_btn, sample_add_btn, sample_manage_group;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sample_remove_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleRemove(event));
        sample_add_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                handleAdd(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sample_manage_group.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                sample_manage_group_handle(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sample_update_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                handleUpdateContact(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        choiceBoxFunc();
        sample_search_txtfieldFunc();

        TableColumn<AddContact.MyUser, String> first_name = new TableColumn<>("First Name");
        first_name.setMinWidth(200);
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<AddContact.MyUser, String> last_name = new TableColumn<>("Last Name");
        last_name.setMinWidth(200);
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<AddContact.MyUser, String> phone = new TableColumn<>("Phone");
        phone.setMinWidth(200);
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<AddContact.MyUser, String> gmail = new TableColumn<>("Gmail");
        gmail.setMinWidth(200);
        gmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));

        TableColumn<AddContact.MyUser, String> birth_date = new TableColumn<>("Birth Date");
        birth_date.setMinWidth(200);
        birth_date.setCellValueFactory(new PropertyValueFactory<>("birth_date"));

        TableColumn<AddContact.MyUser, String> group_name = new TableColumn<>("Group");
        group_name.setMinWidth(200);
        group_name.setCellValueFactory(new PropertyValueFactory<>("group_name"));

        myUsersFilter.clear();
        myUsersFilter.addAll(Main.myUsers);
        tb_main_content.getItems().addAll(myUsersFilter);
        tb_main_content.getColumns().addAll(
                first_name
                , last_name
                , phone
                , gmail
                , birth_date
                , group_name);

    }



    private void sample_manage_group_handle(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/sample/xml/xmlGroupManager.fxml"))));
        stage.showAndWait();
        choiceBoxFunc();
        Main.saver();
    }

    public void handleRemove(MouseEvent event) {
        if(tb_main_content.getSelectionModel().getSelectedItem() == null) return;
        Popup popup = new Popup("Xác nhận xóa contact!", "Xóa", "Hủy");
        popup.getPopup().setTitle("Xóa contact");
        popup.getConfim().setOnAction(event1 -> {
            popup.getPopup().close();
            Main.myUsers.remove(Main.myUsers.indexOf(tb_main_content.getSelectionModel().getSelectedItem()));
            myUsersFilter.clear();
            myUsersFilter.addAll(Main.myUsers);
            choiceBoxFunc();
        });
        popup.getClose().setOnAction(event1 -> popup.getPopup().close());
        popup.getPopup().showAndWait();
        Main.saver();
    }

    private void handleAdd(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("../sample/xml/AddContact.fxml"));
        stage.setScene(new Scene(root));
        stage.showAndWait();
//        setItem(Main.myUsers);
        choiceBoxFunc();
        Main.saver();
    }

    private void handleUpdateContact(MouseEvent event) throws IOException {
        if(tb_main_content.getSelectionModel().getSelectedItem() == null) return;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/xml/UpdateContact.fxml"));
        Parent root = fxmlLoader.load();
        UpdateContact updateContact = fxmlLoader.getController();
        updateContact.setDefaultValue((AddContact.MyUser) tb_main_content.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
        choiceBoxFunc();
        Main.saver();
    }

    public void choiceBoxFunc() {
        sample_groupNames_choiceBox.getItems().clear();
        if(!Main.groupName.isEmpty()){
            sample_groupNames_choiceBox.getItems().addAll(Main.groupName);
            sample_groupNames_choiceBox.setValue(sample_groupNames_choiceBox.getItems().isEmpty() ? "All" : sample_groupNames_choiceBox.getItems().get(0));
        }

        sample_groupNames_choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                myUsersFilter.clear();
                tb_main_content.getItems().clear();
                if ((newValue + "").equalsIgnoreCase("All")) {
                    myUsersFilter.addAll(Main.myUsers);
                } else {
                    for (int i = 0; i < Main.myUsers.size(); i++) {
                        if (Main.myUsers.get(i).getGroup_name().equalsIgnoreCase((String) newValue)) {
                            myUsersFilter.add(Main.myUsers.get(i));
                        }
                    }
                }
                tb_main_content.getItems().addAll(myUsersFilter);
                return;
            }
        });
    }

    private void sample_search_txtfieldFunc() {
        sample_search_txtfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                myUsersFilterSearchtext.clear();

                if (newValue.equals("")) {
                    myUsersFilterSearchtext.addAll(myUsersFilter);
                } else {
                    newValue = newValue.toLowerCase().trim();
                    for (AddContact.MyUser myUser : myUsersFilter) {
                        if (myUser.getFirst_name().toLowerCase().contains(newValue)
                                || myUser.getLast_name().toLowerCase().contains(newValue)
                                || myUser.getPhone().toLowerCase().contains(newValue)
                                || myUser.getGmail().toLowerCase().contains(newValue)
                                || myUser.getBirth_date().toLowerCase().contains(newValue)) {
                            myUsersFilterSearchtext.add(myUser);
                        }
                    }
                }
                tb_main_content.getItems().clear();
                tb_main_content.getItems().addAll(myUsersFilterSearchtext);
            }
        });
    }
    //note importance - ObservableList
    public ObservableList<AddContact.MyUser> getItems() {
        ObservableList<AddContact.MyUser> list = FXCollections.observableArrayList();
        list.addAll(Main.myUsers);
        return list;
    }

    public void setItem(ArrayList<AddContact.MyUser> myUsers) {
        tb_main_content.getItems().clear();
        tb_main_content.getItems().addAll(myUsers);
    }

}
