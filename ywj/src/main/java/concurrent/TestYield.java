package concurrent;

/**
 * Created by yuan on 2018/1/26.
 */
public class TestYield {
    public static void main(String[] args) {
        Thread producer = new TestYield().new Producer();
        Thread consumer = new TestYield().new Consumer();

        producer.setPriority(Thread.MIN_PRIORITY); //Min Priority
        consumer.setPriority(Thread.MAX_PRIORITY); //Max Priority

        producer.start();
        consumer.start();
    }
    class Producer extends Thread {
        public void run() {
            for (int i = 0; i < 50; i++) {
                System.out.println("I am Producer : Produced Item " + i);
                Thread.yield();
            }
        }
    }

    class Consumer extends Thread {
        public void run() {
            for (int i = 0; i < 50; i++) {
                System.out.println("I am Consumer : Consumed Item " + i);
                Thread.yield();
            }
        }
    }

}
