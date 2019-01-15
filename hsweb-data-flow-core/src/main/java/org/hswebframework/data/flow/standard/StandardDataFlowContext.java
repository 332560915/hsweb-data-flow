package org.hswebframework.data.flow.standard;

import org.hswebframework.data.flow.api.DataFlowContext;
import org.hswebframework.data.flow.api.Logger;
import org.hswebframework.data.flow.api.Progress;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StandardDataFlowContext implements DataFlowContext {

    private Map<String, Object> parameters = new HashMap<>();

    private Logger logger;

    private Progress progress;

    public StandardDataFlowContext(String name, Map<String, Object> parameters) {
        this.parameters.putAll(parameters);
        this.logger = new Slf4jLogger("data.flow." + name);
        this.progress = new Slf4jProgress("data.flow.progress", name);
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public <T> Optional<T> getNodeResult(String nodeId, Class<T> type) {
        // TODO: 19-1-14
        throw new UnsupportedOperationException();
    }

    @Override
    public void putParameter(String key, Object value) {
        parameters.put(key, value);
    }

    @Override
    public Logger logger() {
        return logger;
    }

    @Override
    public Progress progress() {
        return progress;
    }
}
