package im.atzma.lista2020.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.IsoChronology;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
    private String browser;
    public WebDriver driver;
    Properties properties;

    NavigationHelper navigationHelper;
    SingupPage singupPage;
    CalendarPage calendarPage;
    ClientListPage clientPage;
    ServicesHelper servicesHelper;
    ClientHelper clientHelper;
    AppointmentHelper appointmentHelper;

    P81_NavigationHelper p81_navigationHelper;
    P81_PolicyHelper p81_policyHelper;
    P81_MembersHelper p81_membersHelper;
    P81_API_Helper p81_api_helper;


    public StringBuffer verificationErrors = new StringBuffer();
    public boolean acceptNextAlert = true;


    public ApplicationManager() {

    }

    public void init() throws InterruptedException, IOException {

        Clock clock = Clock.system(ZoneId.of("Asia/Jerusalem"));
        ZonedDateTime zdt = ZonedDateTime.now(clock);
        ChronoLocalDateTime dt1 = IsoChronology.INSTANCE.localDateTime(zdt);
        String time = dt1.toString();
        properties = new Properties();

        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        browser = properties.getProperty("web.browser");

        if (browser.equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\automation\\browser drivers\\chromedriver_86_win32\\chromedriver.exe");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("86.0");
            capabilities.setCapability("timeZone", "Asia/Jerusalem");
            capabilities.setCapability("geoLocation","IL");
            capabilities.setCapability("applicationCacheEnabled", true);
            capabilities.setCapability("locationContextEnabled", true);
            capabilities.setCapability("javascriptEnabled", true);
//            LoggingPreferences loggingprefs = new LoggingPreferences();
//            loggingprefs.enable(LogType.BROWSER, Level.ALL);
//            loggingprefs.enable(LogType.PERFORMANCE, Level.INFO);
//            loggingprefs.enable(LogType.DRIVER, Level.ALL);
//            capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

            Map<String, String> mobileEmulation = new HashMap<>();
//            mobileEmulation.put("deviceName", "iPhone X");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.setExperimentalOption("useAutomationExtension", false);

//            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeOptions.addArguments(("--auto-open-devtools-for-tabs"));
            chromeOptions.addArguments("--ignore-certificate-errors");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 3);
            chromeOptions.setExperimentalOption("prefs", prefs);
            chromeOptions.merge(capabilities);

            driver = new ChromeDriver();
        } else if (browser.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\automation\\browser drivers\\firefox\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equals("Selenoid")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("84.0");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            capabilities.setCapability("timeZone", "Asia/Jerusalem");
            capabilities.setCapability("geoLocation","IL");
            capabilities.setCapability("videoName", time);
            capabilities.setCapability("applicationCacheEnabled", false);
            capabilities.setCapability("locationContextEnabled", true);
            capabilities.setCapability("javascriptEnabled", true);
//            LoggingPreferences loggingprefs = new LoggingPreferences();
//            loggingprefs.enable(LogType.BROWSER, Level.ALL);
//            loggingprefs.enable(LogType.DRIVER, Level.ALL);
//            capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

            Map<String, String> mobileEmulation = new HashMap<>();
//            mobileEmulation.put("deviceName", "iPhone X");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.setExperimentalOption("useAutomationExtension", false);

//            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeOptions.addArguments(("--auto-open-devtools-for-tabs"));
            chromeOptions.addArguments("--ignore-certificate-errors");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 3);
            chromeOptions.setExperimentalOption("prefs", prefs);
            chromeOptions.merge(capabilities);

            driver = new RemoteWebDriver(
            URI.create("http://157.245.21.27:4444/wd/hub").toURL(), chromeOptions);
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        navigationHelper = new NavigationHelper(driver);
        singupPage = new SingupPage(driver);
        calendarPage = new CalendarPage(driver);
        clientPage = new ClientListPage(driver);
        servicesHelper = new ServicesHelper(driver);
        clientHelper = new ClientHelper(driver);
        appointmentHelper = new AppointmentHelper(driver);

        p81_navigationHelper = new P81_NavigationHelper(driver);
        p81_policyHelper = new P81_PolicyHelper(driver);
        p81_membersHelper = new P81_MembersHelper(driver);
        p81_api_helper = new P81_API_Helper();
    }


    public void stop() {
     //   getLogs();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
        driver.close();
        driver.quit();
        // Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
    }

    public NavigationHelper goTo() { return navigationHelper; }

    public SingupPage singupPage() {
        return singupPage;
    }

    public CalendarPage calendar() {
        return calendarPage;
    }

    public ClientListPage clientList() {
        return clientPage;
    }

    public ServicesHelper service() {
        return servicesHelper;
    }

    public ClientHelper client() {
        return clientHelper;
    }

    public AppointmentHelper appointment() {
        return appointmentHelper;
    }


    public P81_NavigationHelper P81_goTo() { return p81_navigationHelper; }
    public P81_PolicyHelper p81_policyHelper() { return p81_policyHelper; }
    public P81_MembersHelper p81_membersHelper() { return  p81_membersHelper; }
    public P81_API_Helper p81_api_helper() {return  p81_api_helper; }

    public void getLogs() {
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.PERFORMANCE);

        List<LogEntry> errorLogs = logEntries.getAll();

        int status = -1;

        System.out.println("\nList of log entries:\n");

        for (LogEntry logEntry : logEntries) {
            System.out.println(logEntry.getMessage());
        }

        System.out.println("\nstatus code: " + status);

    }
}

