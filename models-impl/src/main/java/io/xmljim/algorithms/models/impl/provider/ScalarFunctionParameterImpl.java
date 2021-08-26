package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ParameterTypes;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarFunctionParameter;

class ScalarFunctionParameterImpl extends BaseParameterImpl<ScalarFunction> implements ScalarFunctionParameter {

    public ScalarFunctionParameterImpl(final String name, final ScalarFunction value) {
        super(name, value);
    }

    public ScalarFunctionParameterImpl(final String name, final String variable, final ScalarFunction value) {
        super(name, variable, value);
    }

    public ParameterTypes getParameterType() {
        return ParameterTypes.SCALAR_FUNCTION;
    }
}
