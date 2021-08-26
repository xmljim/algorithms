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

package io.xmljim.algorithms.client;

import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.functions.statistics.Statistics;
import io.xmljim.algorithms.model.provider.*;

import java.util.ServiceLoader;

public class AlgorithmClient {
    private ModelProvider modelProvider;
    private FunctionProvider functionProvider;

    public AlgorithmClient() throws ProviderNotAvailableException {
        Iterable<ModelProvider> modelProviders = ServiceLoader.load(ModelProvider.class);
        modelProvider = modelProviders.iterator().next();

        if (modelProvider == null) {
            throw new ProviderNotAvailableException("Provider for " + modelProvider.getClass().getName() + " not available");
        }

        Iterable<FunctionProvider> functionProviders = ServiceLoader.load(FunctionProvider.class);
        functionProvider = functionProviders.iterator().next();

        if (functionProvider == null) {
            throw new ProviderNotAvailableException("Provider for " + functionProvider.getClass().getName() + " not available");
        }
    }

    public ModelProvider getModelProvider() {
        return modelProvider;
    }

    public FunctionProvider getFunctionProvider() {
        return functionProvider;
    }

    public Statistics getStatistics() {
        return getFunctionProvider().getStatistics();
    }

    public ParameterFactory getParameterFactory() {
        return getModelProvider().getParameterFactory();
    }

    public VectorFactory getVectorFactory() {
        return getModelProvider().getVectorFactory();
    }

    public CoefficientFactory getCoefficientFactory() {
        return getModelProvider().getCoefficientFactory();
    }

    public MatrixFactory getMatrixFactory() {
        return getModelProvider().getMatrixFactory();
    }
}
