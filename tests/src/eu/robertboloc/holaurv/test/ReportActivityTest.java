package eu.robertboloc.holaurv.test;

import junit.framework.TestCase;
import eu.robertboloc.holaurv.ReportActivity;

public class ReportActivityTest extends TestCase {

    public void testMilisToDisplayTimeWithNegativeInput() {
        ReportActivity reportActivity = new ReportActivity();

        String displayTime = reportActivity.milisToDisplayTime(-8400000);

        assertEquals("-02:20", displayTime);
    }
}
