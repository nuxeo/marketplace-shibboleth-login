package org.nuxeo.ecm.platform.shibboleth;

import static org.nuxeo.functionaltests.Constants.ADMINISTRATOR;

import org.junit.Test;
import org.nuxeo.functionaltests.Locator;
import org.nuxeo.functionaltests.shibboleth.ShibbolethTest;
import org.openqa.selenium.By;

import java.io.IOException;

/**
 * @since 9.10
 */
public class ITLoginLogoutTest extends ShibbolethTest {

    @Test
    public void testLoginLogout() throws IOException {

        loginAsShibbolethUser(NUXEO_URL + "/", ADMINISTRATOR, ADMINISTRATOR);
        Locator.findElementWaitUntilEnabledAndClick(By.linkText("HOME"));
        logoutSimply();
    }

}
