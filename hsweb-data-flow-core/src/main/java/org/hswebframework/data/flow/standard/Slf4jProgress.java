package org.hswebframework.data.flow.standard;

import org.hswebframework.data.flow.api.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Slf4jProgress implements Progress {

    private Logger logger;

    private String prefix;

    public Slf4jProgress(String loggerName, String prefix) {
        this.logger = LoggerFactory.getLogger(loggerName);
        this.prefix = prefix;

    }

    @Override
    public void set(float percent) {
        logger.info("{},{}%", prefix, percent);
    }
}
