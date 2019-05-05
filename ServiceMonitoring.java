import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceMonitoring {

    public static Logger logger = Logger.getLogger("CustomLogger"); 
    public static Scanner sc = new Scanner(System.in);

    public ServiceMonitoring(){

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new CustomFormatter());
        logger.addHandler(ch);

        //ch.close();
    }

    private Socket socket;
    private Scanner scanner;

    
    public static void main(String[] args) throws Exception {
        MyClientSocket client = new MyClientSocket();

        logger.info("---------------------------------------------------");
        logger.info("---------------------WELCOME-----------------------");
        logger.info("---------------------------------------------------\n");
        
        client.get_connection();
        client.start();
        
    
    }
}