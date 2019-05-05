
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class Fen {
    Fen() {
        logger.info("fen construuctor");

    }
    void fen() {
        logger.info("Fen method");
    }
}

class Fin {
    void fin() {
        logger.info("Fin method");
    }
}

class Fon {
    void fon() {
        logger.info("Fon method");
    } 
}

class Fan {
    void fan() {
        logger.info("Fan method");
    }
    public void run() {
        logger.info("run");
    }
}


public class Service {
    Service() {
        logger.info("Fun ");
    }
    void fun() throws UnknownHostException, IOException {
        logger.info("Fun mathod");
        Socket socket = new Socket("127.0.0.1", 3306);
        InetAddress x = socket.getInetAddress();
        logger.info(x.getHostAddress());
        

        logger.info("\n\n Connection Tried ");

    }
    public static void main(String[] args) {
        Service fu = new Service();
        try {
            fu.fun();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Fen fe = new Fen();
        fe.fen();
        Fin fi = new Fin();
        fi.fin();
        Fon fo = new Fon();
        fo.fon();
        Fan fa = new Fan();
        fa.fan();
        fa.run();
    }
}