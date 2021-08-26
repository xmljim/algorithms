package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.Vector;
import io.xmljim.algorithms.model.util.Scalar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VectorFactory testing")
class VectorFactoryImplTest extends ImplementationTestBase {


    @Test
    @DisplayName("Create a generic vector")
    void createVector() {
        String[] testData = {"Boston", "New York", "Philadelphia", "Baltimore", "Washington DC", "Atlanta", "Dallas", "Denver", "Seattle", "Portland", "San Francisco", "Los Angeles", "San Diego", "Phoenix"};
        Vector<String> testVector = getModelProvider().getVectorFactory().createVector("cities", "x", testData);

        IntStream.range(0, testData.length).forEach(i -> assertEquals(testData[i], testVector.get(i)));
        assertEquals(testData.length, testVector.length());
        assertEquals("x", testVector.getVariable());
        assertEquals("cities", testVector.getName());
    }

    @Test
    @DisplayName("Create a scalar vector")
    void createScalarVector() {
        Number[] testData = {1.73, 3.14, 2.78, 1.62, 1.414};
        ScalarVector testVector = getModelProvider().getVectorFactory().createScalarVector("numbers", testData);

        double[] array  = testVector.toDoubleArray();
        Number[] numberArray = testVector.toScalarArray();
        assertEquals(testData[2].doubleValue(), numberArray[2].doubleValue());
        assertEquals(testData[1].doubleValue(), testVector.get(1).asDouble(), 0E-4);
        assertEquals(testData[0], array[0]);
        List<Scalar> sorted = testVector.sorted().collect(Collectors.toList());
        assertEquals(1.414, sorted.get(0).asDouble());
    }
}