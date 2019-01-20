package springboot.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class NotifyServiceImpl implements DisposableBean,InitializingBean {
    private Disruptor<NotifyEvent> disruptor;
//    private static final int RING_BUFFER_SIZE = 1024;
    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    @Override
    public void destroy() throws Exception {
        disruptor.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        disruptor = new Disruptor<NotifyEvent>(new NotifyEventFactory(),RING_BUFFER_SIZE,
                Executors.defaultThreadFactory(), ProducerType.MULTI,new BlockingWaitStrategy());
//        disruptor = new Disruptor<NotifyEvent>(new NotifyEventFactory(),RING_BUFFER_SIZE,
//                Executors.newFixedThreadPool(2), ProducerType.SINGLE,new BlockingWaitStrategy());
//        disruptor.setDefaultExceptionHandler(new NotifyEventHandlerException());
        disruptor.handleEventsWithWorkerPool(new NotifyEventHandler(),new NotifyEventHandler());
        disruptor.start();
    }


    public void sendNotify(String message) {
        RingBuffer<NotifyEvent> ringBuffer = disruptor.getRingBuffer();
//        ringBuffer.publishEvent(new EventTranslatorOneArg<NotifyEvent,  String>() {
//            @Override
//            public void translateTo(NotifyEvent event, long sequence, String data) {
//                event.setMessage(data);
//            }
//        }, message);
        ringBuffer.publishEvent((event, sequence, data) -> event.setMessage(data), message); //lambda式写法，如果是用jdk1.8以下版本使用以上注释的一段

    }
}