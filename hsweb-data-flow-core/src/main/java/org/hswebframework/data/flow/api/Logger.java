package org.hswebframework.data.flow.api;

public interface Logger {

    void info(String message, Object... objects);

    void debug(String message, Object... objects);

    void warn(String message, Object... objects);

    void error(String message, Object... objects);

}
