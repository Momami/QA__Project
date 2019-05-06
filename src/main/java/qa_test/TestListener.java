package qa_test;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.Attachment;
import io.qameta.allure.model.StepResult;

@SuppressWarnings("JavadocType")
public class TestListener extends TestBefore implements StepLifecycleListener {
    private void screenForAllure(final StepResult stepResult){
        Attachment attachment = new Attachment();
        attachment.setType("image/png");
        attachment.setSource(PATH_FOR_SCREEN + "\\" + screenDate() + ".png");
        stepResult.withAttachments(attachment);
    }

    @Override
    public void beforeStepStart(final StepResult stepResult) {
        screenForAllure(stepResult);
    }

    @Override
    public void beforeStepStop(final StepResult stepResult) {
        screenForAllure(stepResult);
    }
}
