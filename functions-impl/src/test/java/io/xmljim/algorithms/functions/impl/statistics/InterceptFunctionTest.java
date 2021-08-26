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
import org.ghotibeaun.json.JSONArray;
import org.ghotibeaun.json.JSONFactory;
import org.ghotibeaun.json.JSONObject;
import org.ghotibeaun.json.JSONValue;
import org.ghotibeaun.json.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class InterceptFunctionTest {

    private static FunctionProvider functionProvider;
    private static ModelProvider modelProvider;

    @BeforeAll
    static void buildProviders() {
        Iterable<FunctionProvider> functionProviders = ServiceLoader.load(FunctionProvider.class);
        functionProvider = functionProviders.iterator().next();

        Iterable<ModelProvider> modelProviders = ServiceLoader.load(ModelProvider.class);
        modelProvider = modelProviders.iterator().next();
    }

    @Test
    void compute() throws IOException {
        long parsingTime = 0;
        long listX = 0;
        long listY = 0;
        long vectX = 0;
        long vectY = 0;
        long compute = 0;

        ScalarVector yearVector;
        ScalarVector logAdjustedNetGainVector;

        long start = System.currentTimeMillis();
        try (InputStream inputStream = getClass().getResourceAsStream("/stockmarket.json")) {
            JSONFactory factory = JSONFactory.newFactory();
            JSONParser parser = factory.newParser();
            JSONObject jsonObject = parser.parse(inputStream).asJSONObject();
            long parsingEnd = System.currentTimeMillis();
            parsingTime = parsingEnd - start;

            long listXStart = System.currentTimeMillis();
            JSONArray yearArray = jsonObject.getJSONArray("year");
            List<Number> numbers = new ArrayList<>();
            for (JSONValue<?> value : yearArray) {
                numbers.add((Number)value.getValue());
            }
            long listXEnd = System.currentTimeMillis();
            listX = listXEnd - listXStart;

            long listYStart = System.currentTimeMillis();
            JSONArray logAdjustedArray = jsonObject.getJSONArray("netLogGainAdjusted");
            List<Number> logList = new ArrayList<>();
            for (JSONValue<?> value : logAdjustedArray) {
                logList.add((Number)value.getValue());
            }
            long listYEnd = System.currentTimeMillis();
            listY = listYEnd - listYStart;


            long vectXStart = System.currentTimeMillis();
            yearVector = modelProvider.getVectorFactory().createScalarVector("year", "x" , numbers);
            long vectXEnd = System.currentTimeMillis();
            vectX = vectXEnd - vectXStart;

            long vectYStart = System.currentTimeMillis();
            logAdjustedNetGainVector = modelProvider.getVectorFactory().createScalarVector("netLogGainAdjusted", "y", logList);
            long vectYEnd = System.currentTimeMillis();
            vectY = vectYEnd - vectXStart;

        }

        ScalarFunction meanX = functionProvider.getStatistics().mean(yearVector);
        ScalarFunction meanY = functionProvider.getStatistics().mean(logAdjustedNetGainVector);
        ScalarFunction varianceX = functionProvider.getStatistics().variance(yearVector, meanX, "x");
        ScalarFunction covariance = functionProvider.getStatistics().covariance(yearVector, logAdjustedNetGainVector, meanX, meanY);
        ScalarFunction slope = functionProvider.getStatistics().slope(varianceX, covariance);
        ScalarFunction intercept = functionProvider.getStatistics().intercept(meanX, meanY, slope);

        long computeStart = System.currentTimeMillis();
        Scalar interceptValue = intercept.compute();
        long computeEnd = System.currentTimeMillis();
        compute = computeEnd - computeStart;
        assertEquals(-64.01753, interceptValue.asDouble(), .00001);
        System.out.println(interceptValue);

        System.out.println("Parsing Time: " + parsingTime);
        System.out.println("List X Time: " + listX);
        System.out.println("List Y Time: " + listY);
        System.out.println("Vector X Time: " + vectX);
        System.out.println("Vector Y Time: " + vectY);
        System.out.println("Compute Time: " + compute);
    }




}