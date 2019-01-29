package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuan on 2018/1/26.
 */
public class TestJoin {

    ExecutorService es= Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException
    {
        Thread t = new Thread(()->{
            System.out.println("First task started");
            System.out.println("Sleeping for 2 seconds");
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First task completed");
        });
        Thread t1 = new Thread(()->{
            System.out.println("Second task completed");
        });
        t.start();  // Line 15
        t.join(); // Line 16
        t1.start();
    }
}
