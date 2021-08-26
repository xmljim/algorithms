package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.Function;
import io.xmljim.algorithms.model.FunctionParameter;
import io.xmljim.algorithms.model.ParameterTypes;

class FunctionParameterImpl<T> extends BaseParameterImpl<Function<T>> implements FunctionParameter<T> {
    public FunctionParameterImpl(final String name, final Function<T> value) {
        super(name, value);
    }

    public FunctionParameterImpl(final String name, final String variable, final Function<T> value) {
        super(name, variable, value);
    }

    public ParameterTypes getParameterType() {
        return ParameterTypes.FUNCTION;
    }
}
