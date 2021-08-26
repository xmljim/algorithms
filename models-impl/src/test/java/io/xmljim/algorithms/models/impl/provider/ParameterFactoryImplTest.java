package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.*;
import io.xmljim.algorithms.model.util.Scalar;
import io.xmljim.algorithms.model.util.SerialTemporal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test ParameterFactory")
class ParameterFactoryImplTest extends ImplementationTestBase {

    @Test
    @DisplayName("Create a Generic Parameter")
    void testCreateParameterGeneric() {
        Parameter<LocalDate> localDateParameter =
                getModelProvider().getParameterFactory().createParameter("currentDate", "currentDate", LocalDate.now());

        assertEquals(LocalDate.now(), localDateParameter.getValue());
        assertEquals(ParameterTypes.GENERIC, localDateParameter.getParameterType());

        Parameter<?> wildcardParam =
                getModelProvider().getParameterFactory().createParameter("wildcard", "WILDCARD");

        String wildcardValue = (String) wildcardParam.getValue();
        assertEquals("WILDCARD", wildcardValue);
        assertEquals(ParameterTypes.GENERIC, wildcardParam.getParameterType());

    }

    @Test
    @DisplayName("Create a Vector Parameter")
    void testCreateParameterVector() {
        Vector<String> stringVector =
                getModelProvider().getVectorFactory().createVector("stringVector", "a", "a","b","c","d","e","f","g");

        Parameter<Vector<String>> stringVectorParameter =
                getModelProvider().getParameterFactory().createParameter(stringVector);

        assertEquals(stringVector.getName(), stringVectorParameter.getName());
        assertEquals(ParameterTypes.VECTOR, stringVectorParameter.getParameterType());
    }

    @Test
    @DisplayName("Create a Scalar Parameter")
    void testCreateParameterNumber() {

        ScalarParameter scalarParameter1 =
                getModelProvider().getParameterFactory().createParameter("p1", 100);
        ScalarParameter scalarParameterBool =
                getModelProvider().getParameterFactory().createParameter("p2", Scalar.of(true));
        ScalarParameter scalarParameterDate =
                getModelProvider().getParameterFactory().createParameter("p3", Scalar.of(LocalDate.now()));

        assertEquals(100, scalarParameter1.getValue().asInt());
        assertEquals(ParameterTypes.SCALAR, scalarParameter1.getParameterType());
        assertEquals(0.0, scalarParameterBool.getValue().asDouble());
        assertEquals(ParameterTypes.SCALAR, scalarParameterBool.getParameterType());
        assertEquals(SerialTemporal.of(LocalDate.now()).intValue(), scalarParameterDate.getValue().intValue());
        assertEquals(ParameterTypes.SCALAR, scalarParameterDate.getParameterType());

    }

    @Test
    @DisplayName("Create ScalarVector Parameter")
    void testCreateParameterScalarVector() {
        ScalarVector vector = getModelProvider().getVectorFactory().createScalarVector("testVector", 1,2,3,4,5,6,7,8,9,0);
        ScalarVectorParameter vectorParameter = getModelProvider().getParameterFactory().createParameter(vector);
        assertEquals(ParameterTypes.SCALAR_VECTOR, vectorParameter.getParameterType());
    }
}