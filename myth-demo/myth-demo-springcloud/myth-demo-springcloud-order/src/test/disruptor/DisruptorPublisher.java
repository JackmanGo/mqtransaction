package disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 生产者
 * @author wangxi
 * @date 2019-10-16 16:37
 */
public class DisruptorPublisher {

    private static final AtomicLong INDEX = new AtomicLong(1);
    private Disruptor<DisruptorEvent> disruptor;

    //开启
    public void start(final int bufferSize, final int threadSize){

        ThreadFactory threadFactory = runnable ->
            new Thread(new ThreadGroup("disruptor"), runnable,
                    "disruptor-thread-" + INDEX.getAndIncrement());

        //ProducerType.MULTI 多生产者
        disruptor = new Disruptor<>(new DisruptorEventFactory(), bufferSize, threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());


        DisruptorEventHandlerOne[] consumerOne = new DisruptorEventHandlerOne[threadSize];
        DisruptorEventHandlerTwo[] consumerTwo = new DisruptorEventHandlerTwo[threadSize];
        DisruptorEventHandlerThree[] consumerThree = new DisruptorEventHandlerThree[threadSize];

        for (int i = 0; i < threadSize; i++) {

            consumerOne[i] = new DisruptorEventHandlerOne();
            consumerTwo[i] = new DisruptorEventHandlerTwo();
            consumerThree[i] = new DisruptorEventHandlerThree();
        }

        disruptor.handleEventsWithWorkerPool(consumerOne).handleEventsWithWorkerPool(consumerTwo)
                .thenHandleEventsWithWorkerPool(consumerThree);

        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
    }

    public static void main(String[] args){

        DisruptorPublisher disruptorPublisher = new DisruptorPublisher();
        disruptorPublisher.start(2*2*2*2*2, 10);

        RingBuffer<DisruptorEvent> ringBuffer = disruptorPublisher.disruptor.getRingBuffer();

        for(int i =0;i<5;i++) {

            ringBuffer.publishEvent(new EventTranslatorOneArg<DisruptorEvent, Integer>() {

                /**
                 * 该回调运行于主线程
                 * @param o 由DisruptorEventFactory产生的
                 * @param l 统计，记录回掉的第几个Event
                 * @param o2 publishEvent 第二个参数回填这里
                 */
                @Override
                public void translateTo(DisruptorEvent o, long l, Integer o2) {
                    //设置Event的参数
                    o.setParam(o2);
                }
            }, i);

        }

    }
}
