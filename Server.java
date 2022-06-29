import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Scanner;
 

public class Server {

    static final int PORT = 1978;
    static int client_counts = 0;
    public static int order_id = 0;
    public static EchoThread thrd;

    public static Connection con = null;
    public static Statement stmt= null;
    public static PreparedStatement preparedStmt = null;


    public static void main(String args[]) throws IOException{
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.print("Server is Running");
            InetAddress iAddress = InetAddress.getLocalHost();
            System.out.println(" with IP Address: " + iAddress.getHostAddress());
            Class.forName("com.mysql.cj.jdbc.Driver");
            Scanner sc = new Scanner(System.in);
            System.out.print("Please Enter Database Password : ");         
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/market place","root",sc.nextLine());
            System.out.println("Database is Connected successfully");
            stmt= con.createStatement();
            sc.close();
        } catch(SQLException s){
            System.out.println("Wrong Database Password, Please Try again");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        while (true) {
            try {
                socket = serverSocket.accept();
                client_counts++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                serverSocket.close();
            }
            // new thread for a client
            thrd = new EchoThread(socket, client_counts);
            thrd.start();
        }  
    }
}

class EchoThread extends Thread {

    protected Socket socket;
    private int client_id;
    private ResultSet rs = null;
    private String Fname = null;
    private String Lname = null;
    private String email = null;
    private String password = null;
    private String mobile_num = null;
    private String birthday = null;
    private String address = null;
    private String gender = null;
    private int productID = 0;
    private String product_name = null;
    private String category = null;
    private float price = 0;
    private int quantity = 0;
    private int actual_quantity = 0;
    private int cart_id = 0;
    private String query = null;

    public EchoThread(Socket clientSocket, int client_counts) {
        this.socket = clientSocket;
        this.client_id = client_counts;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        PrintWriter pr = null;
        String client_IP;

        /*BufferedImage brimg = null;
        BufferedOutputStream bufferedOutputStream = null;*/

        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            pr = new PrintWriter(socket.getOutputStream());
            client_IP = brinp.readLine();
            /*for image sending
            OutputStream outputStream = socket.getOutputStream();
            bufferedOutputStream = new BufferedOutputStream(outputStream);*/
        } catch (IOException e) {
            return;
        }
        System.out.println("Client ( " + client_IP + " ) connected with ID: " + client_id);

        while (true) {
            try {
                String client_sent_message = brinp.readLine();
                System.out.println("Client " + client_id + " : " + client_sent_message);
                                 switch (client_sent_message) {

                    // User Interface Backend
                    case ("login"):
                        pr.println("Enter your email: ");   pr.flush();     email = brinp.readLine();
                        pr.println("Enter your Pass: ");    pr.flush();     password = brinp.readLine();

                        System.out.println("Email :" + email);
                        System.out.println("Password :" + password);

                        rs = Server.stmt.executeQuery("SELECT email, password FROM client WHERE email='" + email + "' AND password = '" + password + "';");
                        if (rs.next()) {
                            System.out.println("Successful Login"); pr.println("Successful Login"); pr.flush();
                        } else {
                            System.out.println("UnSuccessful login, Wrong info"); pr.println("UnSuccessful login, Wrong info"); pr.flush();
                        }
                        break;
                                        case ("register"):
                        pr.println("Enter your First Name: ");      pr.flush();     Fname = brinp.readLine();
                        pr.println("Enter your Last Name: ");       pr.flush();     Lname = brinp.readLine();
                        pr.println("Enter your Email: ");           pr.flush();     email = brinp.readLine();
                        pr.println("Enter your Password: ");        pr.flush();     password = brinp.readLine();
                        pr.println("Enter your Mobile Number: ");   pr.flush();     mobile_num = brinp.readLine();
                        pr.println("Enter your Birthday: ");        pr.flush();     birthday = brinp.readLine();
                        pr.println("Enter your gender: ");          pr.flush();     gender = brinp.readLine();
                        pr.println("Enter your address: ");         pr.flush();     address = brinp.readLine();

                        System.out.println("First Name :" + Fname);
                        System.out.println("Last Name :" + Lname);
                        System.out.println("Email :" + email);
                        System.out.println("Password :" + password);
                        System.out.println("Mobile Number :" + mobile_num);
                        System.out.println("Birthday :" + birthday);
                        System.out.println("gender :" + gender);
                        System.out.println("address :" + address);

                        query = "INSERT INTO `client` (`FName`,`LName`,`Email`,`password`,`Mobile`,`amount_of_money`,`BDay`,`gender`,`address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.setString(1, Fname);
                        Server.preparedStmt.setString(2, Lname);
                        Server.preparedStmt.setString(3, email);
                        Server.preparedStmt.setString(4, password);
                        Server.preparedStmt.setString(5, mobile_num);
                        Server.preparedStmt.setFloat(6, 0);
                        Server.preparedStmt.setString(7, birthday);
                        Server.preparedStmt.setString(8, gender);
                        Server.preparedStmt.setString(9, address);
                        Server.preparedStmt.execute();
                        System.out.println("User added");
                        pr.println("Done"); pr.flush();
                        break;
                    case ("change Password"):
                        query = "select password from client where email='" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("sql : " + query);
                        String new_psw, Entered_psw, real_psw = "";
                        if(rs.next()) real_psw = rs.getString(1);
                        pr.println("Enter Your Password");pr.flush();
                        Entered_psw = brinp.readLine();
                        if(real_psw.equals(Entered_psw)){
                            pr.println("Enter Your New Password"); pr.flush(); new_psw = brinp.readLine();
                            query = "update client set password = '" + new_psw + "' where email = '" + email + "';";
                            Server.preparedStmt = Server.con.prepareStatement(query);
                            System.out.println("sql : " + query);
                            Server.preparedStmt.execute();
                            pr.println("password changed"); pr.flush();
                            System.out.println("psw changed");
                        }
                        else {pr.println("Sorry, You Entered wrong psw"); pr.flush(); System.out.println("psw not changed");}
                        pr.println("Done"); pr.flush();
                        break;
                    case ("view profile"):
                        query = "SELECT * FROM client WHERE email ='" + email + "';";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        if (rs.next()) {
                            for(int i=1; i<10; i++){
                                if(i == 6) i++; 
                                pr.println(rs.getString(i)); pr.flush();
                            }
                            pr.println("Done"); pr.flush();
                        }
                        System.out.println("Profile retrieved successfully");
                        break;
                    case("get wallet balance"):
                        query = " SELECT `amount_of_money` FROM `client` WHERE Email = '" + email + "' ;";
                        rs = Server.stmt.executeQuery(query);
                        if(rs.next()) {
                            pr.println(rs.getFloat(1)); pr.flush();
                            System.out.println(rs.getFloat(1));
                            System.out.println("Data Sent");
                        }
                        break;
                  case ("add to wallet"):
                        pr.println("Enter money amount"); pr.flush();
                        float money = Float.parseFloat(brinp.readLine());
                        query = "update client set amount_of_money = amount_of_money + " + money + " where email = '" + email + "';";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.execute();
                        System.out.println("sql : " + query);
                        pr.println("Done");pr.flush();
                        System.out.println("The Money isadded successfully");
                        break;
                 case ("view orders"):
                        query = " SELECT date,address, `order`.price, qty, product_name, id\n" +
                                "FROM `order`, order_item, products\n" +
                                "WHERE order_item.order_id = `order`.id &&\n" +
                                "products.product_ID = order_item.product_ID &&\n" +
                                "client_email = '" + email + "';";
                                rs = Server.stmt.executeQuery(query); System.out.println("sql : " + query);
                        pr.println("OK .. there is the orders");pr.flush();
                        while(rs.next()){
                            pr.println(rs.getString(1));pr.flush();
                            pr.println(rs.getString(2));pr.flush();
                            pr.println(rs.getFloat(3));pr.flush();
                            pr.println(rs.getInt(4));pr.flush();
                            pr.println(rs.getString(5));pr.flush();
                            pr.println(rs.getInt(6));pr.flush();
                        }
                        pr.println("Done"); pr.flush();
                        System.out.println("Sent data successfully");
                        break;
 case ("search"):
                        pr.println("Enter the product name you want to search for"); pr.flush();
                        String search = brinp.readLine();
                        pr.println("Sending results");pr.flush();
                        query = "select * from products where product_name like '%" + search + "%' OR category like '%" +search+ "%';";
                        System.out.println("sql : " + query);
                        System.out.println("search : " + search);
                        rs = Server.stmt.executeQuery(query);
                        boolean flag = false;
                        while (rs.next()) {
                            pr.println(rs.getInt(1));       pr.flush();
                            pr.println(rs.getString(2));    pr.flush();
                            pr.println(rs.getString(3));    pr.flush();
                            pr.println(rs.getString(4));    pr.flush();
                            pr.println(rs.getInt(5));       pr.flush();
                            pr.println(rs.getString(6));    pr.flush();
                            pr.println(rs.getString(7));    pr.flush();
                            flag = true;
                        }
                        if(flag) {
                            pr.println("Done");pr.flush();
                            System.out.println("Search completed");
                        }
                        else{
                            pr.println("Not Found");pr.flush();
                            System.out.println("Search Failed");
                        }
                        break;
                    case("get quantity from name"):
                        pr.println("Enter Product name"); pr.flush(); product_name = brinp.readLine();
                        query = " SELECT `quantity` FROM `products` WHERE product_name = '"+ product_name+ "' ;";
                        rs = Server.stmt.executeQuery(query); System.out.println("sql : " + query);
                        if(rs.next()) { pr.println(rs.getString(1)); pr.flush(); }
                        break;
                     case("get admin balance"):
                        query = " SELECT `balance` FROM `admin` WHERE Email = 'admin@emarket.net' ;";
                        rs = Server.stmt.executeQuery(query); System.out.println("sql : " + query);
                        if(rs.next()) { pr.println(rs.getFloat(1)); pr.flush(); }
                        break;
                   case ("add products in stock"):
                        pr.println("Enter Product ID");     pr.flush();     productID = Integer.parseInt(brinp.readLine());
                        pr.println("Enter added quantity"); pr.flush();     quantity = Integer.parseInt(brinp.readLine());
                        query = "update products set quantity = quantity + " + quantity + " where product_ID = " + productID + ";";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.execute(); System.out.println("sql : " + query);
                        pr.println("Done");pr.flush(); System.out.println("Data updated successfully from admin");
                        break;
  case ("Admin View Orders"):
                        query = "select O.ID AS 'Order ID', Fname, Lname, C.Email, P.product_name, P.category, OI.qty, OI.price, O.date, C.address From client C, order_item OI, products p, `market place`.order  O where O.client_email = C.Email And OI.order_id = O.id And P.product_ID = OI.product_ID;";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        Boolean data_exist = false;
                        pr.println("sending data"); pr.flush();
                        while(rs.next()) {
                            pr.println(rs.getInt(1));       pr.flush();
                            pr.println(rs.getString(2));    pr.flush();
                            pr.println(rs.getString(3));    pr.flush();
                            pr.println(rs.getString(4));    pr.flush();
                            pr.println(rs.getString(5));    pr.flush();
                            pr.println(rs.getString(6));    pr.flush();
                            pr.println(rs.getInt(7));       pr.flush();
                            pr.println(rs.getFloat(8));     pr.flush();
                            pr.println(rs.getString(9));    pr.flush();
                            pr.println(rs.getString(10));   pr.flush();

                            System.out.println("Data is sent");
                            data_exist = true;
                        }
                        if(!data_exist) { pr.println("No Data"); pr.flush(); System.out.println("No Data");}
                        else { pr.println("Done"); pr.flush(); System.out.println("sent data successfully");}
                        break;
                     case ("view user reports"):
                        query = "SELECT * FROM client;";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        Boolean data_exists = false;
                        pr.println("sending data"); pr.flush();
                        while(rs.next()) {
                            for(int i=1; i<10; i++) {pr.println(rs.getString(i)); pr.flush();}
                            pr.println(rs.getInt(10));pr.flush();
                            System.out.println("Data is sent");
                            data_exists = true;
                        }
                        if(!data_exists) { pr.println("No Data"); pr.flush(); System.out.println("No Data");}
                        else { pr.println("Done"); pr.flush(); System.out.println("sent data successfully");}
                        break;
                    case ("add product"):
                        pr.println("Enter product ID");         pr.flush();     productID = Integer.parseInt(brinp.readLine());
                        pr.println("Enter product name");       pr.flush();     product_name = brinp.readLine();
                        pr.println("Enter category");           pr.flush();     category = brinp.readLine();
                        pr.println("Enter product price");      pr.flush();     price = Float.parseFloat(brinp.readLine());
                        pr.println("Enter product quantity");   pr.flush();     quantity = Integer.parseInt(brinp.readLine());

                        System.out.println("Product ID: " + productID);
                        System.out.println("Product name: " + product_name);
                        System.out.println("Enter category: " + category);
                        System.out.println("Enter product price: " + price);
                        System.out.println("Enter product quantity: " + quantity);

                        query = " INSERT INTO `products` (`quantity`,`category`,`price`,`product_name`,`product_ID`) values (?, ?, ?, ?, ?)";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.setInt(1, quantity);
                        Server.preparedStmt.setString(2, category);
                        Server.preparedStmt.setFloat(3, price);
                        Server.preparedStmt.setString(4, product_name);
                        Server.preparedStmt.setInt(5, productID);
                        Server.preparedStmt.execute();
                        System.out.println("Product added");
                        pr.println("Done"); pr.flush();
                        break;
                    

