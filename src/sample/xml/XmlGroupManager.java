package sample.xml;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class XmlGroupManager implements Initializable {

    public ListView lstvNameGroup;

    public TextField txtField_replace;
    public TextField txtField_replaceto;
    public TextField txtField_remove;
    public TextField txtfield_find;
    public TextField txtfield_add;


    public Button btnFind, btnAdd, btnRemove, btnRename;

    public Text txtAlert;

    ArrayList<String> names = new ArrayList<String>() {{
        if(!Main.groupName.isEmpty()){
            addAll(Main.groupName);
            remove(0);
        }
    }};

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstvNameGroup.getItems().addAll(names);
        lstvNameGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                String nameChoosed = lstvNameGroup.getSelectionModel().getSelectedItem() + "";
                txtField_replace.setText(nameChoosed);
                txtField_remove.setText(nameChoosed);
            }
        });
        btnFind.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleFind(event);
            }
        });
        btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleAdd(event);
            }
        });
        btnRename.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            handleRename(event);
        });
        btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleRemove(event));
    }

    private void handleFind(MouseEvent event) {
        int index = lstvNameGroup.getItems().indexOf(txtfield_find.getText());
        String result = (index == -1) ? "Not Found!" : lstvNameGroup.getItems().get(index) + "";
        txtField_replace.setText(result);
        txtField_remove.setText(result);
    }

    public void handleAdd(MouseEvent event) {
        String newGroup = txtfield_add.getText();
        //unique name
        if (names.indexOf(newGroup) == -1) {
            Main.groupName.add(newGroup);
            names.add(newGroup);
            lstvNameGroup.getItems().add(newGroup);

            txtAlert.setText("* Add new contact success!");
            txtAlert.setFill(Color.GREEN);
        } else {
            txtAlert.setText("* Name exists!");
            txtAlert.setFill(Color.RED);
        }
    }

    public void handleRename(MouseEvent event) {
        if (txtField_replace.getText().equals("") || txtField_replace.getText().equals("Not Found!")) {
            txtAlert.setText("* Please choose another name, which name will be rename!");
            txtAlert.setFill(Color.RED);
            return;
        } else if (txtField_replaceto.getText().equals("")) {
            txtAlert.setText("* Dont let empty new name field!");
            txtAlert.setFill(Color.RED);
            return;
        }

        Main.groupName.set(Main.groupName.indexOf(txtField_replace.getText()), txtField_replaceto.getText());
        names.clear();
        names.addAll(Main.groupName);
        names.remove(0);
        lstvNameGroup.getItems().clear();
        lstvNameGroup.getItems().addAll(names);

        for (AddContact.MyUser user : Main.myUsers){
            if(user.getGroup_name().equalsIgnoreCase(txtField_replace.getText()))
                user.setGroup_name(txtField_replaceto.getText());
        }

        txtField_replace.setText("");
        txtField_replaceto.setText("");
        txtField_remove.setText("");



        txtAlert.setText("* Rename Success!");
        txtAlert.setFill(Color.GREEN);
    }

    public void handleRemove(MouseEvent event) {
        if (txtField_remove.getText().equals("") || txtField_remove.getText().equals("Not Found!")) {
            txtAlert.setText("* Please choose name to remove!");
            txtAlert.setFill(Color.RED);
            return;
        }
        Popup popup = new Popup("Xác nhận xóa nhóm và những contact thuộc nhóm!", "A.Có", "B.Không");
        popup.getPopup().setTitle("Xác nhận!");

        popup.getConfim().setOnAction(event1 -> {

            Main.myUsers.removeIf(myUser -> myUser.getGroup_name().equalsIgnoreCase(txtField_remove.getText()));
            Main.groupName.remove(Main.groupName.indexOf(txtField_remove.getText()));

            popup.getPopup().close();

            //reset value
            names.clear();
            names.addAll(Main.groupName);
            names.remove(0);
            lstvNameGroup.getItems().clear();
            lstvNameGroup.getItems().addAll(names);

            txtField_replace.setText("");
            txtField_replaceto.setText("");
            txtField_remove.setText("");

            txtAlert.setText("* Remove Success!");
            txtAlert.setFill(Color.GREEN);
        });

        popup.getClose().setOnAction(event1 -> {
            popup.getPopup().close();
            Main.log("đóng rồi này");
        });
        popup.getPopup().showAndWait();
    }
}
