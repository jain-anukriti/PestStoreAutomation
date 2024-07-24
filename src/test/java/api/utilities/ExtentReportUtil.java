package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil implements ITestListener { // To override TestNG listeners
	
	public ExtentSparkReporter sparkReporter; // responsible for UI of report
	public ExtentReports extent; // common information
	public ExtentTest test; //test related
	
	String reportName;
	
	public void onStart(ITestContext testContext) {
		String timestamp = new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new Date());
		reportName = "Test report - " + timestamp + ".html";
		
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//reports//" + reportName);
		sparkReporter.config().setDocumentTitle("Pet Store Rest assured automation results");
		sparkReporter.config().setReportName("Pet Store Users API");
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "Anukriti Jain");
	}
	
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
		//can take screenshot here in case of UI test failure
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}

}
