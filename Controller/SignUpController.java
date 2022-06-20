/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;

/**
 *
 * @author Mariz
 */
public class SignUpController {
     private Stage stage;
 private Scene scene;
 private Parent root;
 @FXML
 private TextField Adress;

 @FXML
 private DatePicker Birthday;

 @FXML
 private TextField Email;

 @FXML
 private RadioButton Female;

 @FXML
 private TextField First_Name;

 @FXML
 private TextField Last_Name;

 @FXML
 private RadioButton Male;

 @FXML
 private TextField Mobile_Number;

 @FXML
 private PasswordField Password;




 public void  openmarket (ActionEvent event) throws IOException {
  boolean flag = true;
  String Fname = First_Name.getText();
  String Lname = Last_Name.getText();
  if (Fname.isEmpty() || Lname.isEmpty() ) {
   Main.msgbox("Invalid Input"," First name and Last name cannot be left blank");
   flag = false;
  }
  String pass = Password.getText();
  if (pass.isEmpty() || pass.length() < 8 ) {
   Main.msgbox("Invalid Input","Password must contain at least 8 characters");
   flag = false;
  }
  String mobile_num = Mobile_Number.getText();
  String user_email = Email.getText();
  Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
  Matcher mat = pattern.matcher(user_email);
  if (user_email.isEmpty() || !mat.matches()) {
   Main.msgbox("Invalid Input","Enter a valid Email");
   System.out.println(mat.matches());
   flag = false;
  }
  String birthday = Birthday.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

  String address = Adress.getText();
  String gender ="";
  if (Male.isSelected()){
    gender = "M";
  }else if(Female.isSelected()) {
   gender = "F";
  }else{
   Main.msgbox("Invalid Input","Please Specify the gender");
   flag = false;
  }
  if (flag){
   Main.SendToServer("register");
   Main.SendToServer(Fname);
   Main.SendToServer(Lname);
   Main.SendToServer(user_email);
   Main.SendToServer(pass);
   Main.SendToServer(mobile_num);
   Main.SendToServer(birthday);
   Main.SendToServer(gender);
   Main.SendToServer(address);
   if(Client.server_sent_message.equals("Done")){
    Main.msgbox("Congratulations","User Added to the system Successfully\nPlease Login");
    root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
   }
  }


 }
 
    /**
     *
     * @param event
     * @throws IOException
     */
    public void openLogin(ActionEvent event) throws IOException {
  root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
}
    

