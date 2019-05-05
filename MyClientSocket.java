import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClientSocket implements Runnable {

    public static Logger logger = Logger.getLogger("CustomLogger");
    public static Scanner sc = new Scanner(System.in);

    String host_ip;
    int port_no;
    int poll;
    int poll_frequency;

    Boolean service_status;
    Boolean service_latest_status;
    String service_latest_status_str;

    private Thread t;

    public MyClientSocket() {

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new CustomFormatter());
        logger.addHandler(ch);

        // threadName = name;
        // System.out.println("Creating " + threadName );
    }

    private Socket socket;
    private Scanner scanner;

    private Socket EstablishTCPConnectionToServer(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);

        return this.socket;
    }

    public String get_host_ip() {
        System.out.print("Provide Input for \"Host IP Address\" (preferrably 127.0.0.1) - ");
        host_ip = sc.nextLine();

        return host_ip;
    }

    public int get_port_number() {
        System.out.print("Provide Input for \"Port Number\" - ");
        port_no = sc.nextInt();
        sc.nextLine();

        return port_no;
    }

    public int get_poll_frequency() {
        System.out.print("Provide Input for \"Poll Frequency\" (Seconds) - ");
        poll_frequency = sc.nextInt();
        sc.nextLine();

        return poll_frequency;
    }

    public void get_connection(String host_ip, int port_no, Boolean poll, int poll_frequency) {

        try {
            this.host_ip = host_ip;
            this.port_no = port_no;
            this.poll = poll_frequency;

            this.EstablishTCPConnectionToServer(InetAddress.getByName(host_ip), port_no);
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("Connection established ; Service is up");
            System.out.println("--------------------------------------\n");
            this.service_status = true;

        } catch (Exception e) {
            System.out.println(e + "\n");
            System.out.println("--------------------------------------");
            System.out.println("Connection refused ; Service is not up");
            System.out.println("--------------------------------------\n");
            this.service_status = false;
        }

        if (poll) {
            System.out.println("Registering Service with a polling frequency");
            start();
        }
    }

    public void start_client_connection() throws IOException {
        String input;
        while (true) {
            input = scanner.nextLine();
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(input);
            out.flush();
        }
    }

    synchronized void scheduleFetch() {
        try {
            this.EstablishTCPConnectionToServer(InetAddress.getByName(this.host_ip), this.port_no);
            this.service_latest_status = true;
        } catch (Exception e) {
            this.service_latest_status = false;
        }

        if (this.service_latest_status != this.service_status) {
            if (this.service_latest_status) {
                service_latest_status_str = "Up";
            }
            else {
                service_latest_status_str = "Not Up";
            }
            System.out.println("\n\n** Notification ** - Service Status Changed for HOST IP and Port Number - [" + 
                                this.host_ip + ":" + Integer.toString(this.port_no) + "] ; New Service Status - " + service_latest_status_str + "\n");
            
            this.service_status = this.service_latest_status;
        }
    }

    public void run() {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(this::scheduleFetch, 0, this.poll_frequency, TimeUnit.SECONDS);
    }

    public void start() {
        System.out.println("Starting a new Thread\n");
        if (t == null) {
            t = new Thread(this);
            t.start();
        }

    }

    public static void main(String[] args) throws Exception {

        MyClientSocket client = new MyClientSocket();

        // client.get_connection();
        // client.start_client_connection();

    }

}