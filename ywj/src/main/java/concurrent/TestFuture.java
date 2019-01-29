package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yuan on 2018/2/5.
 */
public class TestFuture {
    static ExecutorService es= Executors.newCachedThreadPool();
    public static void main(String[] args) {
        Future future=es.submit(() -> {
            return "1";
        });
        try {
            Object o=future.get();
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        io.netty.util.concurrent.Future future1=
    }
}
