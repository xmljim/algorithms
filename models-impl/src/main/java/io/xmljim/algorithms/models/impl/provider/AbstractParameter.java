package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.AbstractVariableEntity;
import io.xmljim.algorithms.model.Parameter;

abstract class AbstractParameter<T> extends AbstractVariableEntity implements Parameter<T> {
    private final T value;

    public AbstractParameter(final String name, final T value) {
        super(name);
        this.value = value;
    }

    public AbstractParameter(final String name, final String variable, final T value) {
        super(name, variable);
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
