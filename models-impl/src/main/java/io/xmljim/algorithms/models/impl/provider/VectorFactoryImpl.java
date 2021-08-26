package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.Vector;
import io.xmljim.algorithms.model.provider.VectorFactory;

import java.util.stream.Stream;

class VectorFactoryImpl implements VectorFactory {

    @Override
    public <T> Vector<T> createVector(final String name, final String variable, final Stream<T> stream) {
        return new BaseVectorImpl<>(name, variable, stream);
    }

    @Override
    public ScalarVector createScalarVector(final String name, final String variable, final Stream<Number> stream) {
        return new ScalarVectorImpl(name, variable, stream);
    }
}
