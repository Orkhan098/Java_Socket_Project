import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {
        
    	// VARIABLES
    	ServerSocket socketserver; // server socket for listening at a port
        Socket socket1; // socket for making communication with client side
        BufferedReader in; // to read data
        PrintWriter out;  // to print data
        boolean isActive = true; // looping forever
        int counter = 1; // to count the order of client

        try {
            while(isActive) {
            	socketserver = new ServerSocket(2009);  // listning at port 2009
                System.out.println("The server "+counter+" is waiting in port:" + socketserver.getLocalPort());
                socket1 = socketserver.accept();   // accept an incoming connection
                System.out.println("Client is connected");
                
                in = new BufferedReader(new InputStreamReader(socket1.getInputStream())); // for reading data from client
                String expression = in.readLine();    // reading expression to be evaluated
                
                //  FOR TERMINATING LOOP
                if(expression.contains("quit")){
                    isActive = false;
                }
                else{
                    out = new PrintWriter(socket1.getOutputStream()); // to make an object in PrintWriter() type
                    out.println(calculator(expression));  // evaluate and send the result to the client
                    out.flush();
                }

                // CLOSING THE SERVER
                socket1.close(); 
                socketserver.close();
                counter++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    // calculator() METHOD FOR MAKING SOME SIMPLE CALCULATIONS ON NUMBERS(STRING TYPE)
    static int calculator(String expression){
        
    	// VARIABLES 
    	int result = -1; // IF THERE IS UNKNOW TYPE OF CALCULATION, Default VALUE FOR RETURN
        String[] split_string = expression.trim().split(" "); // TO SPLIT INTO PARTS IN ORDER TO DO CALCULATION
        int n1, n2; // NUMBERS THAT IS FOR CALCULATION
        
        // AFTER DIVIDING THE STRING IF IT HAS 3 ELEMENTS AND SECOND ELEMENT IS CONSISTS OF 1 CHARACTER
        if(split_string.length==3 && split_string[1].length()==1) {
        	// WE CHECK IF THE GIVEN NUMBER IS IN DIGIT FORMAT("1,23,4312") OR NON DIGIT FORMAT("A,B,C,SDFA")
            try {
                n1 = Integer.parseInt(split_string[0]); // PARSING CHARACTER TO INTEGER
                n2 = Integer.parseInt(split_string[2]);
            }
            catch(NumberFormatException e){
                return -1;
            }
            // AFTER PARSING CHARACTERS TO INTEGER, THERE CAN BE NEGATIVE VALUES
            if (n1 > 0 && n2 > 0) {
            	// switch case FOR OPERATIONS
                switch (split_string[1].charAt(0)) {
                    case '+':
                        result = n1 + n2;
                        break;
                    case '-':
                    	// n1 SHOULD BE GREATER THAN n2, OTHERWISE return -1                    	
                        if (n1>n2) result = n1 - n2;
                        else return -1;
                        break;
                    case '*':
                        result = n1 * n2;
                        break;
                    case '/':
                    	// SECOND INTEGER CAN BE 0, ZERO DIVISION - ERROR                    	
                        if (n2 == 0) return -1;
                        else result = n1 / n2;
                        break;
                    default:
                    	return -1;
                }
                return result;
            }
            else return -1;
        }
        else return -1;
    }

}
