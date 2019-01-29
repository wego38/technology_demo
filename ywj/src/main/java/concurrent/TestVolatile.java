package concurrent;

import singleton.DoubleCheckSingleton;

/**
 * Created by yuan on 2018/1/22.
 * 模式 #1：状态标志

 也许实现 volatile 变量的规范使用仅仅是使用一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或请求停机。
 */
public class TestVolatile {

    volatile boolean shutdownRequested;

    public void shutdown() {
        shutdownRequested = true;
    }

    public void doWork() {
        while (!shutdownRequested) {
            // do stuff
        }
    }

    public static void main(String[] args) {
        System.out.println();
        try {
            new Thread(()->{
                try {
                    DoubleCheckSingleton.getSingleton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"thread1").start();
            new Thread(()->{
                try {
                    DoubleCheckSingleton.getSingleton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"thread2").start();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
