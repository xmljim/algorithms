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
import io.xmljim.algorithms.functions.financial.RetirementModel;
import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.provider.ModelProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RetirementModelImplTest {
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
    void testSolve() {
        double currentSalary = 52_095;
        int currentAge = 32;
        int retirementAge = 65;
        double currentRetirementBalance = 26_932;
        double colaPct = 0.03;
        double selfContributionPct = 0.1;
        double employerContributionPct = 0.03;
        double weightedGrowthPct = 0.09203168503741728;
        double postRetirementInterest = 0.04;
        double inflationRate = 0.029;
        int duration = 0;
        double annualIncomePct = 0.0;
        PaymentFrequency distributionFrequency = PaymentFrequency.MONTHLY;
        PaymentFrequency contributionFrequency = PaymentFrequency.SEMI_MONTHLY;

        /*
        {
    "age": 32,
    "retirementAge": 65,
    "currentSalary": 52095.0,
    "colaPct": 0.03,
    "currentRetirementBalance": 26932,
    "selfContributionPct": 0.10,
    "employerContributionPct": 0.03,
    "investmentStyle": 0.85,
    "contributionFrequency": "SEMI_MONTHLY",
    "postRetirementInterestRate": 0.04,
    "distributionFrequency": "MONTHLY",
    "retirementDuration": 0,
    "incomeReplacementPct": 0.00,
    "annualizedDistribution": 0.0
}
         */

        RetirementModel retirementModel =
                functionProvider.getFinancial().retirementModel(currentAge, retirementAge, currentSalary, currentRetirementBalance, selfContributionPct, employerContributionPct,
                        colaPct, weightedGrowthPct, contributionFrequency, postRetirementInterest, distributionFrequency, inflationRate, duration, annualIncomePct);

        retirementModel.solve();

        assertNotNull(retirementModel.getContributionModel());
        assertNotNull(retirementModel.getDistributionModel());
        assertEquals(retirementModel.getRetirementTimeline().size(), retirementModel.getContributionModel().getContributionTimeline().size()
                + retirementModel.getDistributionModel().getDistributionSchedule().size());
        assertEquals(28, retirementModel.getDistributionModel().getDistributionYears().asInt());
        assertEquals(LocalDate.now().getYear() + 28 + (retirementAge - currentAge), retirementModel.getRetirementBalanceDepletionYear().asInt());
        assertNotEquals(0, retirementModel.getRetirementIncomePct().asDouble());


        AtomicInteger currentYear = new AtomicInteger(LocalDate.now().getYear());
        int lastYear = retirementModel.getRetirementBalanceDepletionYear().asInt();

        //assert that the entire timeline increments by year
        retirementModel.getRetirementTimeline().forEach(balance -> {
            assertEquals(currentYear.get(), balance.getYear());
            currentYear.getAndIncrement();
        });

        System.out.println(retirementModel.getRetirementIncomePct());

    }

}