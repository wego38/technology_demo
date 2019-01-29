package javatype;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yuan on 2018/2/2.
 */
public class TestBlockingQueue {
    public class Basket {
        BlockingQueue<String> basket = new LinkedBlockingQueue<String>(3);

        public void produce() throws InterruptedException {
            basket.put("An apple");
        }

        public String consume() throws InterruptedException {
            return basket.take();
        }
    }

    class Producer implements Runnable {
        private String instance;
        private Basket basket;

        public Producer(String instance, Basket basket) {
            this.instance = instance;
            this.basket = basket;
        }

        public void run() {
            try {
                while (true) {
//                    System.out.println("生产者准备生产苹果：" + instance);
                    basket.produce();
//                    System.out.println("!生产者生产苹果完毕：" + instance);
//                    Thread.sleep(300);
                }
            } catch (InterruptedException ex) {
                System.out.println("Producer Interrupted");
            }
        }
    }

    class Consumer implements Runnable {
        private String instance;
        private Basket basket;

        public Consumer(String instance, Basket basket) {
            this.instance = instance;
            this.basket = basket;
        }

        public void run() {
            try {
                while (true) {
                    // 消费苹果
//                    System.out.println("消费者准备消费苹果：" + instance);
//                    System.out.println(basket.consume());
//                    System.out.println("!消费者消费苹果完毕：" + instance);
                    // 休眠1000ms
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                System.out.println("Consumer Interrupted");
            }
        }
    }

    public static void main(String[] args) {
        TestBlockingQueue test = new TestBlockingQueue();

        Basket basket = test.new Basket();

        ExecutorService service = Executors.newCachedThreadPool();
        Producer producer = test.new Producer("生产者001", basket);
        Producer producer2 = test.new Producer("生产者002", basket);
        Consumer consumer = test.new Consumer("消费者001", basket);
        service.submit(producer);
        service.submit(producer2);
        service.submit(consumer);
        // 程序运行5s后，所有任务停止
//        try {
//            Thread.sleep(1000 * 5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        service.shutdownNow();
    }
}
