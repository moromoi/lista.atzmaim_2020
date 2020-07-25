package im.atzma.lista2020.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
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


    public StringBuffer verificationErrors = new StringBuffer();
    public boolean acceptNextAlert = true;


    public ApplicationManager() {

        properties = new Properties();
    }

    public void init() throws InterruptedException, IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        browser = properties.getProperty("web.browser");


        if (browser.equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\automation\\browser drivers\\chromedriver_83_win32\\chromedriver.exe");
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Nexus 5");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeOptions.addArguments(("--auto-open-devtools-for-tabs"));
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//            chromeOptions.addArguments("user-data-dir=C:/Users/user/AppData/Local/Google/Chrome/User Data");
//            chromeOptions.addArguments("--headless", "--disable-gpu");
            driver = new ChromeDriver(chromeOptions);
        } else if (browser.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\automation\\browser drivers\\firefox\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equals("Selenoid")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("83.0");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);

            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Nexus 5");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeOptions.addArguments(("--auto-open-devtools-for-tabs"));
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            chromeOptions.merge(capabilities);

            driver = new RemoteWebDriver(
                    URI.create("http://68.183.243.172:4444/wd/hub").toURL(), chromeOptions);
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
    }


    public void stop() {
        driver.close();
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
        // Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

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
}


