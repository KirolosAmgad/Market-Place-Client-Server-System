package Controller;

import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Client{

    static final int PORT = 1978;
    public static PrintWriter pr;
    public static BufferedReader bf;

    public static InputStream inpStr;
    public static BufferedInputStream bufferedInputStream;
    public static BufferedImage bufferedImage;

    public static String client_input = "";
    public static String server_sent_message = "Ok";
    private static boolean flag = true;
    public static Socket s = null;

    public static void changeClientInput(String str){
        client_input = str;
        flag = false;
    }

    public static void main() throws IOException{


        //Scanner sc = new Scanner(System.in);
        InetAddress iAddress = InetAddress.getLocalHost();

        try {
            s = new Socket("localhost", PORT);
            System.out.print("Connected to the Server \n");
        } catch (IOException e) {
            System.out.print("Server isn't up yet");
            //sc.close();
            return;
        }

        pr = new PrintWriter(s.getOutputStream());
        bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
        //for the image receiving
        inpStr = s.getInputStream();
        bufferedInputStream = new BufferedInputStream(inpStr);

        pr.println(iAddress.getHostAddress()); pr.flush();



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
