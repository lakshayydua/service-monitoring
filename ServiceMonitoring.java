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
        System.out.print("zxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    // synchronized boolean awaitFetch() throws InterruptedException {
    //     long WAIT_LIMIT = 20000;
    //     if (!fetch)
    //         wait(WAIT_LIMIT);
    //     try {
    //         return fetch;
    //     } finally {
    //         fetch = false;
    //     }
    // }

    // Boolean doFetch() {
    //     // try{
    //     // this.EstablishTCPConnectionToServer(InetAddress.getByName(host_ip), port_no);
    //     // }
    //     // catch (Exception e) {
    //     // System.out.println("Connection Not Established");
    //     // System.out.println(e);
    //     // }
    //     return true;
    // }

    // Boolean poll() throws InterruptedException {
    //     return awaitFetch() ? doFetch() : false;
    // }

    public static void main(String[] args) throws Exception {

        // Call constructor to customise logger properties
        new ServiceMonitoring(); 
        System.out.println("---------------------------------------------------");
        System.out.println("-------------------- WELCOME ----------------------");
        System.out.println("---------------------------------------------------\n");

        int user_service_choice_input = 1;
        String host_ip;
        int port_no;
        int poll_frequency;
        
        

        do {
            System.out.println("==================== MENU ==================");
            System.out.println("Choose Service Monitoring Component - ");
            System.out.println("1. Establish a connection to Host IP and Port Number");
            System.out.println("2. Register a service with a polling frequency");
            System.out.println("3. See data");
            System.out.println("4. Exit");
            System.out.println("============================================\n");
            System.out.print("Your Choice - ");
                
            try {
                user_service_choice_input = sc.nextInt();
                System.out.println();
            }
            catch (Exception e) {
                System.out.println("Illegal Value");
            }

            sc.nextLine();

            switch(user_service_choice_input) {
                case 1:
                    MyClientSocket cs = new MyClientSocket();
                    host_ip = cs.get_host_ip();
                    port_no = cs.get_port_number();
                    cs.get_connection(host_ip, port_no, false, 0);
                    break;

                case 2:
                    MyClientSocket cs1 = new MyClientSocket();
                    host_ip = cs1.get_host_ip();
                    port_no = cs1.get_port_number();
                    poll_frequency = cs1.get_poll_frequency();
                    cs1.get_connection(host_ip, port_no, true, poll_frequency);
                    TimeUnit.SECONDS.sleep(10);
                    break;

                case 3:
                    System.out.println("b");
                    break;

                case 4:
                    System.out.println("Thank you for trying out the Service Monitoring Tool");
                    
                    break;

                default:
                    System.out.println("Incorrect Choice");
                    break;
            }
    

        }while (user_service_choice_input != 4);

        

        
        
        // sm.start();
        // sm.poll(); // 8 seconds
        // sm.poll();
        // sm.poll();
          
        }
    }
