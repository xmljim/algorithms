/*
 * Copyright 2021 Jim Earley (xml.jim@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to
 * whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

package io.xmljim.algorithms.functions.impl;

import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.provider.ModelProvider;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public abstract class AbstractModel extends AbstractParameterized implements Model {
    private FunctionProvider functionProvider;
    private ModelProvider modelProvider;
    private Map<String, Coefficient<?>> coefficientMap = new HashMap<>();

    public AbstractModel(final String name, final List<Parameter<?>> parameterList) {
        super(name, parameterList);
    }

    public AbstractModel(final String name, final Parameter<?>... parameters) {
        super(name, parameters);
    }

    public <T> void setCoefficient(Coefficient<T> coefficient) {
        coefficientMap.put(coefficient.getName(), coefficient);
    }

    public void setCoefficient(String name, String label, Scalar value) {
        setCoefficient(getModelProvider().getCoefficientFactory().createCoefficient(name, label, value));
    }

    public void setCoefficient(FunctionType functionType, ScalarFunction function) {
        setCoefficient(functionType.getName(), functionType.getLabel(), function.compute());
    }

    @Override
    public <T> Coefficient<T> getCoefficient(final String name) {
        return (Coefficient<T>) coefficientMap.get(name);
    }

    @Override
    public Stream<Coefficient<?>> coefficients() {
        return coefficientMap.values().stream();
    }

    public FunctionProvider getFunctionProvider() {
        if (functionProvider == null) {
            Iterable<FunctionProvider> functionProviders = ServiceLoader.load(FunctionProvider.class);
            functionProvider = functionProviders.iterator().next();
        }

        return functionProvider;
    }

    public ModelProvider getModelProvider() {
        if (modelProvider == null) {
            Iterable<ModelProvider> modelProviders = ServiceLoader.load(ModelProvider.class);
            modelProvider = modelProviders.iterator().next();
        }

        return modelProvider;
    }
}
