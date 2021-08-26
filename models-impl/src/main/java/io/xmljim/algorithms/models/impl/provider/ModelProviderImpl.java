package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.provider.*;

public class ModelProviderImpl implements ModelProvider {
    private final ParameterFactory parameterFactory = new ParameterFactoryImpl();
    private final VectorFactory vectorFactory = new VectorFactoryImpl();
    private final MatrixFactory matrixFactory = new MatrixFactoryImpl(this);
    private final CoefficientFactory coefficientFactory = new CoefficientFactoryImpl();

    @Override
    public ParameterFactory getParameterFactory() {
        return parameterFactory;
    }

    @Override
    public VectorFactory getVectorFactory() {
        return vectorFactory;
    }

    @Override
    public MatrixFactory getMatrixFactory() {
        return matrixFactory;
    }

    @Override
    public CoefficientFactory getCoefficientFactory() {
        return coefficientFactory;
    }
}
