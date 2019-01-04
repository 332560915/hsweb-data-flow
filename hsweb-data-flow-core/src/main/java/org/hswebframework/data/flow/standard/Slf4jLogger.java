package org.hswebframework.data.flow.standard;

import org.hswebframework.data.flow.api.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogger implements Logger {

    private org.slf4j.Logger logger;

    public Slf4jLogger(String loggerName) {
        this.logger = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public void info(String message, Object... objects) {
        logger.info(message, objects);
    }

    @Override
    public void debug(String message, Object... objects) {
        logger.debug(message, objects);
    }

    @Override
    public void warn(String message, Object... objects) {
        logger.warn(message, objects);
    }

    @Override
    public void error(String message, Object... objects) {
        logger.error(message, objects);
    }
}
