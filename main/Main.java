package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Controller.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    public static String Server_IP;
    public static String SendToServer(String str) throws IOException {
        try{
            Client.pr.println(str); Client.pr.flush();
            Client.server_sent_message = Client.bf.readLine();
        }
        catch(Exception e){
            System.out.println("Sorry server went down, Please try to reconnect");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Down");
            alert.setContentText("Sorry server went down, Please try to reconnect");
            alert.showAndWait();
            System.exit(1);
        }
        System.out.println(Client.server_sent_message);
        return Client.server_sent_message;
    }
    
    public static String ReadFromServer() throws IOException {
        Client.server_sent_message = Client.bf.readLine();
        System.out.println(Client.server_sent_message);
        return Client.server_sent_message;
    }

    public static void msgbox (String title, String msg ){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }

    public static final String CURRENCY = "EGP";
    @Override
    public void start(Stage primaryStage) throws Exception{
        Client.main();
        Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        primaryStage.setTitle("E-Market Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter Server IP : ");
        Server_IP = sc.nextLine();
        launch(args);
        sc.close();
    }
}
