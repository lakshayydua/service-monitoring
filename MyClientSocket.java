import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClientSocket {

    public static Logger logger = Logger.getLogger("CustomLogger"); 
    public static Scanner sc = new Scanner(System.in);
    String host_ip  = "127.0.0.1";
    int port_no  = 3306;


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


    private void start() throws IOException {
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

        logger.info("---------------------------------------------------");
        logger.info("---------------------CLIENT SIDE-------------------");
        logger.info("---------------------------------------------------");
        
        String host_ip;
        int port_no;
        int user_response = 1;

        do{
            try{
                host_ip = client.get_host_ip();
                port_no = client.get_port_number();
                client.EstablishTCPConnectionToServer(InetAddress.getByName(host_ip), port_no);
            }
            catch (Exception e){
                System.out.println(e + "\n");
                logger.info("---------------------------------------------------");
                logger.info("No service is running for provided host and port no provided");
                logger.info("---------------------------------------------------\n");
            }
            logger.info("Do you want to try connneting to a service with different host ip and port number? (1 for Yes / 0 for No)\n");
            user_response = sc.nextInt();
            sc.nextLine();
        }while(user_response == 1);
    
        logger.info("Connected to Server: " + client.socket.getInetAddress());
        client.start();
    }
}