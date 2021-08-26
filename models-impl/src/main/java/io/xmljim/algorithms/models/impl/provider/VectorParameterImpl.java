package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ParameterTypes;
import io.xmljim.algorithms.model.Vector;
import io.xmljim.algorithms.model.VectorParameter;

class VectorParameterImpl<T> extends BaseParameterImpl<Vector<T>> implements VectorParameter<T> {
    public VectorParameterImpl(final String name, final Vector<T> value) {
        super(name, value);
    }

    public VectorParameterImpl(final String name, final String variable, final Vector<T> value) {
        super(name, variable, value);
    }

    @Override
    public ParameterTypes getParameterType() {
        return ParameterTypes.VECTOR;
    }
}
