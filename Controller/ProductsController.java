/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;

public class ProductsController implements Initializable {
    @FXML
 private Stage stage;
 private Scene scene;
 private Parent root;
    @FXML
    private ComboBox<String> products;

    @FXML
    private TextField quantity_added;

    ObservableList<String> names_list = FXCollections.observableArrayList();

    @FXML
    void add_to_stock(MouseEvent event) throws IOException {
        try{
            int addedQty = Integer.parseInt(quantity_added.getText());
            if (addedQty < 1 ){
                Main.msgbox("Error","Please Enter a positive number");
                return;
            }
            if (products.getValue() == null){
                Main.msgbox("Error","Please Specify item to update");
                return;
            }
            
            Main.SendToServer("add quantity to stock");
            Main.SendToServer(products.getValue());
            String qtyafter = Main.SendToServer(quantity_added.getText());
            Main.msgbox("Transaction Successful","The new quantity of " +products.getValue() + " in the stock is equal to: " + qtyafter);
            Main.ReadFromServer();
        }catch (NumberFormatException e){
            Main.msgbox("Error","Please Enter a valid number");
        }
    }

    @FXML
    void openaccount(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/MyAccountAdmin.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> product_name = new ArrayList<String>();
        names_list.clear();
        int i = 0;
        try {
            Main.SendToServer("get all product names");
            while (!Client.server_sent_message.equals("Done")) {
                product_name.add(Client.server_sent_message);
                Main.ReadFromServer();
                names_list.add(product_name.get(i));
                i++;
            }
            products.setItems(names_list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

