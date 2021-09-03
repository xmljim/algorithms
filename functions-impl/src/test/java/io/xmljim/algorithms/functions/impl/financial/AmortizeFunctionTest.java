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

package io.xmljim.algorithms.functions.impl.financial;

import io.xmljim.algorithms.functions.financial.PaymentFrequency;
import io.xmljim.algorithms.functions.financial.RetirementContributionModel;
import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.provider.ModelProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class AmortizeFunctionTest {
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
    void testCompute() {

        double balance = 1_000_000;
        double interest = 0.04;
        PaymentFrequency frequency = PaymentFrequency.MONTHLY;
        int duration = 30;

        ScalarFunction distributionFx = functionProvider.getFinancial().amortize(balance, interest, frequency, duration);

        assertEquals(4774.15, distributionFx.compute().asDouble(), 1E-2);
    }

    @Test
    void testWithRealValue() {
        int currentAge = 43;
        int retirementAge = 67;
        double currentSalary = 100_000;
        double selfContribPct = 0.1;
        double emplContribPct = 0.04;
        double currentBalance = 500_000;
        double colaPct = 0.02;
        double weightedGrowthPct = 0.084;

        RetirementContributionModel model =
                functionProvider.getFinancial().retirementContributionModel(currentAge, retirementAge, currentSalary, selfContribPct, emplContribPct,
                        currentBalance, colaPct, weightedGrowthPct);

        model.solve();

        double balance = model.getBalance().asDouble();
        double interest = 0.03;
        PaymentFrequency frequency = PaymentFrequency.MONTHLY;
        int duration = 30;

        ScalarFunction distributionFx = functionProvider.getFinancial().amortize(balance, interest, frequency, duration);

    }
}