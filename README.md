# Gajab Automation Framework (Mobile + Web)

UI automation for the Gajab app, written in plain-English BDD scenarios so
that QA, developers **and** business/leadership can read what is being
tested without needing to read a line of code.

**Everything - mobile (Appium/Android) and web (Selenium) - lives in this
one project, `Mobile_Automation_E2E/`.** There is no separate framework or
repo per platform: mobile and web are two independent, side-by-side
subtrees (`com.ecommerce.mobile.*` and `com.ecommerce.web.*`) inside the
same Maven build, the same way `pages/` already has one class per screen.
Neither platform's code depends on the other's - see section 2 - but both
are built, run, and documented from this single project root. If you ever
find mobile or web automation code living outside this folder, that's
stale/out of place and should be moved in here.

This is a standard **Maven** project (`pom.xml` at the root, no Gradle
anywhere) and opens directly in **IntelliJ IDEA**: `File → Open` and point
it at this folder — IntelliJ detects the `pom.xml` and imports it as a
Maven project automatically.

---

## 1. Technology used, and why

| Technology | Role in this project |
|---|---|
| **Java** | Programming language everything is written in. |
| **Maven** | Builds the project and downloads all the libraries below. |
| **Appium** | Drives the app on an Android emulator/device (installs it, taps, types, reads screens). |
| **Selenium** | The underlying browser/app automation protocol that Appium is built on top of. |
| **Cucumber (BDD)** | Lets us describe test cases in plain English (`Given/When/Then`) in `.feature` files. |
| **TestNG** | Runs the tests and provides the "retry a failed test" hook. |
| **ExtentReports** | Turns the results into one readable HTML report with pass/fail counts and screenshots. |
| **Log4j2** | Writes a timestamped execution log for every run, to help debug failures. |

**Design pattern:** Page Object Model (POM). Every app screen has one Java
class (under `pages/`) that lists its buttons/fields and the actions you
can do on it. Step definitions call those actions. This keeps the English
scenarios simple and means that if the app's UI changes, you only update
one page class instead of every test.

---

## 2. Project structure

Mobile and web are **fully independent, parallel subtrees** - every layer
that exists for one exists for the other, under its own package, and
neither imports a class from the other's package. This is a deliberate
standard, not an accident: if you ever add a class that both platforms
would use identically, **duplicate it into both `mobile.*` and `web.*`
rather than factor out a shared "common" package** - a shared package is
still a cross-platform dependency, and the whole point is that a change to
web (or a broken web run) can never affect mobile, and vice versa. The one
thing genuinely shared is `config.properties` itself, and that's a data
file each platform's own `ConfigReader` reads independently, not code.

```
Mobile_Automation_E2E/                   # This folder is the whole project - one Maven build,
├── apps/                                # both platforms, nothing lives outside it.
│                                         # Put your .apk file here (mobile only)
├── screenshots/
│   ├── mobile/                          # Auto-created: screenshots of FAILED mobile scenarios
│   └── web/                             # Auto-created: screenshots of FAILED web scenarios
├── logs/
│   ├── mobile-automation.log            # Auto-created: every mobile run appends here
│   └── web-automation.log               # Auto-created: every web run appends here
├── test-output/                         # Auto-created: EVERY run's own timestamped Extent report
│   ├── MobileExtentReport <timestamp>/reports/GajabAutomationReport.html
│   └── WebExtentReport <timestamp>/reports/GajabAutomationReport.html
│                                        # (see section 7B - the report always lives under a fresh
│                                        #  timestamped folder here, never at a fixed reports/ path;
│                                        #  the folder name - Mobile vs Web - is what tells them apart)
├── pom.xml                              # ONE Maven build file for both platforms
├── testng.xml                           # Runs EVERY mobile scenario (the default mobile suite)
├── testng-smoke.xml                     # Mobile only: runs @smoke-tagged scenarios - click & Run
├── testng-regression.xml                # Mobile only: runs @regression-tagged scenarios
├── testng-web.xml                       # Runs the @web-tagged web suite
├── .github/workflows/                   # CI starter(s) - see section 9
├── src/
│   ├── main/java/com/ecommerce/
│   │   ├── mobile/
│   │   │   ├── config/                  # ConfigReader, CapabilityManager, DriverManager,
│   │   │   │                            # AppiumServerManager - reads config.properties,
│   │   │   │                            # starts Appium server, manages the driver
│   │   │   ├── pages/                   # One class per app screen (Page Object Model)
│   │   │   └── utils/                   # ScreenshotUtil (→ screenshots/mobile)
│   │   └── web/
│   │       ├── config/                  # ConfigReader, WebCapabilityManager, WebDriverManager -
│   │       │                            # web's own copies, mirroring mobile's config/ 1:1
│   │       ├── pages/                   # One class per web page (Page Object Model)
│   │       └── utils/                   # ScreenshotUtil (→ screenshots/web) - web's own copy
│   └── test/
│       ├── java/com/ecommerce/
│       │   ├── mobile/
│       │   │   ├── hooks/               # Before/after every scenario (launch app, screenshot on fail)
│       │   │   ├── stepdefinitions/     # Java code behind each English sentence in the .feature files
│       │   │   ├── runner/              # TestRunner - the Cucumber + TestNG entry point
│       │   │   └── listeners/           # RetryAnalyzer, RetryListener (retry + clean reporting)
│       │   └── web/
│       │       ├── hooks/               # WebHooks - web's own copy of Hooks
│       │       ├── stepdefinitions/     # Java code behind each web .feature sentence
│       │       ├── runner/              # WebTestRunner - web's own Cucumber + TestNG entry point
│       │       └── listeners/           # RetryAnalyzer, RetryListener - web's own copies
│       └── resources/
│           ├── features/
│           │   ├── mobile/              # Mobile's .feature files - scoped here, not the parent
│           │   │                        # features/ folder, so TestRunner's recursive scan never
│           │   │                        # picks up web/'s scenarios (they'd fail: mobile's glue
│           │   │                        # can't satisfy web step text)
│           │   └── web/                 # The web .feature files (@web-tagged scenarios)
│           ├── config.properties        # ALL settings for BOTH platforms live here
│           ├── log4j2.xml               # Logging config - routes to mobile- or web-automation.log
│           ├── extent.properties        # Report filename/basefolder for BOTH platforms - see 7B
│           └── extent-config.xml        # Report look & feel for BOTH platforms - see 7B
```

---

## 3. The app under test (6 pages, 18 scenarios)

The framework assumes these 6 screens. If your app's actual screens/flows
are named differently, the structure below is still valid — just rename
the page classes/feature files and adjust the steps to match.

| # | Page | Feature file | Scenarios |
|---|---|---|---|
| 1 | Login | `01_login.feature` | Login screen displays, valid login, invalid login shows error, **+ 1 starter scenario ("Login successful") left as a TODO for you to implement** |
| 2 | Home / Product Listing | `02_home_product_listing.feature` | Product list displays, search, sort by price, open a product |
| 3 | Product Details | `03_product_details.feature` | Details displayed correctly, add to cart, change quantity |
| 4 | Cart | `04_cart.feature` | Item visible in cart, update quantity, remove item |
| 5 | Checkout | `05_checkout.feature` | Full checkout (address + payment + place order), address is saved, checkout blocked when cart is empty |
| 6 | Order Confirmation | `06_order_confirmation.feature` | Success message and order number displayed |

**3 + 4 + 3 + 3 + 3 + 1 = 17 originally-scoped use cases, + 1 starter
scenario in `LoginSteps.java` you're filling in yourself = 18 total.**

These are written as sensible, typical ecommerce flows so the framework is
ready to run structurally. **You will very likely need to**: rename the
sample product `"Wireless Headphones"` used in the feature files to a real
product from your catalog, and update the test login credentials in
`LoginSteps.java`.

---

## 4. One-time software installation

### 4.1 Java & Maven
1. Install **JDK 11 or later**: https://adoptium.net
2. Install **Maven**: https://maven.apache.org/install.html
3. Verify:
   ```
   java -version
   mvn -version
   ```

### 4.2 Node.js & Appium server
Appium is a Node.js application.
1. Install **Node.js** (LTS): https://nodejs.org
2. Install Appium and the Android driver:
   ```
   npm install -g appium
   appium driver install uiautomator2
   ```
3. Verify:
   ```
   appium -v
   ```
   You do **not** need to manually start `appium` before running tests —
   this framework starts/stops the Appium server for you (see
   `appium.server.autostart` in `config.properties`). You can still start
   it yourself and set that flag to `false` if you prefer.

### 4.3 Android SDK & a device/emulator
1. Install **Android Studio**: https://developer.android.com/studio
   (this also installs the Android SDK and `adb`).
2. Either:
   - Create an emulator via Android Studio → Device Manager, **or**
   - Connect a real Android phone via USB with **Developer Options → USB
     debugging** enabled.
3. Verify the device is visible:
   ```
   adb devices
   ```
   You should see your emulator/device listed as `device` (not `offline`).

### 4.4 Appium Inspector (for finding element locators)
Download the desktop app: https://github.com/appium/appium-inspector/releases

### 4.5 IntelliJ IDEA
Any edition works. Open this project via `File → Open` and select this
folder — IntelliJ recognizes the `pom.xml` and imports it as a Maven
project automatically. Let it finish downloading dependencies before
running anything.

---

## 5. Setting up YOUR app

All of this lives in **one file**:
`src/test/resources/config.properties`. Every value that needs your real
device/app details is currently left **blank** and marked
`>>> TO BE FILLED IN <<<` — fill those in, no Java code changes needed.

### Step 0 — Real device details (`deviceName`, `udid`, `platformVersion`)
In `config.properties`:
```properties
deviceName=
udid=
platformVersion=
```
- Connect your real device via USB (with **Developer Options → USB
  debugging** enabled) or start your emulator, then run:
  ```
  adb devices -l
  ```
- The first column is the **udid** — copy it into `udid=`.
- Put a friendly name (e.g. the model name from the same command) into
  `deviceName=`.
- `platformVersion` (the Android OS version, e.g. `14`) can stay blank —
  Appium auto-detects it — or you can set it explicitly.
- If only **one** device/emulator is ever connected at a time, `udid` and
  `deviceName` can stay blank too; Appium will just use whatever is
  connected. `udid` becomes required once more than one is connected at
  once.

### Step 1 — Add the APK
Copy your `.apk` file into the `apps/` folder, then update
`src/test/resources/config.properties`:
```properties
app.path=apps/your-file-name.apk
```

### Step 2 — Find `appPackage` and `appActivity`
These tell Appium which app to launch. Easiest way:
1. Install the APK on your emulator/device manually (drag-and-drop onto
   the emulator, or `adb install apps/your-file-name.apk`).
2. Open the app, then run:
   ```
   adb shell dumpsys window | grep mCurrentFocus
   ```
   You'll see something like:
   ```
   mCurrentFocus=Window{... com.yourcompany.ecommerceapp/com.yourcompany.ecommerceapp.MainActivity}
   ```
   The part before `/` is `appPackage`, the part after is `appActivity`.
3. Update `config.properties`:
   ```properties
   appPackage=com.yourcompany.ecommerceapp
   appActivity=com.yourcompany.ecommerceapp.MainActivity
   ```

(Alternatively, Appium Inspector's connection screen shows both values as
soon as it launches your app for the first time.)

### Step 3 — Find element locators with Appium Inspector
Every `@AndroidFindBy(id = "...")` in the `pages/` classes is currently a
**placeholder** — you must replace each one with a real locator from your
app.

1. Open **Appium Inspector**.
2. Start the Appium server (`appium` in a terminal) and start a new
   session in Inspector using the same capabilities as in
   `CapabilityManager.java` / `config.properties` (platformName Android,
   automationName UiAutomator2, your `app` path, `appPackage`,
   `appActivity`).
3. Once the app opens inside Inspector, click any element on screen (e.g.
   the email field on the Login screen).
4. The right-hand panel shows its `resource-id`, `accessibility id`, and
   `xpath`. Prefer `resource-id` (shown as `id` in our code) when
   available — it's the most stable locator.
5. Copy that value into the matching field in the relevant page class,
   e.g. in `LoginPage.java`:
   ```java
   @AndroidFindBy(id = "com.yourcompany.ecommerceapp:id/et_email")
   private WebElement emailInput;
   ```
6. Repeat for every element referenced in `pages/*.java`.

### Step 4 — Update test data
Open `src/test/java/com/ecommerce/mobile/stepdefinitions/LoginSteps.java`
and replace `VALID_EMAIL` / `VALID_PASSWORD` with a real test account.
Do the same for the sample product name `"Wireless Headphones"` used
throughout the `.feature` files — replace it with a real product from
your catalog (find-and-replace across the `features/mobile/` folder).

---

## 6. Running the tests

The framework supports every common way of triggering a run:

1. Make sure an emulator is running or a real device is connected
   (`adb devices`).
2. From the project root:
   ```
   mvn test
   ```
   This uses `testng.xml`, which runs every scenario in
   `src/test/resources/features/mobile/`.
3. To run just one tag (e.g. only smoke tests) via the command line, add:
   ```
   mvn test -Dcucumber.filter.tags="@smoke"
   ```
4. **In IntelliJ:** right-click `testng.xml` → "Run", or right-click
   `TestRunner.java` → "Run"/"Debug".
5. **In IntelliJ, one scenario at a time:** open any `.feature` file — a
   green ▶ appears in the gutter next to each `Scenario:` line (and next
   to `Feature:` to run the whole file). Click it to run just that one.
6. **In CI:** see section 9 below — the same `mvn test` command is what
   the pipeline runs.

### Running a tag straight from a `testng.xml` file (no command-line flags)
Two more suite files exist purely so a tag-scoped run can be started
**by clicking the XML file itself** — no `-D` flags needed at all:

| File | Runs |
|---|---|
| `testng.xml` | Everything (all 18 scenarios) — the default |
| `testng-smoke.xml` | Only `@smoke`-tagged scenarios (10) |
| `testng-regression.xml` | Only `@regression`-tagged scenarios (14) |

**In IntelliJ:** right-click `testng-smoke.xml` (or `testng-regression.xml`)
→ "Run" — that's it. **From the command line**, override which suite file
`mvn test` uses:
```
mvn test -DsuiteXmlFile=testng-smoke.xml
mvn test -DsuiteXmlFile=testng-regression.xml
```
(`mvn test` with no flag still uses `testng.xml` / everything, unchanged.)

This works because each of these files contains one line —
`<parameter name="cucumber.filter.tags" value="@smoke"/>` — and Cucumber's
TestNG integration reads that `<parameter>` natively; no Java code is
involved. Want a suite scoped to a different tag (e.g. `@checkout`)? Copy
one of these two files, rename it, and change that one line — that's the
entire recipe, verified working the same way.

### Execution modes: what each one actually supports
Retry-on-failure is provided by **TestNG** (`RetryAnalyzer`). The Cucumber
gutter ▶ icon bypasses TestNG entirely and talks to Cucumber's own CLI
runner, so it won't trigger that (the app-launch/screenshot hooks still
fire either way, since those are Cucumber-native, not TestNG).

| How you run it | Retry-on-failure | Screenshot on failure |
|---|---|---|
| `mvn test` | ✅ | ✅ |
| `mvn test -Dcucumber.filter.tags=...` | ✅ | ✅ |
| `mvn test -DsuiteXmlFile=testng-smoke.xml` (or `-regression.xml`) | ✅ | ✅ |
| IntelliJ → right-click `testng.xml` / `testng-smoke.xml` / `testng-regression.xml` → Run/Debug | ✅ | ✅ |
| IntelliJ → right-click `TestRunner.java` → Run/Debug | ✅ | ✅ |
| IntelliJ → ▶ gutter icon on one `Scenario:`/`Feature:` | ❌ | ✅ |
| CI pipeline (`mvn test ...`) | ✅ | ✅ |

If you're actively writing/debugging a single scenario, the gutter icon
is the fastest loop. Once it works, confirm it end-to-end with
`mvn test -Dcucumber.filter.tags="@focus"` (see `DEVELOPER_GUIDE.md`) so
retry gets exercised too.

### Where to look afterwards
This is the mobile suite's output - the web suite's is a separate,
identically-shaped set of files, see section 10:
| What | Where |
|---|---|
| HTML test report | `test-output/MobileExtentReport <timestamp>/reports/GajabAutomationReport.html` - see 7B, it's a new folder every run, not a fixed path |
| Execution log | `logs/mobile-automation.log` |
| Screenshots of failed scenarios | `screenshots/mobile/` |

---

## 7. How the retry mechanism works

`RetryAnalyzer.java` (under `listeners/`) tells TestNG: "if a scenario
fails, run it one more time before marking it as Failed." This is wired
into `TestRunner.java` via `retryAnalyzer = RetryAnalyzer.class`. Web has
its own identical copy wired into `WebTestRunner.java` the same way.

This catches one-off flakiness (a slow device, a slow network call)
without hiding a genuinely broken feature — if it fails twice in a row,
it is reported as failed.

**A TestNG quirk this framework specifically corrects for:** when a
scenario fails once and then passes on retry, TestNG's own retry
mechanism records that first failed attempt as **SKIPPED**, not discarded
— so without any further handling, a scenario that ultimately succeeded
would still show up in the report/summary as "1 skipped, 1 passed" instead
of a clean single pass. `RetryListener.java` (also under `listeners/`,
registered via `@Listeners` on both `TestRunner` and `WebTestRunner`)
removes that now-superseded skip once the suite finishes, so a scenario
that eventually passes is reported as exactly that — one pass, nothing
else. This is standard TestNG behavior being cleaned up, not a bug in
`RetryAnalyzer` itself.

## 7B. Where the Extent report actually lives, and why

`extent.properties` sets both `basefolder.name` and `basefolder.datetimepattern`, which together
mean the Extent adapter writes **every run's report into a brand-new timestamped folder** -
`test-output/MobileExtentReport <timestamp>/reports/GajabAutomationReport.html` for mobile,
`test-output/WebExtentReport <timestamp>/reports/GajabAutomationReport.html` for web - never to a
single fixed `reports/...html` path that gets overwritten each time. That's why old
`test-output/*ExtentReport <timestamp>/` folders pile up over time (see the cleanup note in
[Troubleshooting](#12-troubleshooting) if that bothers you) - each one is a genuinely separate
run's report, not a leftover mistake.

**The report's filename and title/branding are identical for mobile and web, by necessity, not
by choice.** `extent.properties` is one single file on the classpath, shared by both platforms -
there's no way to load a second, platform-specific copy of it. The `extentreports-cucumber7-adapter`
resolves `extent.reporter.spark.out` (the filename) and `extent.reporter.spark.config` (the
title/branding XML) straight from that one loaded file, and - unlike most other keys - ignores any
JVM system property override for those two specific keys (verified by decompiling the adapter jar;
this is a real quirk in the library, not a misconfiguration here). Only `basefolder.name` actually
respects a runtime override, which is why `WebTestRunner`'s static block can make web's *folder*
say "Web" but can't make the report *file inside it* say "Web" too - hence the neutral
`GajabAutomationReport.html` name and "Gajab Automation Report" title. **Which platform a report
is from is told apart by its folder name, not its filename or its title.**

## 8. How failure screenshots work

In `Hooks.java` (mobile) / `WebHooks.java` (web), the `@After` hook checks
`scenario.isFailed()`. If true:
1. A screenshot is taken of the device screen (mobile) or browser window
   (web) at the moment of failure.
2. It's saved to `screenshots/mobile/<scenario-name>_<timestamp>.png` or
   `screenshots/web/<scenario-name>_<timestamp>.png` respectively - each
   platform's own `ScreenshotUtil` copy is hardcoded to its own subfolder,
   so a web run's screenshots can never land next to (or overwrite)
   mobile's, and vice versa.
3. It's also embedded directly into that platform's own HTML report for
   quick viewing.

---

## 9. Continuous Integration (CI)

A starter GitHub Actions workflow for web lives at
[.github/workflows/web-regression.yml](.github/workflows/web-regression.yml):
1. Sets up JDK 11 (no Node.js/Appium/emulator needed - Chrome ships
   preinstalled on `ubuntu-latest`, and Selenium Manager fetches a matching
   chromedriver automatically).
2. Runs `mvn test -DsuiteXmlFile=testng-web.xml` under a virtual display
   (`xvfb`, via `coactions/setup-xvfb`), since `config.properties` ships
   with `web.headless=false` - this keeps CI behaving identically to a
   local run rather than requiring a headless-only override.
3. Uploads the web report, `screenshots/web/`, and `logs/web-automation.log`
   as build artifacts, whether the run passed or failed.

There is currently no mobile CI workflow in this repo. If you want to add
one, it needs: JDK 11 + Node.js + Appium (+ the UiAutomator2 driver), a
booted Android emulator (e.g. via `reactivecircus/android-emulator-runner`
on a `macos-latest` runner), then `mvn test -Dcucumber.filter.tags="@smoke"`
(or `-DsuiteXmlFile=testng-smoke.xml`) - same shape as web-regression.yml,
uploading the mobile report, `screenshots/mobile/`, and
`logs/mobile-automation.log` as artifacts instead.

This is a **starting point** — adjust the trigger branches and default tag
expression for your actual setup. If you use a different CI provider
(Jenkins, GitLab CI, Azure DevOps), the same command it already runs
(`mvn test -DsuiteXmlFile=testng-web.xml`) is what needs to be reproduced
there - the `xvfb` step is the only GitHub-Actions-specific part.

The `-D...` flag can be swapped for `-DsuiteXmlFile=...` pointed at any of
the dedicated suite files from section 6 instead, the same way a local run
can.

---

## 10. Web automation

A Selenium **web** suite lives alongside the mobile one under
`com.ecommerce.web`, mirroring the same concepts (Page Object Model, hooks,
logging, screenshots-on-failure, ExtentReports, retry) so it's familiar to
anyone who already knows the mobile suite - but it is its own independent
copy of every layer, not a shared one:

| Mobile | Web | Role |
|---|---|---|
| `mobile.config.ConfigReader` | `web.config.ConfigReader` | Reads `config.properties` - two independent copies of the same reader, not one shared class. |
| `mobile.config.CapabilityManager` | `web.config.WebCapabilityManager` | Builds driver capabilities from `config.properties` (`web.browser`, `web.headless`). |
| `mobile.config.DriverManager` | `web.config.WebDriverManager` | Owns the driver (ThreadLocal), created once per suite, reset between scenarios. |
| `mobile.pages.BasePage` | `web.pages.BaseWebPage` | Shared page-object vocabulary (`click`/`enterText`/`isDisplayed`/...). |
| `mobile.utils.ScreenshotUtil` | `web.utils.ScreenshotUtil` | Saves a failure screenshot - to `screenshots/mobile/` vs `screenshots/web/` respectively. |
| `mobile.hooks.Hooks` | `web.hooks.WebHooks` | `@BeforeAll` launch once, `@Before` reset state, `@After` screenshot on failure, `@AfterAll` quit. |
| `mobile.listeners.RetryAnalyzer` | `web.listeners.RetryAnalyzer` | Retries a failed scenario once before marking it failed. |
| `mobile.listeners.RetryListener` | `web.listeners.RetryListener` | Cleans up the "skipped" artifact TestNG's retry leaves behind on an eventual pass (see section 7). |
| `mobile.runner.TestRunner` | `web.runner.WebTestRunner` | Cucumber-TestNG entry point. |

**Why duplicated instead of shared:** so mobile and web can build and run
in complete isolation - a broken/missing class on one side can never break
the other, there's no third "common" package either depends on, and a run
of one suite never touches the other's logs, screenshots, or report. See
section 2 for the full rationale.

No driver-binary setup is needed - Selenium Manager (bundled with
Selenium 4.6+) downloads the matching chromedriver/geckodriver
automatically.

**Before running:** fill in `web.baseUrl` in `config.properties`, and
replace any remaining placeholder `@FindBy` locators in `web/pages/*.java`
with the real ones from the site (same "note on locators" convention as
the mobile pages - see section 5, Step 3, but using your browser's DevTools
"Inspect" instead of Appium Inspector).

**To run:**
```
mvn test -DsuiteXmlFile=testng-web.xml
```
or point IntelliJ's right-click Run/Debug at `WebTestRunner.java` directly.
`testng-web.xml` runs the web suite, restricted to `@web`-tagged scenarios
via a `cucumber.filter.tags` `<parameter>` - the same mechanism the mobile
smoke/regression suites use (see DEVELOPER_GUIDE.md section 3.1). Every
scenario under `features/web/` needs the `@web` tag for this reason - see
section "Adding a brand-new web use case" below.

**Where to look afterwards** (the web equivalent of section 6's table):
| What | Where |
|---|---|
| HTML test report | `test-output/WebExtentReport <timestamp>/reports/GajabAutomationReport.html` - see 7B |
| Execution log | `logs/web-automation.log` |
| Screenshots of failed scenarios | `screenshots/web/` |

Mobile and web share `config.properties` itself (as data, read by two
independent `ConfigReader` copies), the underlying Maven/library setup
(`pom.xml`), and - unavoidably, per 7B - the Extent report's filename and
title/branding (`extent.properties`/`extent-config.xml` are one classpath
resource neither platform can override for those two specific keys).
Everything else (logs, screenshots, which *folder* the report lands in,
retry, hooks,
driver manager, page-object base class) is fully separate per platform.

### Adding a brand-new web use case
The exact same 5-step recipe as section 2 of `DEVELOPER_GUIDE.md`, just in
the `web` package instead of `mobile`:
1. Write the scenario in `src/test/resources/features/web/*.feature`,
   tagged `@web` (plus whatever other tag fits) so `WebTestRunner` picks
   it up.
2. Add the step definition in `src/test/java/.../web/stepdefinitions/*.java`.
3. If it needs an action/locator that doesn't exist yet, add it to the
   relevant `src/main/java/.../web/pages/*.java` class - never put a
   locator or a raw Selenium call directly in a step definition.
4. Dry-run it: `mvn test -DsuiteXmlFile=testng-web.xml -Dcucumber.execution.dry-run=true`.
5. Run it for real: `mvn test -DsuiteXmlFile=testng-web.xml`.

See DEVELOPER_GUIDE.md section 2B for a worked example.

---

## 11. Troubleshooting

| Problem | Likely cause / fix |
|---|---|
| `session not created` / Appium fails to start | Another Appium instance is already using the port in `config.properties`. Stop it, or change `appium.server.port`. |
| `adb: no devices/emulators found` | Start your emulator or reconnect your device; confirm with `adb devices`. |
| `APK not found at ...` | Check `app.path` in `config.properties` matches the actual file name in `apps/`. |
| Element not found / `NoSuchElementException` | The locator in the relevant `pages/*.java` file is still a placeholder or is stale — re-check it in Appium Inspector. |
| Tests hang on the Login screen | `appPackage`/`appActivity` are wrong, or credentials in `LoginSteps.java` need updating. |
| Every test passes locally but fails once, then passes on retry | Expected — that's the retry mechanism absorbing normal flakiness. If it fails **twice**, treat it as a real bug. |
