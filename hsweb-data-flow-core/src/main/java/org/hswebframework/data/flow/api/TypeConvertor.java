package org.hswebframework.data.flow.api;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface TypeConvertor {
    <T> T convert(Object in, Class<T> out);

}
