package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.provider.ParameterFactory;

class ParameterFactoryImpl implements ParameterFactory {

    @Override
    public <T> Parameter<T> createParameter(final String name, final String variable, final T value) {
        return new BaseParameterImpl<>(name, variable, value);
    }

    @Override
    public <T> FunctionParameter<T> createParameter(final String name, final String variable, final Function<T> value) {
        return new FunctionParameterImpl<>(name, variable, value);
    }

    @Override
    public ScalarParameter createParameter(final String name, final String variable, final Number scalar) {
        return new ScalarParameterImpl(name, variable, scalar);
    }

    @Override
    public <T> VectorParameter<T> createParameter(final String name, final String variable, final Vector<T> vector) {
        return new VectorParameterImpl<>(name, variable, vector);
    }

    @Override
    public ScalarVectorParameter createParameter(final String name, final String variable, final ScalarVector scalarVector) {
        return new ScalarVectorParameterImpl(name, variable, scalarVector);
    }

    @Override
    public ScalarFunctionParameter createParameter(final String name, final String variable, final ScalarFunction function) {
        return new ScalarFunctionParameterImpl(name, variable, function);
    }

    @Override
    public MatrixParameter createParameter(final String name, final String variable, final Matrix matrix) {
        return new MatrixParameterImpl(name, variable, matrix);
    }
}
