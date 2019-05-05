import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; 

import java.util.logging.Level;
import java.util.logging.*;
import java.util.logging.ConsoleHandler.*;


public class MyServerSocket {
    
    java.util.logging.ConsoleHandler.formatter = com.mycomp.myproj.LogFormatter;    
    private static final Logger logger = Logger.getLogger(MyServerSocket.class.getName());
    
    
    
    private ServerSocket server;

    public MyServerSocket(String ipAddress, Integer portNo) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.server = new ServerSocket(portNo, 1, InetAddress.getByName(ipAddress));
        else
            this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
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

        Scanner sc = new Scanner(System.in);
        
        logger.info("Input Host IP Address - ");
        String host_ip = sc.nextLine();
        
        logger.info("Input Port Number - ");
        Integer port_no = sc.nextInt();
        
        MyServerSocket app = new MyServerSocket(host_ip, port_no);
        logger.info(
                "Running Server - " + "Host=" + app.getSocketAddress().getHostAddress() + " ; Port=" + app.getPort());

        app.listen();
        sc.close();
    }
}