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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class UpdateContact implements Initializable {

    public Button btn_update, btn_close;
    public TextField
            txtField_first_name, txtField_last_name, txtField_phone, txtField_gmail;
    public Text txt_alert;
    public DatePicker datepicker_birth_date;
    public ChoiceBox choiceBox_groupname;

    public AddContact.MyUser selectedContact;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_update.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> handleUpdate(event));
        btn_close.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> handleClose(event));
    }

    private void handleUpdate(MouseEvent event) {
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

        selectedContact.setFirst_name(txtField_first_name.getText());
        selectedContact.setLast_name(txtField_last_name.getText());
        selectedContact.setPhone(txtField_phone.getText());
        selectedContact.setGmail(txtField_gmail.getText());
        selectedContact.setBirth_date(calendar.getTime());
        selectedContact.setGroup_name(choiceBox_groupname.getSelectionModel().getSelectedItem().toString());

        ((Stage)btn_update.getScene().getWindow()).close();

    }

    private void handleClose(MouseEvent event) {
        ((Stage)btn_update.getScene().getWindow()).close();
    }

    public void setDefaultValue(AddContact.MyUser selectedContact){
        this.selectedContact = selectedContact;

        txt_alert.setText("");

        txtField_first_name.setText(selectedContact.first_name);
        txtField_last_name.setText(selectedContact.last_name);
        txtField_phone.setText(selectedContact.phone);
        txtField_gmail.setText(selectedContact.gmail);
        datepicker_birth_date.setValue(selectedContact.getBirth_dateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

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
        choiceBox_groupname.setValue(selectedContact.getGroup_name());

        datepicker_birth_date.setValue(LocalDate.now());

    }

    public void setSelectedContact(AddContact.MyUser selectedContact) {
        this.selectedContact = selectedContact;
    }
}
