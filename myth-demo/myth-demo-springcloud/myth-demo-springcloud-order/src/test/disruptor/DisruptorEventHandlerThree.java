package disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author wangxi
 * @date 2019-10-21 21:10
 */
public class DisruptorEventHandlerThree implements WorkHandler<DisruptorEvent> {

    @Override
    public void onEvent(DisruptorEvent disruptorEvent) throws Exception {

        System.out.println("i am thread three");
        disruptorEvent.say("three");
    }
}
