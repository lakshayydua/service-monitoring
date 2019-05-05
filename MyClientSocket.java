import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

public class MyClientSocket {

    public static Logger logger = Logger.getLogger("CustomLogger"); 
    public static Scanner sc = new Scanner(System.in);
    String host_ip  = "127.0.0.1";
    int port_no  = 3306;

    int try_reconnection_user_response = 1;


    public MyClientSocket(){

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new CustomFormatter());
        logger.addHandler(ch);

        //ch.close();
    }

    private Socket socket;
    private Scanner scanner;

    private Socket EstablishTCPConnectionToServer(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);

        return this.socket;
    }

    private String get_host_ip() {
        logger.info("Provide input for \"Host IP Address\" (preferrably 127.0.0.1)- ");
        this.host_ip = sc.nextLine();

        return this.host_ip;
    }

    private Integer get_port_number() {
        logger.info("Provide input for \"Port Number\" - ");
        this.port_no = sc.nextInt();
        sc.nextLine();

        return this.port_no;
    }

    public void get_connection(){
        // Poll till user connects to a running service
        // Prompt user to enter Host IP and Port Number again or quite program
        do{
            try{
                this.get_host_ip();
                this.get_port_number();
                this.EstablishTCPConnectionToServer(InetAddress.getByName(this.host_ip), this.port_no);
            }
            catch (Exception e){
                System.out.println(e + "\n");
                logger.info("-------------------------------------------------------------");
                logger.info("No Service is Running for Provided Host IP and Port Number");
                logger.info("-------------------------------------------------------------\n");
            }
            logger.info("Do you want to try connneting to a service with different Host IP and Port Number? (1 for Yes / 0 for No)\n");
            try_reconnection_user_response = sc.nextInt();
            sc.nextLine();
        }while(try_reconnection_user_response == 1);
    
        logger.info("Connected to Server: " + this.socket.getInetAddress());
    }

    

    public void start() throws IOException {
        String input;
        while (true) {
            input = scanner.nextLine();
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(input);
            out.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        MyClientSocket client = new MyClientSocket();

        client.get_connection();
        client.start();
        

    }
}