package com.ecommerce.mobile.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * When a scenario fails, TestNG asks this class "should I run it again?".
 * We say yes exactly once (MAX_RETRY_COUNT = 1) - this covers flaky
 * failures (a slow device, a slow network call) without hiding a real,
 * consistently-failing bug: if it fails twice, it is reported as failed.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger LOGGER = LogManager.getLogger(RetryAnalyzer.class);
    private static final int MAX_RETRY_COUNT = 0;
    private int retryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            LOGGER.warn("Scenario failed. Retrying (attempt {} of {})...", retryCount, MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
