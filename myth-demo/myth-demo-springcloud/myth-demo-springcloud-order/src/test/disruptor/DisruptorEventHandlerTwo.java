package disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author wangxi
 * @date 2019-10-21 21:08
 */
public class DisruptorEventHandlerTwo implements WorkHandler<DisruptorEvent> {

    @Override
    public void onEvent(DisruptorEvent disruptorEvent) throws Exception {

        System.out.println("i am thread two");
        disruptorEvent.say("two");
    }
}
