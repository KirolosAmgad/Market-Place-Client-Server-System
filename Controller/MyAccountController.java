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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Order;
import model.item;

public class MyAccountController implements Initializable {

    @FXML
    private ScrollPane Selected;

    @FXML
     private Stage stage;
     private Scene scene;
     private Parent root;

    @FXML
    private Button add_balanace_button;
    @FXML
    private TextField amount_money;

    @FXML
    private Label name;
    @FXML
    private TableColumn<Order, String> Date;

    @FXML
    private TableColumn<Order, Integer> OrderID;

    @FXML
    private TableColumn<Order, String> Products;

    @FXML
    private TableColumn<Order, String> Qty;

    @FXML
    private TableColumn<Order, Float> Total;
    @FXML
    private TableView<Order> myTable;

    private String Fname;
    private String Lname;
    @FXML
    private Label Fname_holder;
    @FXML
    private Label Lname_holder;
    @FXML
    private Label birthday_holder;
    @FXML
    private Label email_holder;
    @FXML
    private Label gender_holder;
    @FXML
    private Label mob_holder;
    @FXML
    private Label address_holder;
    @FXML
    private Label Fname_label;
    @FXML
    private Label Lname_label;
    @FXML
    private Label birth_label;
    @FXML
    private Label email_label;
    @FXML
    private Label gender_label;
    @FXML
    private Label mob_label;
    @FXML
    private Label pass_label;

    @FXML
    private Label New_pass_label;
    @FXML
    private TextField new_pass;
    @FXML
    private TextField old_pass;
    @FXML
    private Label old_pass_label;
    @FXML
    private Button pass_confirm_button;

private ObservableList<Order> list = FXCollections.observableArrayList();


    public void setarrays(Order o){
        o.setName();
        o.setQty();
    }


    @FXML
    void add_to_wallet(MouseEvent event) throws IOException {
        Main.SendToServer("add to wallet");
        //read server
        String amountMoney = amount_money.getText();
        Main.SendToServer(amountMoney);
        String walletBalance = Main.SendToServer("get wallet balance");
        //alert with new balance value
        Main.msgbox("Transfer done","Your new balance is " + walletBalance);
        Text text = new Text("Your E-Wallet Balance is: " + Main.SendToServer("get wallet balance"));
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        Selected.setContent(text);
    }
    
    public void viewPersonalData(ActionEvent event) throws IOException {
        Selected.setContent(new Text(""));
        Fname = Main.SendToServer("view profile");
        Lname = Main.ReadFromServer();
        String email = Main.ReadFromServer();
        Main.ReadFromServer();
        String mobile = Main.ReadFromServer();
        String birthday = Main.ReadFromServer();
        String gender = Main.ReadFromServer();
        String address = Main.ReadFromServer();
        Main.ReadFromServer();

//        Text data = new Text("First Name: "+ Fname+ "\nLast Name: " +Lname+ "\nEmail:    " +email+ "\nPassword: " +pass+ "\nMobile Number: "+mobile+ "\nBirthday: "+birthday+"\nGender: " +gender+ "\nAddress: " +address) ;
//        data.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
//        Selected.setContent(data);
        Fname_holder.setText(Fname);
        Lname_holder.setText(Lname);
        birthday_holder.setText(birthday);
        gender_holder.setText(gender);
        email_holder.setText(email);
        address_holder.setText(address);
        mob_holder.setText(mobile);
        
        add_balanace_button.setVisible(false);
        amount_money.setVisible(false);
        myTable.setVisible(false);
        Fname_holder.setVisible(true);
        Lname_holder.setVisible(true);
        birthday_holder.setVisible(true);
        gender_holder.setVisible(true);
        email_holder.setVisible(true);
        address_holder.setVisible(true);
        mob_holder.setVisible(true);
        Fname_label.setVisible(true);
        Lname_label.setVisible(true);
        birth_label.setVisible(true);
        gender_label.setVisible(true);
        email_label.setVisible(true);
        pass_label.setVisible(true);
        mob_label.setVisible(true);
        
        new_pass.setVisible(false);
        New_pass_label.setVisible(false);
        old_pass.setVisible(false);
        old_pass_label.setVisible(false);
        pass_confirm_button.setVisible(false);
    }

    public void viewOrders(ActionEvent event) throws IOException {
        myTable.getItems().clear();
        myTable.setVisible(true);
        List<String> date = new ArrayList<String>();
        List<String> address = new ArrayList<String>();
        List<Float> price = new ArrayList<Float>();
        List<Integer> qty = new ArrayList<Integer>();
        List<String> product_name = new ArrayList<String>();
        List<Integer> order_id = new ArrayList<Integer>();
        add_balanace_button.setVisible(false);
        amount_money.setVisible(false);
        Main.SendToServer("view orders");
        boolean flag = true;
        while (!Main.ReadFromServer().equals("Done")){
            date.add(Client.server_sent_message);
            address.add(Main.ReadFromServer());
            price.add(Float.parseFloat(Main.ReadFromServer()));
            qty.add(Integer.parseInt(Main.ReadFromServer()));
            product_name.add(Main.ReadFromServer());
            order_id.add(Integer.parseInt(Main.ReadFromServer()));
            flag=false;
        }
        if (flag) {
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
            Order o = new Order(temp_order, price.get(k), date.get(k), productsForId);
            AllOrders.add(o);
        }


        for(int k = 0; k < AllOrders.size(); k++){
            setarrays(AllOrders.get(k));
            list.add(AllOrders.get(k));
        }

        Selected.setContent(myTable);
        Fname_holder.setVisible(false);
        Lname_holder.setVisible(false);
        birthday_holder.setVisible(false);
        gender_holder.setVisible(false);
        email_holder.setVisible(false);
        address_holder.setVisible(false);
        mob_holder.setVisible(false);
        Fname_label.setVisible(false);
        Lname_label.setVisible(false);
        birth_label.setVisible(false);
        gender_label.setVisible(false);
        email_label.setVisible(false);
        pass_label.setVisible(false);
        mob_label.setVisible(false);
        new_pass.setVisible(false);
        New_pass_label.setVisible(false);
        old_pass.setVisible(false);
        old_pass_label.setVisible(false);
        pass_confirm_button.setVisible(false);
    }

    public void viewEWallet(ActionEvent event) throws IOException {
        Text text =new Text("Your E-Wallet Balance is: " + Main.SendToServer("get wallet balance"));
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        Selected.setContent(text);
        add_balanace_button.setVisible(true);
        amount_money.setVisible(true);
        myTable.setVisible(false);

        Fname_holder.setVisible(false);
        Lname_holder.setVisible(false);
        birthday_holder.setVisible(false);
        gender_holder.setVisible(false);
        email_holder.setVisible(false);
        address_holder.setVisible(false);
        mob_holder.setVisible(false);
        Fname_label.setVisible(false);
        Lname_label.setVisible(false);
        birth_label.setVisible(false);
        gender_label.setVisible(false);
        email_label.setVisible(false);
        pass_label.setVisible(false);
        mob_label.setVisible(false);
        new_pass.setVisible(false);
        New_pass_label.setVisible(false);
        old_pass.setVisible(false);
        old_pass_label.setVisible(false);
        pass_confirm_button.setVisible(false);
    }


    public void changePassword(ActionEvent event) {
        add_balanace_button.setVisible(false);
        amount_money.setVisible(false);
        myTable.setVisible(false);
        Selected.setContent(new Text(""));

        Fname_holder.setVisible(false);
        Lname_holder.setVisible(false);
        birthday_holder.setVisible(false);
        gender_holder.setVisible(false);
        email_holder.setVisible(false);
        address_holder.setVisible(false);
        mob_holder.setVisible(false);
        Fname_label.setVisible(false);
        Lname_label.setVisible(false);
        birth_label.setVisible(false);
        gender_label.setVisible(false);
        email_label.setVisible(false);
        pass_label.setVisible(false);
        mob_label.setVisible(false);

        new_pass.setVisible(true);
        New_pass_label.setVisible(true);
        old_pass.setVisible(true);
        old_pass_label.setVisible(true);
        pass_confirm_button.setVisible(true);
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
    void pass_confirm(MouseEvent event) throws IOException {
        String oldpass = old_pass.getText();
        String newpass = new_pass.getText();
        if (newpass.isEmpty() || newpass.length() < 8 ) {
            Main.msgbox("Invalid new password","Password must contain at least 8 characters");
        }
        else{
            Main.SendToServer("change Password");
            String response = Main.SendToServer(oldpass);
            if (response.equals("Enter Your New Password")){
                Main.SendToServer(newpass);
                Main.msgbox("Request Successful","Password Changed Successfully");
            }else{
                Main.msgbox("Request Denied","Your old password is incorrect");
            }
            Main.ReadFromServer();
            new_pass.setText("");
            old_pass.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
        Date.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
        Total.setCellValueFactory(new PropertyValueFactory<Order,Float>("total"));

        Products.setCellValueFactory(new PropertyValueFactory<Order,String>("name"));
        Qty.setCellValueFactory(new PropertyValueFactory<Order,String>("qty"));

        myTable.setItems(list);

        try {
            viewPersonalData(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        name.setText(Fname+" "+Lname);
    }
}
