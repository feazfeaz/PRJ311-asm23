package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.xml.AddContact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Application {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static ArrayList<String> groupName = new ArrayList<String>() {{
        add("All");
        add("wait");
        add("down");
        add("break");
        add("làm sao mà dc");
    }};
    public static ArrayList<AddContact.MyUser> myUsers = new ArrayList<AddContact.MyUser>() {{
        add(new AddContact.MyUser("hà há", ":))))", "0999999", "dcmm@gmail.com", new Date(), "wait"));
        add(new AddContact.MyUser("hà há", ":))))", "0777779985", "dcmm@gmail.com", new Date(), "wait"));
        add(new AddContact.MyUser("asd", ":))))", "0999999", "dcmm@gmail.com", new Date(), "wait"));
        add(new AddContact.MyUser("hà há", ":))))", "0988889", "dcmm@gmail.com", new Date(), "down"));
        add(new AddContact.MyUser("zxc", ":))))", "0999999", "dcmm@gmail.com", new Date(), "wait"));
        add(new AddContact.MyUser("hzxc", ":))))", "0123465789", "dcmm@gmail.com", new Date(), "break"));
        add(new AddContact.MyUser("hà há", ":))))", "0999999", "dcmm@gmail.com", new Date(), "break"));
    }};

    public static void main(String[] args) {
        launch(args);
    }

    public static void log(String content) {
        System.out.println(content);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
