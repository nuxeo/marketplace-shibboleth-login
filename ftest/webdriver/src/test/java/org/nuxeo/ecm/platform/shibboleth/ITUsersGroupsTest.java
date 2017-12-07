/*
 * (C) Copyright 2017 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Funsho David
 */

package org.nuxeo.ecm.platform.shibboleth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.nuxeo.functionaltests.Constants.ADMINISTRATOR;

import org.junit.Ignore;
import org.junit.Test;
import org.nuxeo.functionaltests.Locator;
import org.nuxeo.functionaltests.RestHelper;
import org.nuxeo.functionaltests.pages.UserHomePage;
import org.nuxeo.functionaltests.pages.admincenter.AdminCenterBasePage;
import org.nuxeo.functionaltests.pages.admincenter.usermanagement.GroupsTabSubPage;
import org.nuxeo.functionaltests.pages.admincenter.usermanagement.UsersGroupsBasePage;
import org.nuxeo.functionaltests.pages.admincenter.usermanagement.UsersTabSubPage;
import org.nuxeo.functionaltests.shibboleth.ShibbolethTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @since 9.10
 */
public class ITUsersGroupsTest extends ShibbolethTest {

    protected static final String TEST_GROUP_NAME = "testgroup";

    protected static final String TEST_GROUP_LABEL = "Test Group";

    @Test
    @Ignore
    public void testSetGroupOnUser() {
        try {

            // First connection with a user to create it in the database if not multi-directory
            loginAsShibbolethUser(NUXEO_URL + "/", SHIB_TEST_USER, SHIB_TEST_PASSWORD);
            Locator.findElementWaitUntilEnabledAndClick(By.linkText("HOME"));
            logoutSimply();

            loginAsShibbolethUser(NUXEO_URL + "/", ADMINISTRATOR, ADMINISTRATOR);
            Locator.findElementWaitUntilEnabledAndClick(By.linkText("ADMIN"));
            UsersGroupsBasePage page = asPage(AdminCenterBasePage.class).getUsersGroupsHomePage();
            UsersTabSubPage usersPage = page.getUsersTab().searchUser("nux");
            assertTrue(usersPage.isUserFound(SHIB_TEST_USER));

            GroupsTabSubPage groupsTab = page.getGroupsTab();
            groupsTab = groupsTab.getGroupCreatePage()
                                 .createGroup(TEST_GROUP_NAME, TEST_GROUP_LABEL, new String[] { SHIB_TEST_USER }, null)
                                 .getGroupsTab(true);

            assertTrue(groupsTab.searchGroup(TEST_GROUP_NAME).isGroupFound(TEST_GROUP_NAME));
            logoutSimply();

            loginAsShibbolethUser(NUXEO_URL + "/", SHIB_TEST_USER, SHIB_TEST_PASSWORD);
            asPage(UserHomePage.class).goToProfile();
            WebElement groupLabel = Locator.findElement(By.xpath("//span[@class='group']"));
            assertEquals(TEST_GROUP_LABEL, groupLabel.getText());
            logoutSimply();

        } finally {
            RestHelper.deleteGroup(TEST_GROUP_NAME);
        }
    }

}
