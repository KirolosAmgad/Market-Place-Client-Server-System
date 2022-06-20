package Controller;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MyAccountAdminController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void logout(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openhome(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/marketadmin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
