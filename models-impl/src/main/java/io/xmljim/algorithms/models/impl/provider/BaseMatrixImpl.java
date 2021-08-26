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

package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.Matrix;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.provider.ModelProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class BaseMatrixImpl extends AbstractMatrix implements Matrix {
    private final Map<String, Integer> columnNameMap = new HashMap<>();


    public BaseMatrixImpl(final String name, final ModelProvider modelProvider, final Stream<ScalarVector> rows) {
        super(name, modelProvider, rows);
    }

    public BaseMatrixImpl(final String name, final ModelProvider modelProvider, final Stream<ScalarVector> rows, String...columnHeaders) {
        this(name, modelProvider, rows);
        IntStream.range(0, columnHeaders.length).forEach(i -> {
            columnNameMap.put(columnHeaders[i], i);
        });
    }

    public BaseMatrixImpl(final String name, final ModelProvider modelProvider, final ScalarVector... columns) {
        super(name, modelProvider, columns);
        IntStream.range(0, columns.length).forEach(i -> {
            columnNameMap.put(columns[i].getName(), i);
        });
    }

    public BaseMatrixImpl(final String name, final ModelProvider modelProvider, final Number[][] numberArray) {
        super(name, modelProvider, numberArray);
    }

    public BaseMatrixImpl(final String name, final ModelProvider modelProvider, final Number[][] numberArray, String...columnHeaders) {
        super(name, modelProvider, numberArray);
        IntStream.range(0, columnHeaders.length).forEach(i -> {
            columnNameMap.put(columnHeaders[i], i);
        });
    }

    @Override
    public ScalarVector getColumn(final String name) {
        return getColumn(columnNameMap.get(name));
    }

    @Override
    public String[] getColumnNames() {
        return columnNameMap.keySet().toArray(String[]::new);
    }
}
