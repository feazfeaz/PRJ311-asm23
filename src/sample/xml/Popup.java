package sample.xml;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

    private Text alert;
    private Button confim, close;
    private Stage popup;

    //basic popup without xml
    public Popup(String alert, String confim, String close) {
        //initialize
        this.alert = new Text(alert);
        this.confim = new Button(confim);
        this.close = new Button(close);

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        //setting
        hBox.getChildren().addAll(this.confim, this.close);
        vBox.getChildren().addAll(this.alert, hBox);

        VBox.setMargin(this.alert, new Insets(15));
        HBox.setMargin(this.confim, new Insets(10, 20, 10, 15));
        HBox.setMargin(this.close, new Insets(10, 0, 10, 15));

        this.popup = new Stage();
        this.popup.initModality(Modality.APPLICATION_MODAL);

        Group group = new Group();
        group.getChildren().add(vBox);
        this.popup.setScene(new Scene(group));
    }

    public Button getClose() {
        return close;
    }

    public Button getConfim() {
        return confim;
    }

    public Stage getPopup() {
        return popup;
    }
}
