package io.xmljim.algorithms.models.impl.provider;

import java.util.stream.Stream;

class BaseVectorImpl<T> extends AbstractVector<T> {
    public BaseVectorImpl(final String name, final String variable, final Stream<T> stream) {
        super(name, variable, stream);
    }
}
