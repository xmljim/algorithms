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

package io.xmljim.algorithms.functions.impl.statistics;

import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.provider.ModelProvider;
import io.xmljim.algorithms.model.util.Scalar;
import org.junit.jupiter.api.*;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Create a function to calculate the sum of a sequence")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SumFunctionTest {
    ScalarVector vector;
    FunctionProvider functionProvider;
    ModelProvider modelProvider;
    ScalarFunction meanFunction;

    @BeforeEach
    @DisplayName("is created from a FunctionProvider")
    void createFunctionProvider() {
        Iterable<FunctionProvider> functionProviders = ServiceLoader.load(FunctionProvider.class);
        functionProvider = functionProviders.iterator().next();

        Iterable<ModelProvider> modelProviders = ServiceLoader.load(ModelProvider.class);
        modelProvider = modelProviders.iterator().next();

        vector = modelProvider.getVectorFactory().createScalarVector("testVector", 1,2,3,4,5);
    }

    @Test
    @DisplayName("1. A FunctionProvider is initialized")
    @Order(1)
    void test1FunctionProviderExists() {
        assertNotNull(functionProvider);
    }

    @Test
    @DisplayName("2. A Statistics factory is initialized from FunctionProvider")
    @Order(2)
    void test2StatisticsFactoryInitialized() {
        assertNotNull(functionProvider.getStatistics());
    }

    @Test
    @DisplayName("3. A scalar vector is initialized")
    @Order(3)
    void test3VectorValues() {
        assertNotNull(vector);
    }

    @Test
    @DisplayName("4. The sum function is initialized and value computed")
    @Order(4)
    void compute() {
        ScalarFunction sumFunction = functionProvider.getStatistics().sum(vector);
        Scalar value = sumFunction.compute();
        assertEquals(15, value.asInt());
    }
}