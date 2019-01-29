package concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuan on 2018/2/5.
 */
public class TestPhaser {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(3);

        FileSearch system = new TestPhaser().new FileSearch("d:/code/1", "log", phaser);
        FileSearch apps =  new TestPhaser().new FileSearch("d:/code/2", "log", phaser);
        FileSearch documents =  new TestPhaser().new FileSearch("d:/code/3", "log", phaser);

        Thread systemThread = new Thread(system, "System");
        systemThread.start();
        Thread appsThread = new Thread(apps, "Apps");
        appsThread.start();
        Thread documentsThread = new Thread(documents, "Documents");
        documentsThread.start();
        try {
            systemThread.join();
            appsThread.join();
            documentsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Terminated: %s\n", phaser.isTerminated());
    }

    public class FileSearch implements Runnable {
        private String initPath;
        private String end;
        private List<String> results;
        private Phaser phaser;
        public FileSearch(String initPath, String end, Phaser phaser) {
            this.initPath = initPath;
            this.end = end;
            this.phaser=phaser;
            results=new ArrayList<>();
        }
        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();//等待所有的线程创建完成，确保在进行文件查找的时候所有的线程都已经创建完成了
            System.out.printf("%s: Starting.\n",Thread.currentThread().getName());
            // 1st Phase: 查找文件
            File file = new File(initPath);
            if (file.isDirectory()) {
                directoryProcess(file);
            }
            // 如果查找结果为false，那么就把该线程从Phaser中移除掉并且结束该线程的运行
            if (!checkResults()){
                return;
            }
            // 2nd Phase: 过滤结果，过滤出符合条件的（一天内的）结果集
            filterResults();
            // 如果过滤结果集结果是空的，那么把该线程从Phaser中移除，不让它进入下一阶段的执行
            if (!checkResults()){
                return;
            }
            // 3rd Phase: 显示结果
            showInfo();
            phaser.arriveAndDeregister();//任务完成，注销掉所有的线程
            System.out.printf("%s: Work completed.\n",Thread.currentThread().getName());
        }
        private void showInfo() {
            for (int i=0; i<results.size(); i++){
                File file=new File(results.get(i));
                System.out.printf("%s: %s\n",Thread.currentThread().getName(),file.getAbsolutePath());
            }
            // Waits for the end of all the FileSearch threads that are registered in the phaser
            phaser.arriveAndAwaitAdvance();
        }
        private boolean checkResults() {
            if (results.isEmpty()) {
                System.out.printf("%s: Phase %d: 0 results.\n",Thread.currentThread().getName(),phaser.getPhase());
                System.out.printf("%s: Phase %d: End.\n",Thread.currentThread().getName(),phaser.getPhase());
                //结果为空，Phaser完成并把该线程从Phaser中移除掉
                phaser.arriveAndDeregister();
                return false;
            } else {
                // 等待所有线程查找完成
                System.out.printf("%s: Phase %d: %d results.\n",Thread.currentThread().getName(),phaser.getPhase(),results.size());
                phaser.arriveAndAwaitAdvance();
                return true;
            }
        }
        private void filterResults() {
            List<String> newResults=new ArrayList<>();
            long actualDate=new Date().getTime();
            for (int i=0; i<results.size(); i++){
                File file=new File(results.get(i));
                long fileDate=file.lastModified();

                if (actualDate-fileDate< TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS)){
                    newResults.add(results.get(i));
                }
            }
            results=newResults;
        }
        private void directoryProcess(File file) {
            // Get the content of the directory
            File list[] = file.listFiles();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (list[i].isDirectory()) {
                        // If is a directory, process it
                        directoryProcess(list[i]);
                    } else {
                        // If is a file, process it
                        fileProcess(list[i]);
                    }
                }
            }
        }
        private void fileProcess(File file) {
            if (file.getName().endsWith(end)) {
                results.add(file.getAbsolutePath());
            }
        }
    }
}
