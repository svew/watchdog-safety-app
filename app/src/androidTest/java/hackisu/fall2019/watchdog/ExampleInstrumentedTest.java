package hackisu.fall2019.watchdog;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
<<<<<<< HEAD
    public void useAppContext() {
=======
    public void useAppContext() throws Exception {
>>>>>>> e2171bc12caa029a4c400f8a556f1c87543d2e77
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("hackisu.fall2019.watchdog", appContext.getPackageName());
    }
}
