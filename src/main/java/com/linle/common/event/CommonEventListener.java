package com.linle.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.linle.common.util.JsonUtil;


@Component
public class CommonEventListener implements ApplicationListener<CommonEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(CommonEvent event) {
        logger.debug("BaseEvent:{}", JsonUtil.toJSon(event.getSource()));
    }
}
