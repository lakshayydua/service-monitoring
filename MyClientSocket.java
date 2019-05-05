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
        MyClientSocket client = new MyClientSocket()
        client.EstablishTCPConnectionToServer(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));

        logger.info("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();
    }
}