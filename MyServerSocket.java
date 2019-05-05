import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServerSocket {

    public static Logger logger = Logger.getLogger("CustomLogger"); 

    public MyServerSocket(){

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        //FileHandler handler = new FileHandler("logging.properties");
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new CustomFormatter()); // set formatter
        logger.addHandler(ch);

        //ch.close();
    }

    private ServerSocket server;

    public ServerSocket StartServer(String ipAddress, Integer portNo) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.server = new ServerSocket(portNo, 1, InetAddress.getByName(ipAddress));
        else
            this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());

        return this.server;
    }

    private void listen() throws Exception {
        String data = null;
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        logger.info(" New connection from - " + clientAddress);

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        while ((data = in.readLine()) != null) {
            logger.info(" Message from - " + clientAddress + ": " + data);
        }
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }

    public static void main(String[] args) throws Exception {
        
        MyServerSocket app = new MyServerSocket();

        logger.info("---------------------------------------------------");
        
        logger.info("---------------------------------------------------");

        Scanner sc = new Scanner(System.in);

        logger.info("Input Host IP Address (preferrably 127.0.0.1)- ");
        String host_ip = sc.nextLine();

        logger.info("Input Port Number - ");
        Integer port_no = sc.nextInt();

        app.StartServer(host_ip, port_no);

        logger.info(
                "Running Server - " + "Host=" + app.getSocketAddress().getHostAddress() + " ; Port=" + app.getPort());

        app.listen();
        sc.close();
    }
}