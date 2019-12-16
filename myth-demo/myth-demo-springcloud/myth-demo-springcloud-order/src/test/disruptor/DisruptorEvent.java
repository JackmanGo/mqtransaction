package disruptor;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author wangxi
 * @date 2019-10-16 16:31
 */
@Slf4j
public class DisruptorEvent implements Serializable {

    Integer param;

    public void setParam(Integer i){
        this.param = param;
    }

    public void say(String handle){
        log.info("{}，i===>{},nowTime===>{}, nowThreadId===>{}", handle, param, System.currentTimeMillis()+"", Thread.currentThread().getId()+"");
    }

}
