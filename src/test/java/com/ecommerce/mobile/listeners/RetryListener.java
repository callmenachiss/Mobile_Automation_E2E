package com.ecommerce.mobile.listeners;

import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * TestNG's retry mechanism (see RetryAnalyzer) records a scenario's
 * failed attempt as SKIPPED once a later retry of that same scenario
 * succeeds - so a scenario that fails once then passes on retry is
 * reported as "1 skipped, 1 passed" instead of a clean "1 passed", even
 * though the scenario itself ultimately succeeded. This is standard
 * TestNG behaviour (org.testng.internal.invokers.TestInvoker registers
 * the pre-retry attempt via registerSkippedTestResult), not a bug in
 * RetryAnalyzer.
 *
 * Once the suite finishes, this drops any skipped result that was later
 * superseded by a passed or failed result for the same scenario, so
 * reports and summaries reflect only the final outcome per scenario.
 * Web has its own independent copy (com.ecommerce.web.listeners.RetryListener)
 * so a web run never depends on mobile's test classpath.
 */
public class RetryListener implements ITestListener {

    @Override
    public void onFinish(ITestContext context) {
        IResultMap skippedTests = context.getSkippedTests();

        Set<ITestResult> finalResults = new HashSet<>(context.getPassedTests().getAllResults());
        finalResults.addAll(context.getFailedTests().getAllResults());

        for (ITestResult skippedResult : new HashSet<>(skippedTests.getAllResults())) {
            for (ITestResult finalResult : finalResults) {
                if (sameInvocation(skippedResult, finalResult)) {
                    skippedTests.removeResult(skippedResult);
                    break;
                }
            }
        }
    }

    private boolean sameInvocation(ITestResult a, ITestResult b) {
        ITestNGMethod methodA = a.getMethod();
        ITestNGMethod methodB = b.getMethod();
        return methodA.getRealClass().equals(methodB.getRealClass())
                && methodA.getMethodName().equals(methodB.getMethodName())
                && Arrays.deepEquals(a.getParameters(), b.getParameters());
    }
}
