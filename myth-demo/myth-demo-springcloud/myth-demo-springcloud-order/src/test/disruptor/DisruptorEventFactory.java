package disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author wangxi
 * @date 2019-10-16 16:42
 */
public class DisruptorEventFactory implements EventFactory<DisruptorEvent> {

    @Override
    public DisruptorEvent newInstance() {
        return new DisruptorEvent();

    }
}
