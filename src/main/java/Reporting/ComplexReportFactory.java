package Reporting;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ComplexReportFactory {

    private static ExtentReports reporter;
    private static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
    private static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
    public static String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

    //The ExtentReports class generates HTML reports based on a path specified by the tester.
// Based on the Boolean flag, the existing report has to be overwritten or a new report must be generated.
// ‘True’ is the default value, meaning that all existing data will be overwritten.
    public synchronized static ExtentReports getExtentReport() {
        if (reporter == null) {
            // you can get the file name and other parameters here from a
            // config file or global variables
            reporter = new ExtentReports("reports/AutomationReport" + timeStamp + ".html", true, NetworkMode.OFFLINE);
        }
        return reporter;
    }

    //Create a report for the test
    public synchronized static ExtentTest getTest(String testName, String testDescription) {

        // if this test has already been created return
        if (!nameToTestMap.containsKey(testName)) {
            Long threadID = Thread.currentThread().getId();
            ExtentTest test = getExtentReport().startTest(testName, testDescription);
            nameToTestMap.put(testName, test);
            threadToExtentTestMap.put(threadID, testName);
        }
        return nameToTestMap.get(testName);
    }

    public synchronized static ExtentTest getTest(String testName) {
        return getTest(testName, "");
    }

    /*
     * At any given instance there will be only one test running in a thread. We
     * are already maintaining a thread to extentest map. This method should be
     * used after some part of the code has already called getTest with proper
     * testcase name and/or description.
     *
     * Reason: This method is not for creating test but getting an existing test
     * reporter. sometime you are in a utility function and want to log
     * something from there. Utility function or similar code sections are not
     * bound to a test hence we cannot get a reporter via test name, unless we
     * pass test name in all method calls. Which will be an overhead and a rigid
     * design. However, there is one common association which is the thread ID.
     * When testng executes a test it puts it launches a new thread, assuming
     * test level threading, all tests will have a unique thread id hence call
     * to this function will return the right extent test to use
     */
    public static synchronized ExtentTest getTest() {
        Long threadID = Thread.currentThread().getId();

        if (threadToExtentTestMap.containsKey(threadID)) {
            String testName = threadToExtentTestMap.get(threadID);
            return nameToTestMap.get(testName);
        }
        //system log, this shouldn't happen but in this crazy times if it did happen log it.
        return null;
    }

    // Executes post conditions of a test case
    public static synchronized void closeTest(String testName) {

        if (!testName.isEmpty()) {
            ExtentTest test = getTest(testName);
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest(ExtentTest test) {
        if (test != null) {
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest() {
        ExtentTest test = getTest();
        closeTest(test);
    }

    //End test method and closed the report
    public synchronized static void closeReport() {
        if (reporter != null) {
            reporter.flush();
            //reporter.close();
        }
    }

}
