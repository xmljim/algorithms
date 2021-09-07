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

import io.xmljim.algorithms.functions.financial.ContributionBalance;
import io.xmljim.algorithms.functions.financial.PaymentFrequency;
import io.xmljim.algorithms.functions.provider.FunctionProvider;
import io.xmljim.algorithms.model.Function;
import io.xmljim.algorithms.model.provider.ModelProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ServiceLoader;

@DisplayName("Calculate an estimated retirement balance and contributions")
class ContributionBalanceFunctionTest {

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
    @DisplayName("When the end year is the same as the current year, only interest is added")
    void computeSingleYear() {

        double salary = 25_000;
        double colaPct = 0.02;
        double currentRetirementBalance = 10_000;
        double selfContributionPct = 0.10;
        double emplContributionPct = 0.04;
        double weightedGrowth = 0.078;
        int currentYear = LocalDate.now().getYear();
        int endYear = 2021;
        PaymentFrequency contributionFrequency = PaymentFrequency.SEMI_MONTHLY;

        Function<ContributionBalance> contribution =
                functionProvider.getFinancial().contributionBalance(salary, colaPct, currentRetirementBalance, selfContributionPct, emplContributionPct, weightedGrowth,
                    contributionFrequency, currentYear, endYear);
        ContributionBalance retirementBalance = contribution.compute();


        assertEquals(endYear, retirementBalance.getYear());

    }

}