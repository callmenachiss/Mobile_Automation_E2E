package com.ecommerce.mobile.listeners;

import com.ecommerce.mobile.utils.MailUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.IReporter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fires exactly once, after the ENTIRE suite has finished (including any
 * retries), with the final pass/fail/skip counts. That single "run is
 * done" moment is when the summary email goes out - see MailUtil.
 *
 * Registered via @Listeners(EmailReportListener.class) on TestRunner, so
 * it fires no matter how TestRunner is executed (mvn test, testng.xml,
 * or IntelliJ's right-click Run/Debug on the class or the suite).
 */
public class EmailReportListener implements IReporter {

    private static final Logger LOGGER = LogManager.getLogger(EmailReportListener.class);

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        int passed = 0;
        int failed = 0;
        int skipped = 0;
        List<String> failedScenarioNames = new ArrayList<>();

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> results = suite.getResults();
            for (ISuiteResult suiteResult : results.values()) {
                ITestContext context = suiteResult.getTestContext();
                passed += context.getPassedTests().size();
                failed += context.getFailedTests().size();
                skipped += context.getSkippedTests().size();

                for (ITestResult result : context.getFailedTests().getAllResults()) {
                    failedScenarioNames.add(scenarioName(result));
                }
            }
        }

        LOGGER.info("Suite finished: {} passed, {} failed, {} skipped.", passed, failed, skipped);
        MailUtil.sendRunSummary(passed, failed, skipped, failedScenarioNames);
    }

    private String scenarioName(ITestResult result) {
        Object[] params = result.getParameters();
        if (params.length > 0 && params[0] != null) {
            // Cucumber-TestNG's PickleWrapper.toString() returns the scenario's name.
            return params[0].toString();
        }
        return result.getMethod().getMethodName();
    }
}
