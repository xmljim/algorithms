package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ParameterTypes;

class BaseParameterImpl<T> extends AbstractParameter<T> {
    public BaseParameterImpl(final String name, final T value) {
        super(name, value);
    }

    public BaseParameterImpl(final String name, final String variable, final T value) {
        super(name, variable, value);
    }

    public ParameterTypes getParameterType() {
        return ParameterTypes.GENERIC;
    }
}
