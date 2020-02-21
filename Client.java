import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        // VARIABLES
    	String expression; // EXPRESSION THAT WE WILL TAKE IT FROM THE USER
        Scanner sc = new Scanner(System.in);
        Socket socket2; // CLIENT SOCKET
        BufferedReader in; // TO READ DATA
        PrintWriter out; // TO WRITE DATA

        try{
        	// Creates a stream socket and connects it to the specified port number at the specified address
            socket2=new Socket(InetAddress.getLocalHost(),2009);  // we start the connection to the server

            // TO TAKE STRING FROM THE USER
            System.out.println("Enter the expression :");
            expression = sc.nextLine(); 
            
            // THE PROCESS OF SENDING THAT STRING TO THE CONNECTED SERVER WITH CLIENT
            out = new PrintWriter(socket2.getOutputStream());
            out.println(expression);
            out.flush();

            // THE PROCESS OF READING DATA FROM SERVER
            in=new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            String m = in.readLine(); // reading the string
            System.out.println("result = "+m);

            // CLOSING THE SOCKET
            socket2.close();  
            sc.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
