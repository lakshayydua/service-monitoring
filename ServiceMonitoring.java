import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceMonitoring {

    public static Logger logger = Logger.getLogger("CustomLogger");
    public static Scanner sc = new Scanner(System.in);

    public ServiceMonitoring() {

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new CustomFormatter());
        logger.addHandler(ch);

        // ch.close();
    }

    private Socket socket;
    private Scanner scanner;

    private boolean fetch = true; // we start to fetch right away

    void start() {
        long FETCH_INTERVAL = 8000;
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(this::scheduleFetch, FETCH_INTERVAL, FETCH_INTERVAL, TimeUnit.MILLISECONDS);
    }

    synchronized void scheduleFetch() {
        fetch = true;
        notify();
    }

    synchronized boolean awaitFetch() throws InterruptedException {
        long WAIT_LIMIT = 20000;
        if (!fetch)
            wait(WAIT_LIMIT);
        try {
            return fetch;
        } finally {
            fetch = false;
        }
    }

    Boolean doFetch() {
        // try{
        // this.EstablishTCPConnectionToServer(InetAddress.getByName(host_ip), port_no);
        // }
        // catch (Exception e) {
        // logger.info("Connection Not Established");
        // System.out.println(e);
        // }
        return true;
    }

    Boolean poll() throws InterruptedException {
        return awaitFetch() ? doFetch() : false;
    }

    public static void main(String[] args) throws Exception {
        
        MyClientSocket cs = new MyClientSocket();

        logger.info("---------------------------------------------------");
        logger.info("-------------------- WELCOME ----------------------");
        logger.info("---------------------------------------------------\n");

        int user_service_choice_input = 1;

        do {
            logger.info("==================== MENU ==================");
            logger.info("Choose Service Monitoring Component - ");
            logger.info("1. Establish a connection to Host IP and Port Number");
            logger.info("2. Register a service with a polling frequency");
            logger.info("3. See data");
            logger.info("4. Exit");
            logger.info("============================================\n");
            System.out.print("Your Choice - ");
                
            try {
                user_service_choice_input = sc.nextInt();
                System.out.println();
            }
            catch (Exception e) {
                logger.info("Illegal Value");
            }

            sc.nextLine();

            switch(user_service_choice_input) {
                case 1:
                    cs.get_connection();
                    break;
                case 2:
                    logger.info("b");
                    break;

                case 3:
                    logger.info("b");
                    break;

                case 4:
                    logger.info("Thank you for trying out the Service Monitoring Tool");
                    
                    break;

                default:
                    logger.info("Incorrect Choice");
                    break;
            }
    

        }while (user_service_choice_input != 4);

        

        
        
        // sm.start();
        // sm.poll(); // 8 seconds
        // sm.poll();
        // sm.poll();
          
        }
    }
