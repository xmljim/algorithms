package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ParameterTypes;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.ScalarVectorParameter;

class ScalarVectorParameterImpl extends BaseParameterImpl<ScalarVector> implements ScalarVectorParameter {
    public ScalarVectorParameterImpl(final String name, final ScalarVector value) {
        super(name, value);
    }

    public ScalarVectorParameterImpl(final String name, final String variable, final ScalarVector value) {
        super(name, variable, value);
    }

    public ParameterTypes getParameterType() {
        return ParameterTypes.SCALAR_VECTOR;
    }
}
