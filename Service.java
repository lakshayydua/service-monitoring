
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class Fen {
    Fen() {
        System.out.println("fen construuctor");

    }
    void fen() {
        System.out.println("Fen method");
    }
}

class Fin {
    void fin() {
        System.out.println("Fin method");
    }
}

class Fon {
    void fon() {
        System.out.println("Fon method");
    } 
}

class Fan {
    void fan() {
        System.out.println("Fan method");
    }
    public void run() {
        System.out.println("run");
    }
}


public class Service {
    Service() {
        System.out.println("Fun ");
    }
    void fun() throws UnknownHostException, IOException {
        System.out.println("Fun mathod");
        Socket socket = new Socket("127.0.0.1", 3306);
        InetAddress x = socket.getInetAddress();
        System.out.println(x.getHostAddress());
        

        System.out.println("\n\n Connection Tried ");

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