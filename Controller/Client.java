package Controller;

import main.Main;

//import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;


public class Client{

    static final int PORT = 1978;
    public static PrintWriter pr;
    public static BufferedReader bf;

    public static InputStream inpStr;
   // public static BufferedInputStream bufferedInputStream;
   // public static BufferedImage bufferedImage;

    public static String client_input = "";
    public static String server_sent_message = "Ok";
    public static Socket s = null;

    public static void changeClientInput(String str){
        client_input = str;
    }

    public static void main() throws IOException, InterruptedException {

        //Scanner sc = new Scanner(System.in);
        InetAddress iAddress = InetAddress.getLocalHost();

        try {
            System.out.println("Trying to connect to server...\n");
            s = new Socket(Main.Server_IP, PORT);
            pr = new PrintWriter(s.getOutputStream());
            bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pr.println(iAddress.getHostAddress()); pr.flush();
            System.out.print("Connected to the Server! \n");
        } catch (IOException e) {
            System.out.print("Server isn't up yet or wrong IP, Please try to restart with correct IP");
            //Main.msgbox("ERROR","The Server is not working now, Please try to restart the app");
            System.exit(0);
            return;
        }


        //for the image receiving
        //inpStr = s.getInputStream();
        //bufferedInputStream = new BufferedInputStream(inpStr);

//        while(true){
//            if (server_sent_message.equals("Not Found") ||server_sent_message.equals("Ok") || server_sent_message.equals("Wrong info") ||server_sent_message.equals("Done") || server_sent_message.equals("Successful Login")|| server_sent_message.split(" ")[0].equals("Enter")){
//                System.out.print("Controller.Client: ");
////                client_input = sc.nextLine();
//                //while(flag);
//                //pr.println(client_input); pr.flush();
//                //flag = true;
//            }
//            if(client_input.equals("exit")) break;
//
//            server_sent_message = bf.readLine();
//            System.out.println("Server : " + server_sent_message);
//        }
//        s.close();
        //sc.close();
    }
}
