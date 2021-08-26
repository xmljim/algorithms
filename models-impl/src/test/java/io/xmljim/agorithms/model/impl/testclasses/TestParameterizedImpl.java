package io.xmljim.agorithms.model.impl.testclasses;

import io.xmljim.algorithms.model.AbstractParameterized;
import io.xmljim.algorithms.model.Parameter;

import java.util.List;

public class TestParameterizedImpl extends AbstractParameterized {
    public TestParameterizedImpl(final String name, final List<Parameter<?>> parameterList) {
        super(name, parameterList);
    }

    public TestParameterizedImpl(final String name, final Parameter<?>... parameters) {
        super(name, parameters);
    }

    public TestParameterizedImpl(final String name, final String variable, final List<Parameter<?>> parameterList) {
        super(name, variable, parameterList);
    }

    public TestParameterizedImpl(final String name, final String variable, final Parameter<?>... parameters) {
        super(name, variable, parameters);
    }
}
