package com.ecommerce.mobile.utils;

import com.ecommerce.core.config.ConfigReader;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Sends one summary email after the whole test run finishes: subject,
 * a pass/fail/skip breakdown with a simple HTML bar chart, the list of
 * failed scenarios, and a link to (plus attachment of) the HTML report.
 *
 * Never throws - a broken mail server must not fail the test run. Any
 * problem is logged and swallowed.
 */
public class MailUtil {

    private static final Logger LOGGER = LogManager.getLogger(MailUtil.class);
    private static final String REPORT_FILE = "reports/EcommerceMobileAutomationReport.html";

    public static void sendRunSummary(int passed, int failed, int skipped, List<String> failedScenarioNames) {
        if (!ConfigReader.getBoolean("mail.enabled")) {
            LOGGER.info("Email notification skipped (mail.enabled=false in config.properties).");
            return;
        }

        String host = ConfigReader.get("mail.smtp.host", "");
        String from = ConfigReader.get("mail.from", "");
        String to = ConfigReader.get("mail.to", "");
        String password = System.getenv("MAIL_FROM_PASSWORD");

        if (host.isEmpty() || from.isEmpty() || to.isEmpty() || password == null || password.isEmpty()) {
            LOGGER.warn("Email notification skipped - fill in mail.smtp.host / mail.from / mail.to in "
                    + "config.properties, and set the MAIL_FROM_PASSWORD environment variable. "
                    + "See README.md -> \"Email notifications\".");
            return;
        }

        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
            email.setSmtpPort(ConfigReader.getInt("mail.smtp.port"));
            email.setAuthenticator(new DefaultAuthenticator(from, password));
            email.setStartTLSEnabled(ConfigReader.getBoolean("mail.smtp.starttls"));
            email.setFrom(from);
            email.addTo(to);

            int total = passed + failed + skipped;
            String status = failed > 0 ? "FAILED" : "PASSED";
            email.setSubject(ConfigReader.get("mail.subject.prefix", "Ecommerce Mobile Automation")
                    + " - " + status + " (" + passed + "/" + total + " passed) - "
                    + new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new Date()));

            email.setHtmlMsg(buildHtmlBody(passed, failed, skipped, failedScenarioNames));
            email.setTextMsg("Ecommerce Mobile Automation run finished: " + passed + " passed, "
                    + failed + " failed, " + skipped + " skipped. Open the HTML report for full details.");

            File reportFile = new File(REPORT_FILE);
            if (reportFile.exists()) {
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(reportFile.getAbsolutePath());
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("Full ExtentReports HTML report");
                attachment.setName(reportFile.getName());
                email.attach(attachment);
            } else {
                LOGGER.warn("Report file not found at {} - sending the summary email without an attachment.",
                        reportFile.getAbsolutePath());
            }

            email.send();
            LOGGER.info("Run summary email sent to {}", to);
        } catch (EmailException e) {
            LOGGER.error("Could not send the run summary email. Check mail.* settings in config.properties "
                    + "and the MAIL_FROM_PASSWORD environment variable.", e);
        }
    }

    private static String buildHtmlBody(int passed, int failed, int skipped, List<String> failedScenarioNames) {
        int total = Math.max(passed + failed + skipped, 1);
        int passPct = passed * 100 / total;
        int failPct = failed * 100 / total;
        int skipPct = Math.max(0, 100 - passPct - failPct);

        String reportLink = ConfigReader.get("mail.report.link", "");
        if (reportLink.isEmpty()) {
            File reportFile = new File(REPORT_FILE);
            reportLink = reportFile.exists() ? reportFile.toURI().toString() : "";
        }

        StringBuilder failedList = new StringBuilder();
        if (failedScenarioNames.isEmpty()) {
            failedList.append("<p style=\"color:#2e7d32;\">No failed scenarios.</p>");
        } else {
            failedList.append("<ul>");
            for (String name : failedScenarioNames) {
                failedList.append("<li>").append(escape(name)).append("</li>");
            }
            failedList.append("</ul>");
        }

        return "<html><body style=\"font-family:Arial,sans-serif;color:#222;\">"
                + "<h2>Ecommerce Mobile Automation - Run Summary</h2>"
                + "<table cellpadding=\"6\"><tr>"
                + "<td><b>Total</b></td><td><b>Passed</b></td><td><b>Failed</b></td><td><b>Skipped</b></td></tr>"
                + "<tr><td>" + total + "</td>"
                + "<td style=\"color:#2e7d32;\">" + passed + "</td>"
                + "<td style=\"color:#c62828;\">" + failed + "</td>"
                + "<td style=\"color:#9e9e9e;\">" + skipped + "</td></tr></table>"
                + "<p>Pass / Fail / Skip chart:</p>"
                + "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;\"><tr>"
                + bar(passPct, "#2e7d32") + bar(failPct, "#c62828") + bar(skipPct, "#9e9e9e")
                + "</tr></table>"
                + "<p>"
                + legendDot("#2e7d32") + " Passed (" + passPct + "%)&nbsp;&nbsp;"
                + legendDot("#c62828") + " Failed (" + failPct + "%)&nbsp;&nbsp;"
                + legendDot("#9e9e9e") + " Skipped (" + skipPct + "%)"
                + "</p>"
                + "<h3>Failed scenarios</h3>"
                + failedList
                + (reportLink.isEmpty() ? "" : "<p><a href=\"" + reportLink + "\">Open the full HTML report</a></p>")
                + "<p style=\"color:#777;font-size:12px;\">"
                + "Generated automatically by the Ecommerce Mobile Automation Framework. "
                + "The full report is also attached to this email."
                + "</p></body></html>";
    }

    private static String bar(int percent, String color) {
        if (percent <= 0) {
            return "";
        }
        return "<td style=\"background:" + color + ";width:" + percent + "%;height:24px;\"></td>";
    }

    private static String legendDot(String color) {
        return "<span style=\"display:inline-block;width:10px;height:10px;background:" + color + ";\"></span>";
    }

    private static String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
