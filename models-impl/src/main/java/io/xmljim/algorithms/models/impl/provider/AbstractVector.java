package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.AbstractVariableEntity;
import io.xmljim.algorithms.model.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractVector<T> extends AbstractVariableEntity implements Vector<T> {
    private final List<T> data;

    public AbstractVector(String name, String variable, Stream<T> stream) {
        super(name, variable);
        data = stream.collect(Collectors.toList());
    }

    @Override
    public T first() {
        return data.get(0);
    }

    @Override
    public T last() {
        return data.get(data.size() - 1);
    }

    @Override
    public T get(final long index) {
        return data.get((int)index);
    }

    @Override
    public int length() {
        return data.size();
    }

    @Override
    public Stream<T> stream() {
        return data.stream();
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}
