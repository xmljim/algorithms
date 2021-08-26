package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.Comparator;
import java.util.stream.Stream;

class ScalarVectorImpl extends BaseVectorImpl<Scalar> implements ScalarVector {

    public ScalarVectorImpl(final String name, final String variable, final Stream<Number> stream) {
        super(name, variable, stream.map(Scalar::of));
    }

    @Override
    public Stream<Scalar> sorted() {
        return super.sorted(Comparator.comparingDouble(Scalar::asDouble));
    }
}
