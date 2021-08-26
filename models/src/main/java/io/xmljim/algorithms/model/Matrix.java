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

package io.xmljim.algorithms.model;

import io.xmljim.algorithms.model.util.Scalar;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Matrix extends NamedEntity {

    default Scalar get(int row, int column) {
        ScalarVector rowVector = getRow(row);
        return rowVector.get(column);
    }

    default Stream<Scalar> getRowStream(int row) {
        return getRow(row).stream();
    }

    default Stream<Scalar> getColumnStream(int column) {
        return getColumn(column).stream();
    }

    ScalarVector getRow(int row);

    ScalarVector getColumn(int column);

    ScalarVector getColumn(String name);

    String[] getColumnNames();

    int getRowCount();

    int getColumnCount();

    default Double[][] toDoubleArray() {
        Double[][] data = new Double[getRowCount()][getColumnCount()];

        IntStream.range(0, getRowCount()).forEach(row -> {
            IntStream.range(0, getColumnCount()).forEach(column -> {
                try {
                    data[row][column] = get(row, column).asDouble();
                } catch (Exception e) {
                    data[row][column] = null;
                }
            });
        });

        return data;
    }


}
