/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package afaque.spring.boot.k8;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class ApplicationTest {
    @Test public void appHasAGreeting() {
        Application classUnderTest = new Application();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
