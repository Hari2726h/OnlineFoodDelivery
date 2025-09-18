Conversation opened. 7 messages. All messages read.

Skip to content
Using Sri Krishna College of Technology Mail with screen readers
in:sent 

2 of 121
(no subject)

727823TUCS102 HARIHARAN C <727823tucs102@skct.edu.in>
7:48 AM (3 hours ago)
to Hari

package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Reporter {
    private static Properties prop;
    private static ExtentReports extentReport;

    public static ExtentReports generateExtentReport() {
        return generateExtentReport(null);
    }

    public static ExtentReports generateExtentReport(String reportName) {
        if (extentReport == null) {
            extentReport = createExtentReport(reportName);
        }
        return extentReport;
    }

    private static ExtentReports createExtentReport(String reportName) {
        ExtentReports extentReport = new ExtentReports();

        // Load properties from browser.properties file
        String filepath = System.getProperty("user.dir") + "/config/browser.properties";
        try {
            FileInputStream file = new FileInputStream(filepath);
            prop = new Properties();
            prop.load(file);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        // Get the current timestamp for the report name
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata"); // IST timezone
        dateFormat.setTimeZone(istTimeZone);
        String timestamp = dateFormat.format(new Date());

        // Define the report file path with the timestamp and provided report name
        String reportFilePath = System.getProperty("user.dir") + "/reports/";
        if (reportName == null || reportName.isEmpty()) {
            reportName = "_Enter Name_Application Report";
        }
        reportFilePath += reportName + "_" + timestamp + ".html";

        File extentReportFile = new File(reportFilePath);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName("_Enter Name_Test Report");
        sparkReporter.config().setDocumentTitle("_Enter Name_ Test Automation Report");
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss");

        extentReport.attachReporter(sparkReporter);

        extentReport.setSystemInfo("Application URL", prop.getProperty("url"));
        extentReport.setSystemInfo("Browser Name", prop.getProperty("browserName"));
        extentReport.setSystemInfo("Email", prop.getProperty("validEmail"));
        extentReport.setSystemInfo("Password", prop.getProperty("validPassword"));
        extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReport.setSystemInfo("Username", System.getProperty("user.name"));
        extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
        return extentReport;
    }

    public static String captureScreenshotAsBase64(WebDriver driver, String screenshotName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata"); // IST timezone
        dateFormat.setTimeZone(istTimeZone);
        String timestamp = dateFormat.format(new Date());

        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        byte[] screenshotBytes = screenshotDriver.getScreenshotAs(OutputType.BYTES);

        String base64Screenshot = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(screenshotBytes);
            base64Screenshot = Base64.getEncoder().encodeToString(baos.toByteArray());

            // Save the screenshot to a file for reference
            saveScreenshotToFile(screenshotBytes, screenshotName + "_" + timestamp + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64Screenshot;
    }

    private static void saveScreenshotToFile(byte[] screenshotBytes, String fileName) {
        try {
            String screenshotsDirPath = System.getProperty("user.dir") + "/reports/errorScreenshots/";
            File screenshotsDir = new File(screenshotsDirPath);
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            String destinationScreenshotPath = screenshotsDirPath + fileName;
            FileOutputStream outputStream = new FileOutputStream(destinationScreenshotPath);
            outputStream.write(screenshotBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //To attach screenshot in the report file please call the below method.
     public static void attachScreenshotToReport(ExtentTest test, WebDriver driver, String screenshotName) {
        String base64Screenshot = captureScreenshotAsBase64(driver, screenshotName);
        test.addScreenCaptureFromBase64String(base64Screenshot);
    }
}


package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
    public static WebDriver driver;
    public final int IMPLICIT_WAIT_TIME = 10;
    public final int PAGE_LOAD_TIME = 18;
    public Properties prop;
    public Base() {
        String filepath = System.getProperty("user.dir") + "/config/browser.properties";
    
        try {
            FileInputStream file = new FileInputStream(filepath);
            prop = new Properties();
            prop.load(file);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public WebDriver openBrowser() throws MalformedURLException {
        String browsername = prop.getProperty("browsername");
        if (browsername.equalsIgnoreCase("chrome")) {
             DesiredCapabilities dc = new DesiredCapabilities();
             dc.setBrowserName("chrome");
             driver = new RemoteWebDriver(new URL("http://localhost:4444"), dc);
        } else if (browsername.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browsername.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browsername.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else if (browsername.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME));   
        driver.get(prop.getProperty("url"));
        WebDriverListener listener = new EventHandler();
        driver = new EventFiringDecorator<>(listener).decorate(driver);
        return driver;
    }

//Note: The below method is used to navigate to the URL mentioned in the browser.properties file.
//If you are using this method then comment the above driver.get(prop.getProperty("url")); and uncomment the below method.


    // public void navigateToURL(WebDriver driver) {
    //     try {
    //         driver.get(prop.getProperty("url"));        
    //     } catch (Exception e) {
    //         e.printStackTrace();

    //     }
    // }
}




package utils;

import org.openqa.selenium.support.events.WebDriverListener;

public class EventHandler implements WebDriverListener {

}






package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static FileInputStream file;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static FileOutputStream fileout;
    public static XSSFRow row;
    public static XSSFCell col;
    public static int rowvalue;
    public static int colvalue;

    public static String readdata(String filepath, String sheetname, int rownumber, int colnumber) throws IOException

    {

        try {
            file = new FileInputStream(filepath);
            workbook = new XSSFWorkbook(file);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        sheet = workbook.getSheet(sheetname);
        row = sheet.getRow(rownumber);
        col = row.getCell(colnumber);

        // String value = col.toString();
        String value;

        try {
            DataFormatter d = new DataFormatter();
            value = d.formatCellValue(col);
            return value;

        } catch (Exception e) {
            value = "";
        }
        workbook.close();
        file.close();
        return value;

    }

    public static void writedata(String filepath, String sheetname, int rownumber, int colnumber, String value)
            throws IOException {
        try {
            file = new FileInputStream(filepath);
            workbook = new XSSFWorkbook(file);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        sheet = workbook.getSheet(sheetname);

        row = sheet.getRow(rownumber);

        col = row.getCell(colnumber);

        col.setCellValue(value);

        try {
            fileout = new FileOutputStream(filepath);
            workbook.write(fileout);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        workbook.close();

        file.close();

        fileout.close();

    }

}





package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.PatternLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerHandler {

     private static final Logger logger = Logger.getLogger(LoggerHandler.class);

        public static Logger getLogger() {
            return logger;
        }

    public static void initLog4j() {
         // Set the system property for currentTimestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        System.setProperty("log.datePattern", "yyyy-MM-dd-HH-mm-ss");

        // Initialize Log4j using the properties file
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        // Obtain the "RollingAppender" and configure it
        Logger log = Logger.getLogger(LoggerHandler.class);
        DailyRollingFileAppender appender = (DailyRollingFileAppender) log.getAppender("RollingAppender");

        // Define the desired log file extension format
        String logFileExtension = ".logs"; // Modify this as needed

        // Configure the appender as needed
        appender.setFile("logs/logfile_" + timestamp + logFileExtension);
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%p %d %c %M - %m%n");
        appender.setLayout(layout);
        appender.activateOptions(); // Activate the new options
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logDebug(String message) {
        logger.debug(message);
    }

    public static void logWarn(String message) {
        logger.warn(message);
    }

    public static void logError(String message) {
        logger.error(message);
    }

    public static void logFatal(String message) {
        logger.fatal(message);
    }

    public static void logTrace(String message) {
        logger.trace(message);
    }
}





package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
    public static String getScreenShot(WebDriver driver, String fileName) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        Date date = new Date();
        
        String directoryPath = System.getProperty("user.dir") + "/screenshot/";
        File directory = new File(directoryPath);
        
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = directoryPath + fileName + "-" + dateFormat.format(date) + ".png";
        File target = new File(destination);
        FileUtils.copyFile(scrFile, target);
        return destination;
    }
}





package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WebDriverHelper {
    public static final int IMPLICIT_WAIT_TIME = 10;
    public static final int PAGE_LOAD_TIME = 10;
    private WebDriver driver;

    public WebDriverHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementToBeVisible(By element, int timeoutInSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public void clickOnElement(By element) {
        try {
            WebElement webElement = driver.findElement(element);
            String elementName = webElement.getText();
            webElement.click();
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public void sendKeys(By element, String data) {
        try {
            WebElement webElement = driver.findElement(element);
            webElement.sendKeys(data);
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public String getText(By element) {
        try {
            WebElement webElement = driver.findElement(element);
            return webElement.getText();
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
            return null;
        }
    }

    public void jsClick(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'background: lightskyblue; border: 2px solid red;');", element);
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
            js.executeScript("arguments[0].click();", element);
            String elementName = element.getText();
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public void javascriptScroll(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'background: lightskyblue; border: 2px solid red;');", element);
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
            js.executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public void switchToNewWindow() {
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!windowHandle.isEmpty()) {
                    driver.switchTo().window(windowHandle);
                } else {
                    throw new Exception("New window could not be retrieved");
                }
            }
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public void enterAction(By element) {
        try {
            WebElement webElement = driver.findElement(element);
            webElement.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }

    public void hoverOverElement(By element) {
        try {
            WebElement webElement = driver.findElement(element);
            Actions actions = new Actions(driver);
            actions.moveToElement(webElement).perform();
        } catch (Exception e) {
            // Handle or rethrow the exception here
            e.printStackTrace();
        }
    }
}




4

727823TUCS102 HARIHARAN C
11:11 AM (35 minutes ago)
---------- Forwarded message --------- From: 727823TUCS102 HARIHARAN C <727823tucs102@skct.edu.in> Date: Thu, 18 Sept, 2025, 10:39 am Subject: Fwd: To: <727823t

727823TUCS102 HARIHARAN C <727823tucs102@skct.edu.in>
11:25 AM (21 minutes ago)
to 727823tucs115

