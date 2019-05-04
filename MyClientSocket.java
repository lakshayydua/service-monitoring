import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClientSocket {

    private static final Logger logger = Logger.getLogger(MyServerSocket.class.getName());

    private Socket socket;
    private Scanner scanner;

    private MyClientSocket(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
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
        MyClientSocket client = new MyClientSocket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));

        logger.info("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();
    }
}