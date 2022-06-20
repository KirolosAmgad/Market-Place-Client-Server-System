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
                    case ("add product"):
                        pr.println("Enter product ID");
                        pr.flush();
                        productID = Integer.parseInt(brinp.readLine());
                        pr.println("Enter product name");
                        pr.flush();
                        product_name = brinp.readLine();
                        pr.println("Enter category");
                        pr.flush();
                        category = brinp.readLine();
                        pr.println("Enter product price");
                        pr.flush();
                        price = Float.parseFloat(brinp.readLine());
                        pr.println("Enter product quantity");
                        pr.flush();
                        quantity = Integer.parseInt(brinp.readLine());

                        System.out.println("Product ID: " + productID);
                        System.out.println("Product name: " + product_name);
                        System.out.println("Enter category: " + category);
                        System.out.println("Enter product price: " + price);
                        System.out.println("Enter product quantity: " + quantity);

                        query = " INSERT INTO `products` (`quantity`,`category`,`price`,`product_name`,`product_ID`)"
                                + " values (?, ?, ?, ?, ?)";
// create the mysql insert preparedstatement
                        Server.preparedStmt = Server.con.prepareStatement(query);

                        Server.preparedStmt.setInt(1, quantity);
                        Server.preparedStmt.setString(2, category);
                        Server.preparedStmt.setFloat(3, price);
                        Server.preparedStmt.setString(4, product_name);
                        Server.preparedStmt.setInt(5, productID);
                        Server.preparedStmt.execute();
                        System.out.println("Product added");
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
