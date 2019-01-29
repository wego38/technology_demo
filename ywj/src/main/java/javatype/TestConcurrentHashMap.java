package javatype;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by yuan on 2018/1/29.
 */
public class TestConcurrentHashMap {

    static Object lock=new Object();

    private  final static ConcurrentHashMap cmap=new ConcurrentHashMap();
    private  final static HashMap map=new HashMap();

    public synchronized static void putmap(String key,int i){
        map.put(key,i);
    }
    public static void putcmap(String key,int i){
        cmap.put(key,i);
    }

    public static void main(String[] args) {
        cmap.put("c1","c11");
        cmap.size();

//        AtomicLong a=new AtomicLong(0);
//        AtomicLong b=new AtomicLong(0);
//        for (int i=0;i<1;i++) {
//            new Thread(()->{
//                for (int j=0;j<1;j++){
//                    long ta=System.currentTimeMillis();
//                    putcmap(Thread.currentThread().getName()+j,j);
//                    long tb=System.currentTimeMillis();
//                    a.getAndAdd(tb-ta);
//                    long tc=System.currentTimeMillis();
//                    putmap(Thread.currentThread().getName()+j,j);
//                    long td=System.currentTimeMillis();
//                    b.getAndAdd(td-tc);
//                }
//            }).start();
//        }
//        try {
//            Thread.sleep(1000);
//            lock.wait();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println();
    }
}
