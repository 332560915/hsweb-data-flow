package org.hswebframework.data.flow.standard.parser;

import org.hswebframework.data.flow.model.DataFlowProcessDefinition;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowProcessDefinitionParserStrategy {

    boolean support(String format);

    DataFlowProcessDefinition parse(String meta);

}
