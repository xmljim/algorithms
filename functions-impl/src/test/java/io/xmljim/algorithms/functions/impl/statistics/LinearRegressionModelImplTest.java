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

import io.xmljim.algorithms.functions.impl.provider.NameConstants;
import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.provider.ModelProvider;
import org.apache.commons.math3.distribution.TDistribution;
import org.ghotibeaun.json.JSONArray;
import org.ghotibeaun.json.JSONFactory;
import org.ghotibeaun.json.JSONObject;
import org.ghotibeaun.json.JSONValue;
import org.ghotibeaun.json.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class LinearRegressionModelImplTest {

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
    void solve() {

        ScalarVector vectorX = null;
        ScalarVector vectorY = null;
        try (InputStream inputStream = getClass().getResourceAsStream("/stockmarket.json")) {
            JSONFactory factory = JSONFactory.newFactory();
            JSONParser parser = factory.newParser();
            JSONObject jsonObject = parser.parse(inputStream).asJSONObject();
            long parsingEnd = System.currentTimeMillis();

            JSONArray xArray = jsonObject.getJSONArray("year");
            List<Number> listX = new ArrayList<>();
            for (JSONValue<?> value : xArray) {
                listX.add((Number)value.getValue());
            }

            JSONArray yArray = jsonObject.getJSONArray("netLogGainAdjusted");
            List<Number> listY = new ArrayList<>();
            for (JSONValue<?> value : yArray) {
                listY.add((Number)value.getValue());
            }

            vectorX = modelProvider.getVectorFactory().createScalarVector(NameConstants.VECTOR, NameConstants.X_VARIABLE, listX);
            vectorY = modelProvider.getVectorFactory().createScalarVector(NameConstants.VECTOR, NameConstants.Y_VARIABLE, listY);
        } catch (Exception e) {
            fail(e);

        }

        LinearRegressionModel lrm = functionProvider.getStatistics().linearRegression(vectorX, vectorY);
        lrm.solve();

        lrm.coefficients().forEach(c -> {
            System.out.println(c.toString());
        });

        System.out.println(Math.pow(10, lrm.getSlope().asDouble()));
    }
}