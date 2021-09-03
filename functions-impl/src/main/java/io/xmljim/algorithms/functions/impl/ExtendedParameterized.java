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

import io.xmljim.algorithms.model.AbstractParameterized;
import io.xmljim.algorithms.model.Parameter;
import io.xmljim.algorithms.model.ParameterTypes;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.List;

/**
 * Utility class that extends the default {@link AbstractParameterized} model implementation
 * with get* methods for automatic casting/conversion of {@link Scalar} parameter values
 */
public abstract class ExtendedParameterized extends AbstractParameterized {
    public ExtendedParameterized(final String name, final List<Parameter<?>> parameterList) {
        super(name, parameterList);
    }

    public ExtendedParameterized(final String name, final Parameter<?>... parameters) {
        super(name, parameters);
    }

    public ExtendedParameterized(final String name, final String variable, final List<Parameter<?>> parameterList) {
        super(name, variable, parameterList);
    }

    public ExtendedParameterized(final String name, final String variable, final Parameter<?>... parameters) {
        super(name, variable, parameters);
    }

    /**
     * Get an integer value from either a {@link io.xmljim.algorithms.model.ScalarParameter} or {@link io.xmljim.algorithms.model.ScalarFunctionParameter}
     * parameter
     * @param paramName The parameter name
     * @return the integer value from the parameter
     * @throws FunctionException thrown if the parameter does not exist, or cannot be cast to an integer
     */
    public int getInteger(String paramName) {
        ParameterTypes parameterType = getParameterType(paramName).orElseThrow(() -> new FunctionException("No parameter with name " + paramName + " found"));
        if (ParameterTypes.SCALAR.equals(parameterType)) {
            return ((Scalar)getValue(paramName)).asInt();
        } else if (ParameterTypes.SCALAR_FUNCTION.equals(parameterType)) {
            return ((ScalarFunction)getValue(paramName)).compute().asInt();
        } else {
            throw new FunctionException("Invalid parameter cast: cannot return an integer from a " + parameterType.name());
        }
    }

    /**
     * Get an double value from either a {@link io.xmljim.algorithms.model.ScalarParameter} or {@link io.xmljim.algorithms.model.ScalarFunctionParameter}
     * parameter
     * @param paramName The parameter name
     * @return the integer value from the parameter
     * @throws FunctionException thrown if the parameter does not exist, or cannot be cast to a double
     */
    public double getDouble(String paramName) {
        ParameterTypes parameterType = getParameterType(paramName).orElseThrow(() -> new FunctionException("No parameter with name " + paramName + " found"));
        if (ParameterTypes.SCALAR.equals(parameterType)) {
            return ((Scalar)getValue(paramName)).asDouble();
        } else if (ParameterTypes.SCALAR_FUNCTION.equals(parameterType)) {
            return ((ScalarFunction)getValue(paramName)).compute().asDouble();
        } else {
            throw new FunctionException("Invalid parameter cast: cannot return an integer from a " + parameterType.name());
        }
    }
}
