/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class CartController implements Initializable {

    @FXML
    private Label user_placeholder;
    @FXML
    private TableColumn<items, String> Category;

    @FXML
    private TableColumn<items, Float> Price;

    @FXML
    private TableColumn<items, String> Product;

    @FXML
    private TableColumn<items, Integer> Quantity;

    @FXML
    private TableColumn<items, Float> Subtotal;

    @FXML
    private TableView<items> cart_table;


    @FXML
    private Label total_placeholder;
         private Stage stage;
 private Scene scene;
 private Parent root;
    public static String cart_data_info = "";

    public static Float total;
    @FXML
    private TextField new_qty;
    @FXML
    private ComboBox<String> cart_items_list;

    @FXML
    private Label wallet;
    ObservableList<items> list = FXCollections.observableArrayList();
    ObservableList<String> names_list = FXCollections.observableArrayList();
    private String Fname;
    private String Lname;

    void display_wallet_balance() throws IOException {
        wallet.setText("Balance: "+Main.SendToServer("get wallet balance") + " EGP");
    }

    public void viewPersonalData() throws IOException {
        Fname = Main.SendToServer("view profile");
        Lname = Main.ReadFromServer();
        Main.ReadFromServer();
        Main.ReadFromServer();
        Main.ReadFromServer();
        Main.ReadFromServer();
        Main.ReadFromServer();
        Main.ReadFromServer();
        Main.ReadFromServer();
        user_placeholder.setText(Fname + " " + Lname);
    }

    @FXML
    void openhome(MouseEvent event) throws IOException {
  root = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
    }

    @FXML
    void openLogin(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        @FXML
    void openacc(MouseEvent event) throws IOException {
          root = FXMLLoader.load(getClass().getResource("../views/MyAccount.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();

    }

    @FXML
    void purchase(MouseEvent event) throws IOException {
        Main.SendToServer("purchase");
        while (!Client.server_sent_message.equals("Done")&&!Client.server_sent_message.equals("No items in cart")&&!Client.server_sent_message.equals("Sorry, Items aren't available anymore")&&!Client.server_sent_message.equals("Not Enough Balance")){
            Client.server_sent_message = Client.bf.readLine();
            System.out.println(Client.server_sent_message);
        }
        if (Client.server_sent_message.equals("Done")){
            Main.SendToServer("get wallet balance");
            Main.msgbox("Balance Update","Your new Balance is: " + Client.server_sent_message);
            Main.msgbox("Purchase Done","Your order will be delivered from 2 to 4 working hours\nThank You!");
        } else if (Client.server_sent_message.equals("No items in cart")) {
            Main.msgbox("Error","No items in your cart\nPlease Fill your cart and try again");
        } else if (Client.server_sent_message.equals("Sorry, Items aren't available anymore")) {
            Main.msgbox("Error","Sorry your cart items are no longer available in our stock..");
        } else if (Client.server_sent_message.equals("Not Enough Balance")){
            Main.msgbox("Error","Sorry your balance is not enough to complete the purchase");
        }
        root = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void wallet_open(MouseEvent event) throws IOException {
        Main.SendToServer("get wallet balance");
    }

    @FXML
    void change_qty(MouseEvent event) throws IOException {
        try{
            int newQty = Integer.parseInt(new_qty.getText());
            if (newQty < 0 ){
                Main.msgbox("Error","Please Enter a non-negative number");
                return;
            }
            if (cart_items_list.getValue() == null){
                Main.msgbox("Error","Please Specify item to update");
                return;
            }
            Main.SendToServer("user edit cart");
            Main.SendToServer(cart_items_list.getValue());
            Main.SendToServer(Integer.toString(newQty));
            if (Client.server_sent_message.equals("Sorry, Quantity not available")){
                Main.msgbox("Error","Sorry, Quantity requested not available");
                return;
            } else if (Client.server_sent_message.equals("Product removed")){
                Main.msgbox("Request Successful","the selected item has been deleted from your cart");
            } else  if (Client.server_sent_message.equals("Done")){
                Main.msgbox("Request Successful","Selected product quantity has been changed");
            }
            cart_table.getItems().clear();
            cart_items_list.getItems().clear();
            update_cart();
        }catch (NumberFormatException e){
            Main.msgbox("Error","Please Enter a valid number");
        }



    }
    @FXML
    void delete_item(MouseEvent event) throws IOException {
        if (cart_items_list.getValue() == null){
            Main.msgbox("Error","Please Specify item to delete");
            return;
        }
        Main.SendToServer("remove from cart");
        Main.SendToServer(cart_items_list.getValue());
        Main.msgbox("Operation","Deleted");
        cart_table.getItems().clear();
        cart_items_list.getItems().clear();
        update_cart();
    }
    public void update_cart() throws IOException {
        List<Integer> product_IDs = new ArrayList<Integer>();
        List<Integer> qty = new ArrayList<Integer>();
        List<String> product_name = new ArrayList<String>();
        List<String> product_category = new ArrayList<String>();
        List<Float> price = new ArrayList<Float>();
        List<Float> sub_total = new ArrayList<>();
        try {
            Main.SendToServer("view cart items");
            while (!Client.server_sent_message.equals("Done")) {
                qty.add(Integer.parseInt(Client.server_sent_message));
                Main.ReadFromServer();

                Main.ReadFromServer();

                product_IDs.add(Integer.parseInt(Client.server_sent_message));
                Main.ReadFromServer();
            }
            System.out.println(product_IDs);

            total = Float.valueOf(0);

            for (int i = 0; i < product_IDs.size(); i++) {
                Main.SendToServer("get product info");
                System.out.println(product_IDs.get(i));
                Main.SendToServer(Integer.toString(product_IDs.get(i)));
                product_name.add(Client.server_sent_message);

                Main.ReadFromServer();
                product_category.add(Client.server_sent_message);

                Main.ReadFromServer();
                price.add(Float.parseFloat(Client.server_sent_message));

                Main.ReadFromServer();
                sub_total.add(price.get(i) * qty.get(i));
                total += sub_total.get(i);

                items p = new items(qty.get(i), product_name.get(i), product_category.get(i), price.get(i), sub_total.get(i));
                list.add(p);
                names_list.add(p.product_name);
            }
            Quantity.setCellValueFactory(new PropertyValueFactory<items, Integer>("quantity"));
            Product.setCellValueFactory(new PropertyValueFactory<items, String>("product_name"));
            Category.setCellValueFactory(new PropertyValueFactory<items, String>("category"));
            Price.setCellValueFactory(new PropertyValueFactory<items, Float>("price"));
            Subtotal.setCellValueFactory(new PropertyValueFactory<items, Float>("subtotal"));
            cart_table.setItems(list);
            cart_items_list.setItems(names_list);
            String s = String.format("%.2f",total);
            total_placeholder.setText(s + " EGP");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public class items {
        private int quantity;
        private String product_name;
        private String category;
        private float price;
        private float subtotal;

        public items(int quantity, String product_name, String category, float price, float subtotal) {
            this.quantity = quantity;
            this.product_name = product_name;
            this.category = category;
            this.price = price;
            this.subtotal = subtotal;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(float subtotal) {
            this.subtotal = subtotal;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            display_wallet_balance();
            viewPersonalData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            update_cart();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

