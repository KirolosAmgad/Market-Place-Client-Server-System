import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.*;


public class Server {

    static final int PORT = 1978;
    static int client_counts = 0;

    public static int order_id = 1;

    public static Connection con = null;

    public static Statement stmt= null;

    public static PreparedStatement preparedStmt = null;


    public static void main(String args[]) throws IOException{
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is Running");
            InetAddress iAddress = InetAddress.getLocalHost();
            System.out.println("with IP Adress: " + iAddress.getHostAddress());
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/market place","root","Kiro1205");
                System.out.println("Database Connected");
                stmt= con.createStatement();

            }
            catch(Exception e){
                System.out.println(e);}
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                client_counts++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket, client_counts).start();
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
    private String adress = null;
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
        String client_IP, server_reply;

        BufferedImage brimg = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            pr = new PrintWriter(socket.getOutputStream());
            client_IP = brinp.readLine();
            //for image sending
//            OutputStream outputStream = socket.getOutputStream();
//            bufferedOutputStream = new BufferedOutputStream(outputStream);
        } catch (IOException e) {
            return;
        }
        System.out.println("Client ( " + client_IP + " ) connected");

        while (true) {
            try {
                String client_sent_message = brinp.readLine();

                if (client_sent_message.equals("exit")) {
                    System.out.println("Client " + client_id + " : " + "exited the server");
                    socket.close();
                    break;
                }

                System.out.println("Client " + client_id + " : " + client_sent_message);

                switch (client_sent_message) {
                    case ("login"):
                        pr.println("Enter your email: ");
                        pr.flush();
                        email = brinp.readLine();
                        pr.println("Enter your Pass: ");
                        pr.flush();
                        password = brinp.readLine();
                        System.out.println("Email :" + email);
                        System.out.println("Password :" + password);
                        //Server.stmt= Server.con.createStatement();
                        rs = Server.stmt.executeQuery("SELECT email, password FROM client WHERE email='" + email + "' AND password = '" + password + "';");
                        if (rs.next()) {
                            System.out.println("Data are correct");
                            pr.println("Successful Login");
                            pr.flush();
                        } else {
                            System.out.println("No Data");
                            pr.println("Wrong info");
                            pr.flush();
                        }
                        break;
                    case ("register"):
                        pr.println("Enter your First Name: ");
                        pr.flush();
                        Fname = brinp.readLine();
                        pr.println("Enter your Last Name: ");
                        pr.flush();
                        Lname = brinp.readLine();
                        pr.println("Enter your Email: ");
                        pr.flush();
                        email = brinp.readLine();
                        pr.println("Enter your Password: ");
                        pr.flush();
                        password = brinp.readLine();
                        pr.println("Enter your Mobile Number: ");
                        pr.flush();
                        mobile_num = brinp.readLine();
                        pr.println("Enter your Birthday: ");
                        pr.flush();
                        birthday = brinp.readLine();
                        pr.println("Enter your gender: ");
                        pr.flush();
                        gender = brinp.readLine();
                        pr.println("Enter your address: ");
                        pr.flush();
                        adress = brinp.readLine();
                        System.out.println("First Name :" + Fname);
                        System.out.println("Last Name :" + Lname);
                        System.out.println("Email :" + email);
                        System.out.println("Password :" + password);
                        System.out.println("Mobile Number :" + mobile_num);
                        System.out.println("Birthday :" + birthday);
                        System.out.println("gender :" + gender);
                        System.out.println("adress :" + adress);
                        query = "INSERT INTO `client` (`FName`,`LName`,`Email`,`password`,`Mobile`,`amount_of_money`,`BDay`,`gender`,`address`)\n" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.setString(1, Fname);
                        Server.preparedStmt.setString(2, Lname);
                        Server.preparedStmt.setString(3, email);
                        Server.preparedStmt.setString(4, password);
                        Server.preparedStmt.setString(5, mobile_num);
                        Server.preparedStmt.setFloat(6, 0);
                        Server.preparedStmt.setString(7, birthday);
                        Server.preparedStmt.setString(8, gender);
                        Server.preparedStmt.setString(9, adress);
                        Server.preparedStmt.execute();
                        System.out.println("User added");
                        pr.println("Done");
                        pr.flush();
                        break;
                    case ("view profile"):
                        System.out.println("your email is : " + email);

                        query = "SELECT * FROM client WHERE email='" + email + "';";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        if (rs.next()) {
                            pr.println(rs.getString(1));pr.flush();
                            pr.println(rs.getString(2));pr.flush();
                            pr.println(rs.getString(3));pr.flush();
                            pr.println(rs.getString(4));pr.flush();
                            pr.println(rs.getString(5));pr.flush();
                            pr.println(rs.getString(7));pr.flush();
                            pr.println(rs.getString(8));pr.flush();
                            pr.println(rs.getString(9));pr.flush();
                            System.out.println("Data is sent");
                            pr.println("Done"); pr.flush();
                        } else {
                            System.out.println("No Data");
                        }
                        //pr.flush();
                        System.out.println("Sent data successfully");
                        break; 
                    case ("add to wallet"):
                        pr.println("Enter money amount"); pr.flush();
                        float money = Float.parseFloat(brinp.readLine());
                        query = "update client set amount_of_money = amount_of_money + " + money + " where email = '" + email + "';";
                        System.out.println("sql : " + query);
                        System.out.println("money : " + money);
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.execute();
                        System.out.println("query is done");
                        pr.println("Done");pr.flush();
                        System.out.println("sent data successfully");
                        break;
                     case ("view orders"):
                        query = " SELECT date,address, `order`.price, qty, product_name, id\n" +
                                "FROM `order`, order_item, products\n" +
                                "WHERE order_item.order_id = `order`.id &&\n" +
                                "products.product_ID = order_item.product_ID &&\n" +
                                "client_email = '" + email + "';";
                        rs = Server.stmt.executeQuery(query);
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
                    
                     case("get wallet balance"):                        
                        query = " SELECT `amount_of_money` FROM `client` WHERE Email = '" + email + "' ;";                        
                        rs = Server.stmt.executeQuery(query);                        
                        if(rs.next()) {                            
                            pr.println(rs.getFloat(1)); pr.flush();                            
                            System.out.println(rs.getFloat(1));                            
                            System.out.println("Data Sent");}                        
                        break;

                     case ("change Password"):
                        query = "select password from client where email='" + email + "';";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        String  real_psw = "";
                        if(rs.next()) real_psw = rs.getString(1);
                        pr.println("Enter Your Password");pr.flush();
                        String Entered_psw = brinp.readLine();
                        System.out.println(real_psw);
                        System.out.println(Entered_psw);
                        if(real_psw.equals(Entered_psw)){
                            pr.println("Enter Your New Password"); pr.flush();
                            String new_psw = brinp.readLine();

                            query = "update client set password = '" + new_psw + "' where email = '" + email + "';";
                            System.out.println("sql : " + query);
                            Server.preparedStmt = Server.con.prepareStatement(query);
                            Server.preparedStmt.execute();
                            pr.println("password changed"); pr.flush();System.out.println("psw changed");
                        }
                        else {pr.println("Sorry, You Entered wrong psw"); pr.flush(); System.out.println("psw not changed");}
                        pr.println("Done"); pr.flush();
                        break;
                        
                 case ("view products"):
                        query = "SELECT * FROM products ;";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        pr.println("sending data");pr.flush();
                        while (rs.next()) {
                            pr.println(rs.getInt(1));pr.flush();
                            pr.println(rs.getString(2));pr.flush();
                            pr.println(rs.getString(3));pr.flush();
                            pr.println(rs.getString(4));pr.flush();
                            pr.println(rs.getInt(5));pr.flush();
                            pr.println(rs.getString(6));pr.flush();
                            pr.println(rs.getString(7));pr.flush();

//                            ImageIcon imageIcon = new ImageIcon("C:\\Users\\kiro_\\IdeaProjects\\Market\\src\\img\\cherry.png");
//                            Image image = imageIcon.getImage();
//                            brimg = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
//                            bufferedOutputStream.flush();
//                            ImageIO.write(brimg,"png",socket.getOutputStream());bufferedOutputStream.flush();
                        }
                        pr.println("Done");
                        pr.flush();
                        System.out.println("sent data successfully");
                        break;
                        
                    case ("add to cart"):
                        pr.println("Enter product ID: ");pr.flush();
                        productID = Integer.parseInt(brinp.readLine());
                        query = " SELECT `quantity` FROM `products` WHERE product_ID = '" + productID + "';";
                        rs = Server.stmt.executeQuery(query);
                        if(rs.next()){
                            actual_quantity = rs.getInt(1);
                        }
                        pr.println("Enter quantity (smaller than "+ (actual_quantity+1) +"): ");pr.flush();
                        quantity = Integer.parseInt(brinp.readLine());
                        if (quantity > actual_quantity){
                            pr.println("Unavailable entry");pr.flush();
//                            quantity = Integer.parseInt(brinp.readLine());
                            break;
                        }
                        query = " SELECT `cart_id` FROM `client` WHERE Email = '" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        if(rs.next()){
                            cart_id = rs.getInt(1);
                        } else {
                            System.out.println("No Data");
                        }
                        query = "INSERT INTO `cart_item` (qty, cart_id, product_ID) "+ "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE qty= ?";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.setInt(1, quantity);
                        Server.preparedStmt.setInt(2, cart_id);
                        Server.preparedStmt.setInt(3, productID);
                        Server.preparedStmt.setInt(4, quantity);
                        Server.preparedStmt.execute();
                        pr.println("Done"); pr.flush();
                        System.out.println("Item ID :" + productID + " added to cart of the client with email " + email);
                        break;  
                        
                    case ("view cart items"):
                        query = " SELECT `cart_id` FROM `client` WHERE Email = '" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        if(rs.next()){
                            cart_id = rs.getInt(1);
                        } else {
                            System.out.println("No Data");
                        }
                        query = "select * from cart_item where cart_id =" +cart_id+ " ;";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        String cart_item_list;
                        while (rs.next()) {
                            pr.println(rs.getInt(1)); pr.flush();
                            pr.println(rs.getInt(2));pr.flush();
                            pr.println(rs.getInt(3));pr.flush();

                        }
                        pr.println("Done");
                        pr.flush();
//                        System.out.println("Data is sent");
                        //pr.flush();
                        System.out.println("Sent data successfully");
                        break;
           
                    case ("remove from cart"):
                        pr.println("Enter product name to be removed");
                        pr.flush();
                        product_name = brinp.readLine();
                        query = "SELECT cart_id FROM client WHERE Email='" + email + "';";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        if (rs.next()) {
                            cart_id=rs.getInt(1);
                        }
                        query = "SELECT product_id from products where product_name = '" + product_name + "';";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("query is done");
                        if (rs.next()) {
                            productID = rs.getInt(1);
                        }
                        query = "DELETE FROM cart_item WHERE cart_id= " + cart_id + " AND product_ID= " + productID +" ;";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.execute();
                        System.out.println("Product removed");
                        pr.println("Done");
                        pr.flush();
                        break;
                
                     case("purchase"):
                        query = "select CI.*, p.price, p.quantity from client AS C, cart_item as CI, products as p where C.cart_id = CI.cart_id and p.product_ID = CI.product_ID and email='" + email + "';";
                        System.out.println("sql : " + query);
                        rs = Server.stmt.executeQuery(query);
                        boolean cart_empty = true;
                        boolean Items_avialable = true;
                        float total_price = 0;
                        while (rs.next()) {
                            cart_empty = false;
                            int needed_qty = rs.getInt(1);
                            int available_qty = rs.getInt(5);
                            total_price += rs.getFloat(4) * needed_qty;
                            System.out.println("available in stock : " + available_qty);
                            System.out.println("needed qty : " + needed_qty);
                            if(available_qty < needed_qty){Items_avialable = false; break;}
                        }
                        if(cart_empty){
                            pr.println("No items in cart");pr.flush();
                            System.out.println("No items in cart");
                            break;
                        }
                        else if(!Items_avialable){
                            pr.println("Sorry, Items aren't available anymore"); pr.flush();
                            System.out.println("Purchase not done");
                            break;
                        }
                        else System.out.println("Total price for order = " + total_price);

                        query = "select amount_of_money from client where email='" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        float amount_of_money = 0;
                        if(rs.next()) amount_of_money = rs.getFloat(1);
                        if(amount_of_money < total_price){
                            pr.println("Not Enough Balance"); pr.flush();
                            System.out.println("Purchase not done");
                            break;
                        }else {
                            query = "update client set amount_of_money = amount_of_money - " + total_price + " where email = '" + email + "';";
                            System.out.println("sql : " + query);
                            Server.preparedStmt = Server.con.prepareStatement(query);
                            Server.preparedStmt.execute();
                            System.out.println("Enough balance");

                            query = "update admin set balance = balance + " + total_price + " where email = 'sit.amet@yahoo.org';";
                            System.out.println("sql : " + query);
                            Server.preparedStmt = Server.con.prepareStatement(query);
                            Server.preparedStmt.execute();
                        }

                        query = "SELECT address FROM client where email = '" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        String address = "";
                        if(rs.next()) address = rs.getString(1);
                        query = "INSERT INTO `order` (`address`,`price`,`client_email`,`date`) "+ "VALUES(?, ?, ?, ?);";
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.setString(1, address);
                        Server.preparedStmt.setFloat(2, total_price);
                        Server.preparedStmt.setString(3, email);
                        Server.preparedStmt.setString(4, String.valueOf(new Timestamp(System.currentTimeMillis())));
                        Server.preparedStmt.execute();

                        query = "select id from `order` where client_email='" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        int order_id = 0;
                        while(rs.next()) order_id = rs.getInt(1);

                        query = "select CI.*, p.price from client AS C, cart_item as CI, products as p where C.cart_id = CI.cart_id and p.product_ID = CI.product_ID and email='" + email + "';";
                        rs = Server.stmt.executeQuery(query);
                        System.out.println("sql : " + query);
                        while (rs.next()) {
                            query = "INSERT INTO `order_item` (price, qty, order_id, product_ID) "+ "VALUES(?, ?, ?, ?);";
                            Server.preparedStmt = Server.con.prepareStatement(query);
                            int needed_qty = rs.getInt(1);
                            int prod_ID = rs.getInt(3);
                            Server.preparedStmt.setFloat(1, rs.getFloat(4)*needed_qty);
                            Server.preparedStmt.setInt(2, needed_qty);
                            Server.preparedStmt.setInt(3, order_id);
                            Server.preparedStmt.setInt(4, prod_ID);

                            System.out.println("price : " + rs.getFloat(4)*needed_qty);
                            System.out.println("qty : " + needed_qty);
                            System.out.println("order_id : " + Server.order_id);
                            System.out.println("prod id : " + prod_ID);

                            Server.preparedStmt.execute();

                            System.out.println("sql : " + query);
                            query = "update products set quantity = quantity - " + needed_qty + "  where product_ID = "+ prod_ID +  ";" ;
                            System.out.println("sql : " + query);
                            Server.preparedStmt = Server.con.prepareStatement(query);
                            Server.preparedStmt.execute();
                        }
                        query = "delete CI.* from client AS C, cart_item as CI where C.cart_id = CI.cart_id and C.email='" + email + "';";
                        System.out.println("sql : " + query);
                        Server.preparedStmt = Server.con.prepareStatement(query);
                        Server.preparedStmt.execute();
                        pr.println("Done");pr.flush();
                        break;
 
                        
                    default:
                        pr.println("Ok");pr.flush();
                  
