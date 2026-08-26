# Developer Guide — Writing Scenarios, Debugging, and Execution Flow

This file is for **understanding and working inside** the framework day to
day. For first-time installation/setup (JDK, Appium, APK, locators) see
[README.md](README.md) instead — this guide assumes that's already done.

---

## 1. Execution flow: where it starts, and how it jumps between files

Every run follows the exact same path. Knowing this path is the fastest
way to know *which file to open* when something breaks.

```
STEP 0  You run:  mvn test        (or right-click testng.xml → Run in IntelliJ)
        │
        ▼
STEP 1  testng.xml  (or testng-smoke.xml / testng-regression.xml)
        <class name="com.ecommerce.mobile.runner.TestRunner"/>
        <parameter name="cucumber.filter.tags" value="@smoke"/>   ← only in the *-smoke/-regression files
        → tells TestNG: "go run this one class"; if a cucumber.filter.tags
          <parameter> is present, Cucumber-TestNG reads it directly (see 3.2)
        │
        ▼
STEP 2  src/test/java/.../runner/TestRunner.java
        @CucumberOptions(
            features = "src/test/resources/features",   ← where the .feature files are
            glue     = {"...hooks", "...stepdefinitions"} ← where the Java code behind them is
        )
        extends AbstractTestNGCucumberTests
        → this is the REAL entry point. Cucumber now takes over, applies any
          tag filter, and builds a list of every MATCHING scenario.
        │
        ▼
STEP 3  For the FIRST scenario only:
        src/test/java/.../hooks/Hooks.java  →  @BeforeAll  startAppiumServer()
        → starts the Appium server once for the whole run
        │
        ▼
STEP 4  For EVERY scenario, in this order:
        │
        ├─ Hooks.java  @Before  launchApp(scenario)
        │     → src/main/java/.../config/CapabilityManager.java   (reads config.properties)
        │     → src/main/java/.../config/DriverManager.java        (opens the Appium session)
        │     → the app launches fresh on the device/emulator
        │
        ├─ Cucumber matches each line of the scenario to a Java method:
        │     src/test/resources/features/0X_xxxx.feature   (the English)
        │            ↓ text match
        │     src/test/java/.../stepdefinitions/XxxxSteps.java   (the Java behind it)
        │            ↓ calls
        │     src/main/java/.../pages/XxxxPage.java   (the actual tap/type/read actions)
        │            ↓ calls
        │     Appium Java client  →  Appium server  →  the device/emulator
        │
        ├─ Hooks.java  @After  tearDown(scenario)
        │     → if the scenario FAILED:
        │          src/main/java/.../utils/ScreenshotUtil.java → saves PNG to /screenshots
        │          screenshot is also attached to the HTML report
        │     → if it failed, listeners/RetryAnalyzer.java tells TestNG to run
        │       the WHOLE scenario again from STEP 4 (once, not more)
        │     → the app/session is closed either way
        │
        ▼
STEP 5  After the LAST scenario:
        Hooks.java  @AfterAll  stopAppiumServer()
        │
        ▼
STEP 6  ExtentCucumberAdapter (the "plugin" in TestRunner's @CucumberOptions)
        writes  reports/GajabMobileAutomationReport.html
        Log4j2 has been writing to  logs/mobile-automation.log  the whole time.
        │
        ▼
STEP 7  Once the WHOLE TestNG suite has finished (all scenarios, all retries):
        src/test/java/.../listeners/EmailReportListener.java   (a TestNG IReporter,
        registered via @Listeners on TestRunner.java) fires ONCE with the
        final pass/fail/skip counts
        → src/main/java/.../utils/MailUtil.java builds the HTML email
          (subject, pass/fail chart, failed-scenario list, report attached)
          and sends it via config.properties' mail.* settings +
          the MAIL_FROM_PASSWORD environment variable
        → if mail settings/credentials are missing, this step just logs a
          warning and does nothing else - it never fails the build
```

**The single most useful sentence to remember:**
`.feature` file text → matched by `stepdefinitions/*.java` → which calls
`pages/*.java` → which talks to the device. Nothing skips a layer.

---

## 2. How to add a brand-new scenario

Say you want to add: *"Show an error when searching for a product that
does not exist."* Here's the exact sequence:

### Step 1 — Write the English first (the `.feature` file)
Open `src/test/resources/features/02_home_product_listing.feature` and add:
```gherkin
  @regression
  Scenario: Searching for a product that does not exist shows no results
    When I search for the product "NonExistentProductXYZ"
    Then no products should be found
```
Reuse existing step text where you can (`I search for the product "..."`
already exists) — only the new line (`Then no products should be found`)
needs a new step definition.

### Step 2 — Add the missing step definition
Open `src/test/java/.../stepdefinitions/HomeSteps.java` and add:
```java
@Then("no products should be found")
public void no_products_should_be_found() {
    Assert.assertEquals(homePage.getDisplayedProductCount(), 0,
            "Expected no products, but some were still shown.");
}
```
(`getDisplayedProductCount()` already exists on `HomePage` — check the
page class first, most actions you need already exist there.)

### Step 3 — Only if the page is missing something, add it to the Page Object
If you needed an action or locator that doesn't exist yet, that always
goes in `src/main/java/.../pages/XxxxPage.java` — **never** put a locator
or an Appium call directly inside a step definition. Step definitions
should only read like sentences: `homePage.doThing()`, never raw
`driver.findElement(...)`.

### Step 4 — Confirm it's wired up, without needing a real device
```
mvn test -Dcucumber.execution.dry-run=true
```
This prints every scenario and which Java method each line resolved to,
without touching Appium at all. If a line prints as `Undefined` or throws
an "ambiguous step definitions" error, fix that before doing anything else
— see the debugging section below.

### Step 5 — Run it for real
```
mvn test -Dcucumber.filter.tags="@regression"
```

---

## 3. Running scenarios (targeted, not always the full 18)

| Goal | Command |
|---|---|
| Run everything | `mvn test` |
| Run only the critical path | `mvn test -Dcucumber.filter.tags="@smoke"` |
| Run one feature area | `mvn test -Dcucumber.filter.tags="@cart"` |
| Run everything except checkout | `mvn test -Dcucumber.filter.tags="not @checkout"` |
| Combine | `mvn test -Dcucumber.filter.tags="@regression and @cart"` |

**Isolating a single scenario while you're actively writing it:** temporarily
tag it `@focus` and run `mvn test -Dcucumber.filter.tags="@focus"`. Remove
the tag once it's working — don't leave `@focus` tags committed.

**In IntelliJ:** right-click the `.feature` file itself → "Run Feature" runs
just that file; there's also a green ▶ gutter icon next to each individual
`Scenario:` line to run just that one scenario.

### 3.1 Running a tag straight from testng.xml — no `-D` flags

Two extra suite files exist so a tag-scoped run doesn't need any
command-line flag at all — just right-click and Run:

| File | Filters to |
|---|---|
| `testng.xml` | nothing — runs all 18 |
| `testng-smoke.xml` | `@smoke` (10 scenarios) |
| `testng-regression.xml` | `@regression` (14 scenarios) |

Each one is a normal suite file plus a single extra line:
```xml
<test name="Smoke Tests">
    <parameter name="cucumber.filter.tags" value="@smoke"/>
    <classes>
        <class name="com.ecommerce.mobile.runner.TestRunner"/>
    </classes>
</test>
```

**Why this works (worth understanding, not just copying):**
`AbstractTestNGCucumberTests.setUpClass()` — the `@BeforeClass` method
Cucumber-TestNG puts on every runner, including ours — builds its
`RuntimeOptions` by layering, in order: the `cucumber.properties` file →
the `@CucumberOptions` annotation on `TestRunner` → **whatever
`XmlTest.getParameter(...)` returns for this `<test>`** → environment
variables → JVM system properties (highest priority, confirmed by our own
`-Dcucumber.filter.tags=...` usage elsewhere in this guide). That third
layer is exactly `<parameter>` values from testng.xml. This was verified
directly (decompiled bytecode showed the lambda is literally
`xmlTest::getParameter`), then confirmed empirically — running
`testng-smoke.xml` produces exactly the 10 `@smoke` scenarios, no more,
no less.

**To add a new tag-scoped suite** (e.g. one for `@checkout`): copy
`testng-smoke.xml`, rename it, change the `value="@smoke"` to
`value="@checkout"`. That's the entire recipe — no Java changes, and it's
the same officially-supported mechanism, not a workaround.

**From the command line**, the same suite files work via:
```
mvn test -DsuiteXmlFile=testng-smoke.xml
mvn test -DsuiteXmlFile=testng-regression.xml
```
(`pom.xml` defines `suiteXmlFile` as a property defaulting to `testng.xml`,
so plain `mvn test` is unaffected.)

### 3.2 Every execution mode is supported, but not identically
The framework runs the same way whether triggered by `mvn test`,
`testng.xml`, `TestRunner.java`, a CI pipeline, or IntelliJ's per-scenario
▶ gutter icon. The one thing to know: the gutter icon calls Cucumber's own
CLI runner directly and skips TestNG entirely, so it won't exercise
TestNG-provided features (retry, the summary email). Everything else
(hooks, screenshots) behaves identically.

| How you run it | Retry-on-failure | Summary email |
|---|---|---|
| `mvn test` / `mvn test -Dcucumber.filter.tags=...` | ✅ | ✅ |
| `mvn test -DsuiteXmlFile=testng-smoke.xml` | ✅ | ✅ |
| IntelliJ → any `testng*.xml` or `TestRunner.java` → Run/Debug | ✅ | ✅ |
| IntelliJ → ▶ gutter icon on one scenario/feature | ❌ | ❌ |
| CI pipeline | ✅ | ✅ |

See README.md section 6 for the full breakdown including screenshots.

---

## 4. Debugging

### 4.1 Dry-run first, always
Before blaming the device/Appium for a failure, rule out a wiring problem:
```
mvn test -Dcucumber.execution.dry-run=true
```
No device needed. This instantly tells you if a step is undefined,
ambiguous (matches two methods), or just fine.

### 4.2 Setting breakpoints (IntelliJ)
Since everything is plain Java, normal breakpoints work:
1. Open the relevant `stepdefinitions/*.java` or `pages/*.java` file.
2. Click in the gutter next to the line to set a breakpoint.
3. Right-click `testng.xml` (or the runner) → **Debug**, not Run.
4. Execution pauses at your breakpoint with the live Appium session still
   open on the device — you can inspect `driver`, hover over WebElements,
   step line-by-line, etc.

### 4.3 Reading the evidence after a run
| Question | Where to look |
|---|---|
| "What actually happened, in order?" | `logs/mobile-automation.log` — every hook, step, and error is logged with a timestamp. |
| "What did the screen look like when it failed?" | `screenshots/mobile/<scenario-name>_<timestamp>.png` |
| "Give me the whole picture (pass/fail counts, screenshots inline, step timings)" | `reports/GajabMobileAutomationReport.html` — open it in a browser |

### 4.4 Common errors and what they actually mean

| Error | What it really means | Fix |
|---|---|---|
| `'app.path' is blank in config.properties` (or `appPackage`/`appActivity`) | You haven't filled in `config.properties` yet | Fill in the blank fields — see README section 5 |
| `session not created` | Appium server couldn't start, or a stale session is holding the port | Check nothing else is running on `appium.server.port`; check `adb devices` shows your device |
| `adb: no devices/emulators found` | Emulator not started, or real device not connected/authorized | Run `adb devices` — fix before running tests |
| `NoSuchElementException` | The locator in the relevant `pages/*.java` file doesn't match the real app | Re-inspect that element in Appium Inspector, update the `@AndroidFindBy` |
| `StaleElementReferenceException` | The screen changed (navigation/animation) between finding the element and using it | Usually means an action needs a short wait added in `BasePage`, or the step is acting before the new screen has loaded |
| `Undefined step` in dry-run | The exact wording in the `.feature` file doesn't match any `@Given/@When/@Then` text | Either fix a typo in the feature file, or add the missing step definition (see section 2) |
| `Multiple step definitions match` (ambiguous) | Two step definitions have the identical (or overlapping) text pattern | Reword one of them to be more specific |
| Scenario passes on retry but failed once | This is the retry mechanism (`RetryAnalyzer`) working as intended — one-off flakiness | Only worry if it fails **twice** in a row |
| No summary email, but the run completed | Either `mail.from`/`mail.smtp.host`/`mail.to` in `config.properties` or the `MAIL_FROM_PASSWORD` env var is unset, or you ran via the per-scenario gutter icon (see 3.2) | Check `logs/automation.log` for the exact `MailUtil` warning line naming what's missing |
| Email send fails with an auth error (Gmail) | Using the real account password instead of an App Password | Create an App Password (Google Account → Security → App passwords) and use that as `MAIL_FROM_PASSWORD` |

---

## 5. Quick reference — "I want to change X, where do I go?"

| I want to... | Edit this |
|---|---|
| Change the wording/steps of a test case | `src/test/resources/features/*.feature` |
| Change what a step actually does | `src/test/java/.../stepdefinitions/*.java` |
| Change how a screen is interacted with, or fix a locator | `src/main/java/.../pages/*.java` |
| Change device, APK, or timeout settings | `src/test/resources/config.properties` |
| Change what happens before/after every scenario | `src/test/java/.../hooks/Hooks.java` |
| Change the retry count | `src/test/java/.../listeners/RetryAnalyzer.java` |
| Change which feature files/step packages Cucumber loads | `src/test/java/.../runner/TestRunner.java` |
| Change report look, screenshot folder, or log format | `extent.properties`, `extent-config.xml`, `log4j2.xml` |
| Change the summary email's content/subject/chart | `src/main/java/.../utils/MailUtil.java` |
| Change which counts feed the email (pass/fail/skip aggregation) | `src/test/java/.../listeners/EmailReportListener.java` |
| Change mail server/recipient settings | `src/test/resources/config.properties` (`mail.*` keys) — password stays in the `MAIL_FROM_PASSWORD` env var, never here |
| Change the CI pipeline | `.github/workflows/mobile-regression.yml` |
| Add a new tag-scoped suite runnable straight from an XML file | copy `testng-smoke.xml`, rename it, change its `cucumber.filter.tags` value (see 3.1) |
