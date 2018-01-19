package logback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLogback {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			try {
				log.info(i+"");
				log.error(i+"err");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
