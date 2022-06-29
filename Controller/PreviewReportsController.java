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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import model.Order;
import model.Person;
import model.item;

public class PreviewReportsController implements Initializable {
          @FXML 
 private Stage stage;
 private Scene scene;
 private Parent root;
    @FXML
    private TableColumn<Person, String> Address;

    @FXML
    private TableColumn<Person, String> BD;

    @FXML
    private TableColumn<item, String> Category;

    @FXML
    private TableView<Person> ClientTable;

    @FXML
    private ScrollPane ClientTableScroll;

    @FXML
    private TableColumn<Order, String> Date;

    @FXML
    private TableColumn<Person, String> E;

    @FXML
    private TableColumn<Person, String> F;

    @FXML
    private TableColumn<Order, String> Fname;

    @FXML
    private TableColumn<item, Integer> ID;

    @FXML
    private TableColumn<Person, String> L;

    @FXML
    private TableColumn<Order, String> Lname;

    @FXML
    private TableColumn<Person, String> Mob;

    @FXML
    private TableColumn<item, String> Name;

    @FXML
    private TableColumn<Order, Integer> OID;

    @FXML
    private TableView<Order> OrderClientTable;

    @FXML
    private ScrollPane OrderClientTableScroll;

    @FXML
    private TableColumn<item, String> Price;

    @FXML
    private TableView<item> ProductTable;

    @FXML
    private ScrollPane ProductTableScroll;

    @FXML
    private TableColumn<Order, String> Products;

    @FXML
    private TableColumn<Order, String> Qty;

    @FXML
    private TableColumn<item, Integer> Stock;

    @FXML
    private TableColumn<Order, Float> Total;

    @FXML
    private TableColumn<Person, String> Wallet;
    @FXML
    private TableColumn<Order, String> OAddress;
    @FXML
    private TableColumn<Person, String> gender;

   ObservableList<Person> client_list = FXCollections.observableArrayList();
   ObservableList<item> product_list = FXCollections.observableArrayList();
   ObservableList<Order> orders_list = FXCollections.observableArrayList();


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
    @FXML
    void orders_report(MouseEvent event) throws IOException {
       OrderClientTableScroll.setVisible(true);
       ClientTableScroll.setVisible(false);
       ProductTableScroll.setVisible(false);
       OrderClientTable.getItems().clear();
       ClientTable.getItems().clear();
       ProductTable.getItems().clear();

        List<String> date = new ArrayList<String>();
        List<String> address = new ArrayList<String>();
        List<Float> price = new ArrayList<Float>();
        List<Integer> qty = new ArrayList<Integer>();
        List<String> product_name = new ArrayList<String>();
        List<Integer> order_id = new ArrayList<Integer>();
        List<String> Fname = new ArrayList<String>();
        List<String> Lname = new ArrayList<String>();
        List<String> email = new ArrayList<String>();
        List<String> product_category = new ArrayList<String>();

        Main.SendToServer("Admin View Orders");
        Main.ReadFromServer();

        while (!Client.server_sent_message.equals("Done")&&!Client.server_sent_message.equals("No Data")){
            order_id.add(Integer.parseInt(Client.server_sent_message));
            Fname.add(Main.ReadFromServer());
            Lname.add(Main.ReadFromServer());
            email.add(Main.ReadFromServer());
            product_name.add(Main.ReadFromServer());
            product_category.add(Main.ReadFromServer());
            qty.add(Integer.parseInt(Main.ReadFromServer()));
            price.add(Float.parseFloat(Main.ReadFromServer()));
            date.add(Main.ReadFromServer());
            address.add(Main.ReadFromServer());
            Main.ReadFromServer();
        }
        if (Client.server_sent_message.equals("No Data")){
            Main.msgbox("Error","No orders in the system");
            return;
        }
        ArrayList<Order> AllOrders = new ArrayList<Order>();
        ArrayList<item> productsForId = new ArrayList<item>();


        int temp_order = -1;
        for(int k = 0; k < order_id.size(); k++) {
            if(temp_order == order_id.get(k)) continue;
            temp_order = order_id.get(k);
            //fix fixed sized arrays
            productsForId = new ArrayList<item>();
            for (int j = 0; j < order_id.size(); j++){
                if (temp_order == order_id.get(j)) {
                    productsForId.add(new item(product_name.get(j), qty.get(j)));
                }
            }
            Order o = new Order(temp_order, price.get(k), date.get(k), productsForId,Fname.get(k),Lname.get(k),address.get(k));
            AllOrders.add(o);
        }
        for(int k = 0; k < AllOrders.size(); k++){
            setarrays(AllOrders.get(k));
            orders_list.add(AllOrders.get(k));
        }
    }
   public void setarrays(Order o){
      o.setName();
      o.setQty();
   }

    @FXML
    void products_report(MouseEvent event) throws IOException {
       OrderClientTableScroll.setVisible(false);
       ClientTableScroll.setVisible(false);
       ProductTableScroll.setVisible(true);
       OrderClientTable.getItems().clear();
       ClientTable.getItems().clear();
       ProductTable.getItems().clear();

        List<Integer> product_IDs = new ArrayList<Integer>();;
        List<Integer> qty = new ArrayList<Integer>();
        List<String> product_name = new ArrayList<String>();
        List<String> product_category = new ArrayList<String>();
        List<String> price = new ArrayList<String>();
        List<String> imgsrc = new ArrayList<String>();
        List<String> color = new ArrayList<String>();

        Main.SendToServer("view products");
        int i = 0;
        while (!Main.ReadFromServer().equals("Done")){
            qty.add(Integer.parseInt(Client.server_sent_message));
            product_category.add(Main.ReadFromServer());
            price.add(Main.ReadFromServer());
            product_name.add(Main.ReadFromServer());
            product_IDs.add(Integer.parseInt(Main.ReadFromServer()));
            imgsrc.add(Main.ReadFromServer());
            color.add(Main.ReadFromServer());
            item p = new item(product_name.get(i),product_category.get(i),product_IDs.get(i),price.get(i),qty.get(i));
            product_list.add(p);
            i++;
        }
    }

    @FXML
    void user_reports(MouseEvent event) throws IOException {
       OrderClientTableScroll.setVisible(false);
       ClientTableScroll.setVisible(true);
       ProductTableScroll.setVisible(false);
       OrderClientTable.getItems().clear();
       ClientTable.getItems().clear();
       ProductTable.getItems().clear();

        List<String> Fname = new ArrayList<String>();
        List<String> Lname = new ArrayList<String>();
        List<String> email = new ArrayList<String>();
        List<String> mobile = new ArrayList<String>();
        List<String> balance = new ArrayList<String>();
        List<String> birthday = new ArrayList<String>();
        List<String> gender = new ArrayList<String>();
        List<String> address = new ArrayList<String>();
        List<String> cart_ids = new ArrayList<String>();


        Main.SendToServer("view user reports");
        int i = 0;
        while (!Main.ReadFromServer().equals("Done")){
            Fname.add(Client.server_sent_message);
            Lname.add(Main.ReadFromServer());
            email.add(Main.ReadFromServer());
            Main.ReadFromServer();
            mobile.add(Main.ReadFromServer());
            balance.add(Main.ReadFromServer());
            birthday.add(Main.ReadFromServer());
            gender.add(Main.ReadFromServer());
            address.add(Main.ReadFromServer());
            cart_ids.add(Main.ReadFromServer());

            Person p = new Person(Fname.get(i),Lname.get(i),email.get(i),balance.get(i),mobile.get(i),birthday.get(i),address.get(i),gender.get(i));
            client_list.add(p);
            i++;
        }
    }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
       // Clients report table
      F.setCellValueFactory(new PropertyValueFactory<Person,String>("Fname"));
      L.setCellValueFactory(new PropertyValueFactory<Person,String>("Lname"));
      E.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));
      Mob.setCellValueFactory(new PropertyValueFactory<Person,String>("Mobile"));
      Wallet.setCellValueFactory(new PropertyValueFactory<Person,String>("Wallet"));
      BD.setCellValueFactory(new PropertyValueFactory<Person,String>("bd"));
      Address.setCellValueFactory(new PropertyValueFactory<Person,String>("Address"));
      gender.setCellValueFactory(new PropertyValueFactory<Person,String>("Gender"));
      ClientTable.setItems(client_list);

      // products report table
      ID.setCellValueFactory(new PropertyValueFactory<item,Integer>("id"));
      Name.setCellValueFactory(new PropertyValueFactory<item,String>("name"));
      Price.setCellValueFactory(new PropertyValueFactory<item,String>("price"));
      Stock.setCellValueFactory(new PropertyValueFactory<item, Integer>("stockqty"));
      Category.setCellValueFactory(new PropertyValueFactory<item,String>("category"));
      ProductTable.setItems(product_list);

      // orders report table
      Fname.setCellValueFactory(new PropertyValueFactory<Order,String>("Fname"));
      Lname.setCellValueFactory(new PropertyValueFactory<Order,String>("Lname"));
      OID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
      Date.setCellValueFactory(new PropertyValueFactory<Order,String>("Date"));
      Total.setCellValueFactory(new PropertyValueFactory<Order,Float>("Total"));
      Products.setCellValueFactory(new PropertyValueFactory<Order,String>("Name"));
      Qty.setCellValueFactory(new PropertyValueFactory<Order,String>("Qty"));
      OAddress.setCellValueFactory(new PropertyValueFactory<Order, String>("Address"));
      OrderClientTable.setItems(orders_list);
   }
}
