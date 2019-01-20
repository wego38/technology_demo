package springboot.disruptor;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

public class NotifyEventHandler implements EventHandler<NotifyEvent>,WorkHandler<NotifyEvent> {

    @Override
    public void onEvent(NotifyEvent notifyEvent, long l, boolean b) throws Exception {
        System.out.println("接收到消息");
        Thread.sleep(10000);
//        this.onEvent(notifyEvent);
    }

    @Override
    public void onEvent(NotifyEvent notifyEvent) throws Exception {
        System.out.println("接收到消息");
        Thread.sleep(10000);
        System.out.println(notifyEvent+">>>"+ UUID.randomUUID().toString());
    }
}
