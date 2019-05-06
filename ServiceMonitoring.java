import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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

    }

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
        
        Map<String, MyClientSocket> client_dict = new Hashtable<String, MyClientSocket>();

        String service_config;
        Integer service_poll_frequency;

        


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
            } catch (Exception e) {
                System.out.println("Illegal Value");
            }

            sc.nextLine();
            MyClientSocket cs = new MyClientSocket();
            MyClientSocket ts;

            switch (user_service_choice_input) {
            case 1:
                host_ip = cs.get_host_ip();
                port_no = cs.get_port_number();
                cs.get_connection(host_ip, port_no, false, 0);
                break;

            case 2:
                host_ip = cs.get_host_ip();
                port_no = cs.get_port_number();
                poll_frequency = cs.get_poll_frequency();

                service_config = host_ip + ":" + Integer.toString(port_no);
                try {
                    ts = client_dict.get(service_config);
                    
                } catch (NullPointerException e) {
                    ts = null;
                }

                if (ts == null) {
                    System.out.println("xxxxxxxxxxx");
                    client_dict.put(service_config, new MyClientSocket());
                }
                else {
                    System.out.println("xx");
                    service_poll_frequency = client_dict.get(service_config).poll_frequency;
                    

                    if (poll_frequency < service_poll_frequency) {    
                        client_dict.get(service_config).schedule_again = true; // cancels running schedule
                        System.out.println("New polling frequency for [" + service_config + "] - " + poll_frequency);
                        poll_frequency = service_poll_frequency;
                    }
                }
                client_dict.get(service_config).num_clients = client_dict.get(service_config).num_clients + 1;
                client_dict.get(service_config).get_connection(host_ip, port_no, true, poll_frequency);  
                
                break;

            case 3:
                System.out.println("Service Config - Number of Client Requests - Status\n");

                for (Map.Entry<String, MyClientSocket> entry : client_dict.entrySet() ) {
                    System.out.println(entry.getKey() + " - " + Integer.toString(entry.getValue().num_clients) 
                                        + " - " + entry.getValue().service_latest_status_str);
                }
                System.out.println("\n");
                break;

            case 4:
                System.out.println("Thank you for trying out the Service Monitoring Tool");

                break;

            default:
                System.out.println("Incorrect Choice");
                break;
            }

        } while (user_service_choice_input != 4);

    }
}
