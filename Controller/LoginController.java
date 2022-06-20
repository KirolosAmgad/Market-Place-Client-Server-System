/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.Main;

/**
 * @author Mariz
 */


public class LoginController {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button login;

    @FXML
    private Button sign;

    @FXML
    private Pane signup;

    public void openmarket(ActionEvent event) throws IOException {
        String user_email = email.getText();
        String pass = password.getText();
        if (user_email.equals("admin@emarket.net") && pass.equals("admin1234")) {
            root = FXMLLoader.load(getClass().getResource("../views/marketadmin.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }
        Main.SendToServer("login");
        Main.SendToServer(user_email);
        Main.SendToServer(pass);
        if (Client.server_sent_message.equals("Successful Login")) {
            System.out.println("Login Done");
            root = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Main.msgbox("Unsuccessful login", "Please enter valid credentials");
        }

    }

    /**
     * @param event
     * @throws IOException
     */
    public void opensignup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/SignUpform.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
