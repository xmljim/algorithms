package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ParameterTypes;
import io.xmljim.algorithms.model.ScalarParameter;
import io.xmljim.algorithms.model.util.Scalar;

class ScalarParameterImpl extends BaseParameterImpl<Scalar> implements ScalarParameter {
    public ScalarParameterImpl(final String name, final Number value) {
        super(name, Scalar.of(value));
    }

    public ScalarParameterImpl(final String name, final String variable, final Number value) {
        super(name, variable, Scalar.of(value));
    }

    public ParameterTypes getParameterType() {
        return ParameterTypes.SCALAR;
    }
}
