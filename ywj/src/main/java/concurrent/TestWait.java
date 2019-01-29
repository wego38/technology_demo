package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yuan on 2018/1/26.
 */
public class TestWait {
    public static void main(String[] args) {
        AtomicInteger integer=new AtomicInteger();
        integer.incrementAndGet();
        System.out.println();
//        TestWait c = new TestWait();
//        Object lock = new Object();
//        Storage storage = new TestWait().new Storage();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//        new Thread(c.new Customer(storage, lock)).start();
//        new Thread(c.new Producer(storage, lock)).start();
//        new Thread(c.new Customer(storage, lock)).start();
//        new Thread(c.new Producer(storage, lock)).start();
//        new Thread(c.new Customer(storage, lock)).start();
//        new Thread(c.new Producer(storage, lock)).start();
//        new Thread(c.new Customer(storage, lock)).start();
//        new Thread(c.new Producer(storage, lock)).start();
//        new Thread(c.new Customer(storage, lock)).start();
//        new Thread(c.new Producer(storage, lock)).start();
//        new Thread(c.new Customer(storage, lock)).start();
//        new Thread(c.new Producer(storage, lock)).start();

    }
    private class Customer implements Runnable {
        private Storage storage;
        private Object lock;
        public Customer(Storage storage, Object lock) {
            super();
            this.storage = storage;
            this.lock = lock;
        }
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                synchronized (lock) {
                    while (storage.getFoods().size() <= 0) {
                        try {
                            System.out.println("货物已空，提示生产者生产");
                            lock.wait();   //当前线程在lock上等待，并释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    storage.getFoods().remove(0);
                    lock.notifyAll(); //唤醒消费者与生产者
                    System.out.println("消费者消费1, "+Thread.currentThread().getName()+", 余量："+storage.getFoods().size());
                }
            }
        }
    }

    private class Producer implements Runnable {
        private Storage storage;
        private Object lock;
        public Producer(Storage storage, Object lock) {
            super();
            this.storage = storage;
            this.lock = lock;
        }
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                synchronized (lock) {
                    while (storage.getFoods().size() >= Storage.MAX_SIZE) {
                        try {
                            System.out.println("货物已满，提示消费者消费");

                            lock.wait(); //当前线程在lock上等待，并释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    storage.getFoods().add(1);
                    lock.notifyAll();  //唤醒消费者与生产者
                    System.out.println("生产者生产1, "+Thread.currentThread().getName() +",余量："+storage.getFoods().size());
                }
            }
        }
    }
    public class Storage {
        private List<Object> foods;
        public final static int MAX_SIZE = 5;
        public Storage(){
            foods = new ArrayList<Object>();
        }
        public List<Object> getFoods() {
            return foods;
        }
        public void setFoods(List<Object> foods) {
            this.foods = foods;
        }
    }
}
