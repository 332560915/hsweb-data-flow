package org.hswebframework.data.flow.standard.factory.supports;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.hswebframework.data.flow.api.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class JavaMethodInvokeStrategy extends AbstractRunnableFactoryStrategy<JavaInvokeConfig> {

    @Getter
    @Setter
    private ClassLoader classLoader;

    private static Object[] emptyArgs = new Object[0];

    public JavaMethodInvokeStrategy(TaskRunner runner) {
        this(runner, JavaMethodInvokeStrategy.class.getClassLoader());
    }

    public JavaMethodInvokeStrategy(TaskRunner runner, ClassLoader classLoader) {
        super(runner);
        this.classLoader = classLoader;
    }

    @SneakyThrows
    public Object getInstance(Class type) {
        return type.newInstance();
    }

    @SneakyThrows
    public Class getType(String className) {
        return classLoader.loadClass(className);
    }

    @Override
    @SneakyThrows
    public TaskRunnable createRunnable(JavaInvokeConfig config) {
        String className = config.getClassName();
        String methodName = config.getMethodName();
        Class clazz = getType(className);
        Method method;
        try {
            method = clazz.getMethod(methodName);
        } catch (Exception e) {
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (Exception e2) {
                method = Stream.concat(Stream.of(clazz.getMethods()), Stream.of(clazz.getDeclaredMethods()))
                        .filter(m -> m.getName().equals(methodName))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchMethodException(className + "." + methodName));
            }
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        Object instance = Modifier.isStatic(method.getModifiers()) ? null : getInstance(clazz);
        Method finaleMethod = method;
        int parameterCount = method.getParameterCount();
        Class[] methodTypes = method.getParameterTypes();

        return (context, resultConsumer) -> {
            context.logger().info("调用方法[{}.{}]", className, methodName);
            try {
                Object[] invokeParameter = parameterCount > 0 ? new Object[parameterCount] : emptyArgs;
                for (int i = 0; i < parameterCount; i++) {
                    invokeParameter[i] = convertParameter(methodTypes[i], context, config, i);
                }
                Object result = finaleMethod.invoke(instance, (Object[]) invokeParameter);
                context.progress().set(100F);
                resultConsumer.accept(TaskFuture.success(result));
            } catch (Throwable e) {
                resultConsumer.accept(TaskFuture.fail(e));
            }
        };
    }

    protected Object convertParameter(Class type, DataFlowNodeContext context, JavaInvokeConfig config, int index) {
        if (DataFlowNodeContext.class.isAssignableFrom(type)) {
            return context;
        }
        if (Progress.class.isAssignableFrom(type)) {
            return context.progress();
        }
        if (Logger.class.isAssignableFrom(type)) {
            return context.logger();
        }
        // TODO: 19-1-14 完善参数转换
        return config.getParameter(index);
    }

    @Override
    public JavaInvokeConfig newConfig() {
        return new JavaInvokeConfig();
    }

    @Override
    public boolean support(String type) {
        return "java-method".equalsIgnoreCase(type);
    }

    @Override
    public String getName() {
        return "调用JAVA方法";
    }

}
