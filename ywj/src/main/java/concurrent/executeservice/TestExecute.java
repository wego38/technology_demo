package concurrent.executeservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestExecute {

	private static ScheduledExecutorService es = Executors.newScheduledThreadPool(10);
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			es.schedule(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(Thread.currentThread().getName()+"dsfs");
				}
			},5000,TimeUnit.MILLISECONDS);
			Thread.sleep(1000);
		}
	}
}
