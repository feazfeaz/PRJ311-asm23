package sample.xml;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class AddContact implements Initializable {

    public Button btn_add, btn_close;
    public TextField
            txtField_first_name, txtField_last_name, txtField_phone, txtField_gmail;
    public Text txt_alert;
    public DatePicker datepicker_birth_date;
    public ChoiceBox choiceBox_groupname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txt_alert.setText("");

        txtField_phone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtField_phone.setText(newValue.replaceAll("[^\\d]", ""));
                    return;
                }
            }
        });

        datepicker_birth_date.setEditable(false);

        //default field value
        ArrayList<String> groupNameContact = new ArrayList<String>();
        groupNameContact.addAll(Main.groupName);
        groupNameContact.remove(0);
        choiceBox_groupname.getItems().clear();
        choiceBox_groupname.getItems().addAll(groupNameContact);
        choiceBox_groupname.setValue(groupNameContact.isEmpty() ? "Empty" : groupNameContact.get(0));

        datepicker_birth_date.setValue(LocalDate.now());

        //event
        btn_add.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleAdd(event));
        btn_close.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleClose(event));
    }

    public void handleAdd(MouseEvent event) {

        if (txtField_first_name.getText().isEmpty()
                || txtField_last_name.getText().isEmpty()
                || txtField_phone.getText().isEmpty()
                || txtField_gmail.getText().isEmpty()
                || (datepicker_birth_date.getValue()+"").isEmpty() ) {
            txt_alert.setText("Please, fill all of infomation!");
            txt_alert.setFill(Color.RED);
            return;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(datepicker_birth_date.getValue().getYear()
                , datepicker_birth_date.getValue().getMonth().getValue() - 1
                , datepicker_birth_date.getValue().getDayOfMonth()
        );

        Main.myUsers.add(new MyUser(
                        txtField_first_name.getText()
                        , txtField_last_name.getText()
                        , txtField_phone.getText()
                        , txtField_gmail.getText()
                        , calendar.getTime()
                        , choiceBox_groupname.getSelectionModel().getSelectedItem() + ""
                )
        );
        txtField_first_name.setText("");
        txtField_last_name.setText("");
        txtField_phone.setText("");
        txtField_gmail.setText("");
        datepicker_birth_date.setValue(LocalDate.now());
        txt_alert.setText("Contact add success!");
        txt_alert.setFill(Color.GREEN);
    }

    public void handleClose(MouseEvent event) {
        ((Stage) btn_close.getScene().getWindow()).close();
    }

    public static class MyUser {
        String first_name, last_name, phone, gmail;
        Date birth_date;
        String group_name;

        public MyUser(String first_name, String last_name, String phone, String gmail, Date datepicker_birth_date, String group_name) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.phone = phone;
            this.gmail = gmail;
            this.birth_date = datepicker_birth_date;
            this.group_name = group_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getGmail() {
            return gmail;
        }

        public void setGmail(String gmail) {
            this.gmail = gmail;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBirth_date() {
            return Main.dateFormat.format(birth_date);
        }
        public Date getBirth_dateDate() {
            return birth_date;
        }

        public void setBirth_date(Date birth_date) {
            this.birth_date = birth_date;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }
    }
}
