
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

package io.xmljim.algorithms.model.provider;

import io.xmljim.algorithms.model.*;

public interface ParameterFactory {

    <T> Parameter<T> createParameter(String name, String variable, T value);

    default <T> Parameter<T> createParameter(String name, T value) {
        return createParameter(name, null, value);
    }

    <T> FunctionParameter<T> createParameter(String name, String variable, Function<T> value);

    default <T> FunctionParameter<T> createParameter(String name, Function<T> value) {
        return createParameter(name, null, value);
    }

    default <T> FunctionParameter<T> createParameter(Function<T> function) {
        return createParameter(function.getName(), function.getVariable(), function);
    }

    ScalarParameter createParameter(String name, String variable, Number scalar);

    default ScalarParameter createParameter(String name, Number scalar) {
        return createParameter(name, null, scalar);
    }

    <T> VectorParameter<T> createParameter(String name, String variable, Vector<T> vector);

    default <T> VectorParameter<T> createParameter(String name, Vector<T> vector) {
        return createParameter(name, null, vector);
    }

    default <T> VectorParameter<T> createParameter(Vector<T> vector) {
        return createParameter(vector.getName(), vector.getVariable(), vector);
    }

    ScalarVectorParameter createParameter(String name, String variable, ScalarVector scalarVector);

    default ScalarVectorParameter createParameter(String name, ScalarVector scalarVector) {
        return createParameter(name, null, scalarVector);
    }

    default ScalarVectorParameter createParameter(ScalarVector scalarVector) {
        return createParameter(scalarVector.getName(), scalarVector.getVariable(), scalarVector);
    }

    ScalarFunctionParameter createParameter(String name, String variable, ScalarFunction function);

    default ScalarFunctionParameter createParameter(String name, ScalarFunction function) {
        return createParameter(name, null, function);
    }

    default ScalarFunctionParameter createParameter(ScalarFunction function) {
        return createParameter(function.getName(), function.getVariable(), function);
    }

    MatrixParameter createParameter(String name, String variable, Matrix matrix);

    default MatrixParameter createParameter(String name, Matrix matrix) {
        return createParameter(name, null, matrix);
    }

    default MatrixParameter createParameter(Matrix matrix) {
        return createParameter(matrix.getName(), null, matrix);
    }

}
