# Ecommerce Mobile Automation Framework

Mobile UI automation for the Ecommerce Android app, written in plain-English
BDD scenarios so that QA, developers **and** business/leadership can read
what is being tested without needing to read a line of code.

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

```
mobile/
├── apps/                                # Put your .apk file here
├── screenshots/                         # Auto-created: screenshots of FAILED tests
├── logs/                                # Auto-created: automation.log for every run
├── reports/                             # Auto-created: the HTML test report
├── pom.xml                              # Maven build file (all dependencies)
├── testng.xml                           # Runs EVERY scenario (the default suite)
├── testng-smoke.xml                     # Runs only @smoke-tagged scenarios - just click & Run
├── testng-regression.xml                # Runs only @regression-tagged scenarios - just click & Run
├── .github/workflows/                   # CI pipeline (GitHub Actions starter)
├── src/
│   ├── main/java/com/ecommerce/
│   │   ├── mobile/
│   │   │   ├── config/                  # Reads config.properties, builds capabilities,
│   │   │   │                            # starts Appium server, manages the driver
│   │   │   ├── pages/                   # One class per app screen (Page Object Model)
│   │   │   └── utils/                   # ScreenshotUtil, MailUtil (screenshots + summary email)
│   │   └── web/                         # Reserved for future Selenium WEB automation
│   └── test/
│       ├── java/com/ecommerce/
│       │   ├── mobile/
│       │   │   ├── hooks/               # Before/after every scenario (launch app, screenshot on fail)
│       │   │   ├── stepdefinitions/     # Java code behind each English sentence in the .feature files
│       │   │   ├── runner/              # TestRunner - the Cucumber + TestNG entry point
│       │   │   └── listeners/           # RetryAnalyzer (reruns a failed scenario once),
│       │   │                            # EmailReportListener (sends the summary email once the run finishes)
│       │   └── web/                     # Reserved for future web step definitions
│       └── resources/
│           ├── features/                # The 6 .feature files (18 scenarios total)
│           ├── config.properties        # ALL settings you need to change live here
│           ├── log4j2.xml               # Logging configuration
│           ├── extent.properties        # Report output location
│           └── extent-config.xml        # Report look & feel
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
your catalog (find-and-replace across the `features/` folder).

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
   `src/test/resources/features/`.
3. To run just one tag (e.g. only smoke tests) via the command line, add:
   ```
   mvn test -Dcucumber.filter.tags="@smoke"
   ```
4. **In IntelliJ:** right-click `testng.xml` → "Run", or right-click
   `TestRunner.java` → "Run"/"Debug".
5. **In IntelliJ, one scenario at a time:** open any `.feature` file — a
   green ▶ appears in the gutter next to each `Scenario:` line (and next
   to `Feature:` to run the whole file). Click it to run just that one.
6. **In CI:** see section 10 below — the same `mvn test` command is what
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
Retry-on-failure and the summary email are provided by **TestNG**
(`RetryAnalyzer`, `EmailReportListener`). The Cucumber gutter ▶ icon
bypasses TestNG entirely and talks to Cucumber's own CLI runner, so it
won't trigger either of those two things (the app-launch/screenshot hooks
still fire either way, since those are Cucumber-native, not TestNG).

| How you run it | Retry-on-failure | Summary email | Screenshot on failure |
|---|---|---|---|
| `mvn test` | ✅ | ✅ | ✅ |
| `mvn test -Dcucumber.filter.tags=...` | ✅ | ✅ | ✅ |
| `mvn test -DsuiteXmlFile=testng-smoke.xml` (or `-regression.xml`) | ✅ | ✅ | ✅ |
| IntelliJ → right-click `testng.xml` / `testng-smoke.xml` / `testng-regression.xml` → Run/Debug | ✅ | ✅ | ✅ |
| IntelliJ → right-click `TestRunner.java` → Run/Debug | ✅ | ✅ | ✅ |
| IntelliJ → ▶ gutter icon on one `Scenario:`/`Feature:` | ❌ | ❌ | ✅ |
| CI pipeline (`mvn test ...`) | ✅ | ✅ | ✅ |

If you're actively writing/debugging a single scenario, the gutter icon
is the fastest loop. Once it works, confirm it end-to-end with
`mvn test -Dcucumber.filter.tags="@focus"` (see `DEVELOPER_GUIDE.md`) so
retry and the email path get exercised too.

### Where to look afterwards
| What | Where |
|---|---|
| HTML test report | `reports/EcommerceMobileAutomationReport.html` |
| Execution log | `logs/automation.log` |
| Screenshots of failed scenarios | `screenshots/` |
| Summary email | the inbox at `mail.to` in `config.properties` |

---

## 7. How the retry mechanism works

`RetryAnalyzer.java` (under `listeners/`) tells TestNG: "if a scenario
fails, run it one more time before marking it as Failed." This is wired
into `TestRunner.java` via `retryAnalyzer = RetryAnalyzer.class`.

This catches one-off flakiness (a slow device, a slow network call)
without hiding a genuinely broken feature — if it fails twice in a row,
it is reported as failed.

## 8. How failure screenshots work

In `Hooks.java`, the `@After` hook checks `scenario.isFailed()`. If true:
1. A screenshot is taken of the device screen at the moment of failure.
2. It's saved to `screenshots/<scenario-name>_<timestamp>.png`.
3. It's also embedded directly into the HTML report for quick viewing.

---

## 9. Email notifications

Once the **whole run finishes** (not per-scenario), `EmailReportListener`
(a TestNG `IReporter`, registered via `@Listeners` on `TestRunner.java`)
sends one summary email:
- **Subject**: pass/fail status and count, e.g. `Ecommerce Mobile
  Automation - FAILED (15/18 passed) - 21-Aug-2026 10:41`.
- **Body**: a pass/fail/skip table, a simple HTML bar chart, the list of
  failed scenario names, and a link to the HTML report.
- **Attachment**: the full `EcommerceMobileAutomationReport.html`.

### Setup
1. In `config.properties`, fill in:
   ```properties
   mail.from=your-sending-address@gmail.com
   mail.smtp.host=smtp.gmail.com      # already set for Gmail; change for another provider
   ```
   `mail.to` is already set to `nachiyappanworks@gmail.com`.
2. **Do not put the mailbox password in `config.properties`.** Set it as
   the `MAIL_FROM_PASSWORD` environment variable instead:
   - Locally: `export MAIL_FROM_PASSWORD="your-app-password"` before
     running `mvn test`.
   - In IntelliJ: Run/Debug Configuration → Environment variables →
     `MAIL_FROM_PASSWORD=...`.
   - In CI: store it as a repository secret (see section 10) — never
     commit it.
3. **If using Gmail**: enable 2-Step Verification on the account, then
   create an **App Password** (Google Account → Security → App
   passwords) and use that as `MAIL_FROM_PASSWORD` — not the normal
   account password.

If any of `mail.from` / `mail.smtp.host` / `mail.to` / `MAIL_FROM_PASSWORD`
is missing, the email is simply skipped (logged as a warning) — it never
fails the build. Set `mail.enabled=false` to turn the feature off
entirely.

---

## 10. Continuous Integration (CI)

A starter GitHub Actions workflow is at
[.github/workflows/mobile-regression.yml](.github/workflows/mobile-regression.yml).
It:
1. Sets up JDK 11, Node.js, and Appium (+ the UiAutomator2 driver).
2. Boots an Android emulator (via `reactivecircus/android-emulator-runner`)
   and runs `mvn test -Dcucumber.filter.tags="@smoke"` against it
   (configurable per-run via the workflow's manual "tags" input).
3. Uploads `reports/`, `screenshots/`, and `logs/` as build artifacts,
   whether the run passed or failed.
4. Passes `MAIL_FROM_PASSWORD` in from a GitHub **repository secret** —
   add it under Settings → Secrets and variables → Actions.

This is a **starting point** — adjust the emulator API level/target, the
default tag expression, and the trigger branches for your actual setup.
If you use a different CI provider (Jenkins, GitLab CI, Azure DevOps),
the same three commands (install Appium, start an emulator/device, run
`mvn test`) are what needs to be reproduced there; the emulator-boot step
is the only GitHub-Actions-specific part.

The workflow uses `-Dcucumber.filter.tags=...`, but `-DsuiteXmlFile=testng-smoke.xml`
works exactly as well as the `script:` line if you'd rather point CI at one
of the dedicated suite files from section 6 instead.

---

## 11. Roadmap: adding web automation later

This project is deliberately structured so a Selenium **web** suite can be
added without restructuring anything:
- `com.ecommerce.web` packages already exist (empty, under both
  `src/main/java` and `src/test/java`) as the reserved home for web page
  objects and web step definitions.
- `src/test/resources/features/web/` is reserved for web `.feature` files.
- When ready: add `selenium-java`'s WebDriverManager dependency, create a
  `WebDriverManager`/`WebCapabilityManager` under `com.ecommerce.web`
  mirroring the mobile `config/` classes, and add a second TestNG runner
  (e.g. `WebTestRunner`) pointed at the web features/glue.

---

## 12. Troubleshooting

| Problem | Likely cause / fix |
|---|---|
| `session not created` / Appium fails to start | Another Appium instance is already using the port in `config.properties`. Stop it, or change `appium.server.port`. |
| No summary email arrives | Check the log for the `MailUtil` warning — it names exactly which of `mail.from` / `mail.smtp.host` / `mail.to` / `MAIL_FROM_PASSWORD` is missing. Also confirm you ran via `mvn test`/`testng.xml`/`TestRunner.java`, not the per-scenario gutter icon (see section 6). |
| Gmail rejects the login | You're using your normal account password. Create an App Password instead (see section 9) — Gmail blocks plain-password SMTP logins by default. |
| `adb: no devices/emulators found` | Start your emulator or reconnect your device; confirm with `adb devices`. |
| `APK not found at ...` | Check `app.path` in `config.properties` matches the actual file name in `apps/`. |
| Element not found / `NoSuchElementException` | The locator in the relevant `pages/*.java` file is still a placeholder or is stale — re-check it in Appium Inspector. |
| Tests hang on the Login screen | `appPackage`/`appActivity` are wrong, or credentials in `LoginSteps.java` need updating. |
| Every test passes locally but fails once, then passes on retry | Expected — that's the retry mechanism absorbing normal flakiness. If it fails **twice**, treat it as a real bug. |
