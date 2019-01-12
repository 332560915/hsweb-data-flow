package org.hswebframework.data.flow.standard;

import org.hswebframework.data.flow.api.DataFlowContext;
import org.hswebframework.data.flow.api.Logger;
import org.hswebframework.data.flow.api.Progress;

import java.util.Map;
import java.util.Optional;

public class StandardDataFlowContext implements DataFlowContext {

    private Map<String, Object> parameters;


    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public <T> Optional<T> getNodeResult(String nodeId, Class<T> type) {
        return null;
    }

    @Override
    public void putParameter(String key, Object value) {

    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public Progress progress() {
        return null;
    }
}
