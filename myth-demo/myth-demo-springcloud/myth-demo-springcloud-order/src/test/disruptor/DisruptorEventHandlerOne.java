package disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * 消费者
 * @author wangxi
 * @date 2019-10-16 16:55
 */
public class DisruptorEventHandlerOne implements WorkHandler<DisruptorEvent> {

    @Override
    public void onEvent(DisruptorEvent disruptorEvent) throws Exception {

        System.out.println("i am thread one");
        disruptorEvent.say("one");
    }
}
