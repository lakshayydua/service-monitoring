import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;





public class XX {


    public static void main(String[] args) throws Exception {
        
        // please change name of your own choice
        Logger log = Logger.getLogger("CustomLogger"); 
        log.setUseParentHandlers(false);
        log.setLevel(Level.ALL);

        //FileHandler handler = new FileHandler("logging.properties");
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new CustomFormatter()); // set formatter
        log.addHandler(ch);

        log.info("test message");

        ch.close(); // close the handler at some later point in your application.        
    }
}