package qa_test;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener extends TestBefore implements ITestListener {

    @Override
    public void onTestStart(ITestResult tr) {
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        screen.saveAllureScreenshotError("ERROR" + tr.getName());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
    }

    @Override
    public void onStart(ITestContext tr) {
    }


    @Override
    public void onFinish(ITestContext tr) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult res) {
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
    }
}

