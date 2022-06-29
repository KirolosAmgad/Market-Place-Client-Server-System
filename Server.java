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
