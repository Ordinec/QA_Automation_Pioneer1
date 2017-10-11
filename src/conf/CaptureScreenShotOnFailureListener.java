package conf;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static tests.Main.getDriver;
import static tests.Main.stopDriver;

public class CaptureScreenShotOnFailureListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        File file = new File("");
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS", Locale.ENGLISH);
        Reporter.setCurrentTestResult(result);
        String path = file.getAbsolutePath();
        String reportsFolder = path + "/target/";
        String screenShotsFolder = "failure-screenshots/";
        String screenShotName = result.getMethod().getMethodName() + "(" + result.getTestClass().getName() + ")" + "-"
                + formatter.format(calendar.getTime()) + ".png";
        // Create the filename for the screen shots
        String fileName = reportsFolder
                + screenShotsFolder
                + screenShotName;
        //Put the path, written with red font and link to the screen shot into ReportNG output.
        CustomLogger.log("<font color='red'>Fail! Screenshot with test failure saved at " + fileName + "</font>");
        CustomLogger.log("<a href='" + screenShotsFolder + screenShotName + "'>" + screenShotName + "</a>");
        try {
            File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnreachableBrowserException e) {
            CustomLogger.log("Cannot capture the screenshot. Error communicating with the remote browser. It may have died.");
        } catch (UnhandledAlertException a){
            CustomLogger.log("Cannot capture screenshot, killing browser");
        }
        Reporter.setCurrentTestResult(null);
        stopDriver();
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        stopDriver();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        stopDriver();
    }

}