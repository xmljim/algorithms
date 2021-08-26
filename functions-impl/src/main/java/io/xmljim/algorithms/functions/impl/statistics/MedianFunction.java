
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

import io.xmljim.algorithms.functions.impl.AbstractScalarFunction;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.ScalarVectorParameter;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.List;
import java.util.stream.Collectors;

class MedianFunction extends AbstractScalarFunction {
    private Scalar result;

    public MedianFunction(ScalarVectorParameter scalarVectorParameter) {
        super(StatisticsFunctions.MEDIAN, scalarVectorParameter);
    }

    public MedianFunction(ScalarVectorParameter scalarVectorParameter, String variable) {
        super(StatisticsFunctions.MEDIAN, variable, scalarVectorParameter);
    }

    Scalar computeMedian() {
        double med;
        ScalarVector vector = getValue(0);
        List<Number> sorted = vector.sorted().collect(Collectors.toList());

        if (sorted.size() % 2 == 1) {
            int index = ((sorted.size() + 1) / 2) - 1;
            med = sorted.get(index).doubleValue();
        } else {
            int left = (sorted.size() / 2) - 1;
            int right = (sorted.size() / 2);

            med = (sorted.get(left).doubleValue() + sorted.get(right).doubleValue()) / 2;
        }
        return Scalar.of(med);
    }

    @Override
    public Scalar compute() {
        if (result == null) {
            result = computeMedian();
        }
        return result;
    }
}
