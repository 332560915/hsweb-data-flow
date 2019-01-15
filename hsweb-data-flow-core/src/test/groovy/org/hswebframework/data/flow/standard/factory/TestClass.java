package org.hswebframework.data.flow.standard.factory;

import lombok.SneakyThrows;
import org.hswebframework.data.flow.api.Progress;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class TestClass {

    public Object method() {
        System.out.println(2);
        return "simpleMethod";
    }

    @SneakyThrows
    public static Object staticMethod(Progress progress) {
        progress.set(10F);
        Thread.sleep(1000);
        progress.set(58.2F);
        Thread.sleep(1000);
        return "staticMethod";
    }
}
