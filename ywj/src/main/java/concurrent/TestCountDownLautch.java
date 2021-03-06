package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuan on 2018/2/5.
 */
public class TestCountDownLautch {

    public static void main(String[] args) {
        VideoConference conference =new TestCountDownLautch(). new VideoConference(10);
        Thread threadConference = new Thread(conference);
        threadConference.start();//开启await()方法，在内部计数器为0之前线程处于等待状态
        for (int i = 0; i < 10; i++) {
            Participant p = new TestCountDownLautch().new Participant(conference, "Participant " + i);
            Thread t = new Thread(p);
            t.start();
        }
    }

    public class Participant implements Runnable {
        private VideoConference conference;

        private String name;

        public Participant(VideoConference conference, String name) {
            this.conference=conference;
            this.name=name;
        }
        @Override
        public void run() {
            Long duration=(long)(Math.random()*10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            conference.arrive(name);//每到一个人员，CountDownLatch计数器就减少1
        }
    }

    public class VideoConference implements Runnable{
        private final CountDownLatch controller;

        public VideoConference(int number) {
            controller=new CountDownLatch(number);
        }
        public void arrive(String name){
            System.out.printf("%s has arrived.\n",name);

            controller.countDown();//调用countDown()方法，使内部计数器减1
            System.out.printf("VideoConference: Waiting for %d participants.\n",controller.getCount());
        }

        @Override
        public void run() {
            System.out.printf("VideoConference: Initialization: %d participants.\n",controller.getCount());
            try {

                controller.await();//等待，直到CoutDownLatch计数器为0

                System.out.printf("VideoConference: All the participants have come\n");
                System.out.printf("VideoConference: Let's start...\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
