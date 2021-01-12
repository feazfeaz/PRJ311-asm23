package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.xml.AddContact;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main extends Application {

    private static final String SPILIT_STRING = "as12Za1K89s";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static ArrayList<String> groupName = new ArrayList<String>();
    public static ArrayList<AddContact.MyUser> myUsers = new ArrayList<AddContact.MyUser>();
    private static File fileGrName = new File("GroupName.txt");
    private static File fileContact = new File("Contact.txt");

    public static void main(String[] args) {
        launch(args);
    }

    public static void log(String content) {
        System.out.println(content);
    }

    public static void loader() {
        groupName.clear();
        groupName.add("All");
        myUsers.clear();
        try {
            if (!fileGrName.createNewFile() || !fileContact.createNewFile()) {
                Scanner scan = new Scanner(fileGrName);
                String value = "";
                //group
                while (scan.hasNextLine()) {
                    value+=scan.nextLine();
                }
                for (String string : value.split(SPILIT_STRING)) groupName.add(string);
                //contact
                scan = new Scanner(fileContact);
                value = "";
                while (scan.hasNextLine())value+=scan.nextLine();
                String[] slice = value.split(SPILIT_STRING);
                for (int i = 0; i < slice.length ;) {
                    try {
                        myUsers.add(new AddContact.MyUser(
                                slice[i++]  // first name
                                ,slice[i++] // last name
                                ,slice[i++] // phone
                                ,slice[i++] // gmail
                                ,dateFormat.parse(slice[i++]) //date
                                ,slice[i++] //group
                        ));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saver() {
        try {
            FileWriter fileWriter = new FileWriter(fileContact);
            String value = "";
            //contact

            if (myUsers.isEmpty()) {
                fileWriter.write("");
                fileWriter.close();
            } else {
                for (AddContact.MyUser myUser : myUsers) {
                    value = value.concat(myUser.getFirst_name() + SPILIT_STRING
                            + myUser.getLast_name() + SPILIT_STRING
                            + myUser.getPhone() + SPILIT_STRING
                            + myUser.getGmail() + SPILIT_STRING
                            + myUser.getBirth_date() + SPILIT_STRING
                            + myUser.getGroup_name() + SPILIT_STRING);
                }
                fileWriter.write(value);
                fileWriter.close();
            }
            //group contact
            value = "";
            fileWriter = new FileWriter(fileGrName);

            ArrayList<String> names = (ArrayList<String>) groupName.clone();
            names.remove(0);
            if (names.isEmpty()) {
                fileWriter.write("");
                fileWriter.close();
                return;
            }

            for (String name : names) value = value + name + SPILIT_STRING;
            fileWriter.write(value);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader();
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("sample.fxml"))));
        primaryStage.setTitle("Contact");
        primaryStage.show();

    }


}
