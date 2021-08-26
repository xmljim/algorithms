package io.xmljim.algorithms.functions.impl.statistics;

import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.provider.ModelProvider;
import org.junit.jupiter.api.*;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Create a function to calculate the mean")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MeanFunctionTest {
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
    @DisplayName("4. A mean function is initialized")
    @Order(4)
    void test4FunctionCreation() {
        meanFunction = functionProvider.getStatistics().mean(vector);
        assertNotNull(meanFunction);
        assertEquals(StatisticsFunctions.MEAN.getName(), meanFunction.getName());
    }

    @Test
    @DisplayName("5. The mean value evaluates to 3")
    @Order(5)
    void testMeanValue() {
        meanFunction = functionProvider.getStatistics().mean(vector);
        assertEquals(3.0, meanFunction.compute().asDouble());
    }


}