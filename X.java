import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class X {

    public X() {

        int a = 8;

        // ch.close();
    }

    private boolean fetch = true; // we start to fetch right away

    void start() {
        long FETCH_INTERVAL = 8000;
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(this::scheduleFetch, FETCH_INTERVAL, FETCH_INTERVAL, TimeUnit.MILLISECONDS);
    }

    synchronized void scheduleFetch() {
        fetch = true;
        try {
            this.poll();
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        notify();
    }
    
    
    synchronized boolean awaitFetch() throws InterruptedException {
        long WAIT_LIMIT = 20000;
        if (!fetch)
            wait(WAIT_LIMIT);
        try {
            return fetch;
        } finally {
            fetch = false;
        }
    }

    Boolean doFetch() {
        System.out.println("ssss");
        return true;
    }
    
    public Boolean poll() throws InterruptedException {
        return awaitFetch() ? doFetch() : false;
    }
    

    
    public static void main(String[] args) throws Exception {
        X x = new X();

        x.start();
        // x.poll(); // 8 seconds
        // x.poll();
        // x.poll();        
    
    }
}
