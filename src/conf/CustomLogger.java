package conf;

import org.testng.Reporter;

public class CustomLogger {

    public static void log(String message) {
        Reporter.log(message + "<br>");
    }

    public static void log(int i) {
        log("" + i);
    }

    public static void log(boolean b) {
        log("" + b);
    }

    public static void title(String message) {
        log("<h3>" + message + "</h3>");
    }
}
