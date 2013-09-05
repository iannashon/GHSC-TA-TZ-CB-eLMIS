/*
 * Copyright © 2013 VillageReach.  All Rights Reserved.  This Source Code Form is subject to the terms of the Mozilla Public License, v. 20.
 *
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.openlmis.pageobjects;

import org.openlmis.UiUtils.TestWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.IOException;

import static org.openqa.selenium.support.How.ID;


public class ConfigureEDIPage extends Page {

  @FindBy(how = ID, using = "configureOrder")
  private static WebElement configureOrderButton;

  @FindBy(how = ID, using = "configureShipment")
  private static WebElement configureShipmentButton;

  public ConfigureEDIPage(TestWebDriver driver) throws IOException {
    super(driver);
    PageFactory.initElements(new AjaxElementLocatorFactory(TestWebDriver.getDriver(), 10), this);
    testWebDriver.setImplicitWait(10);
    testWebDriver.waitForElementToAppear(configureOrderButton);
  }

  public ConfigureOrderPage navigateConfigureOrderPage() throws IOException {
    testWebDriver.waitForElementToAppear(configureOrderButton);
    configureOrderButton.click();

    return new ConfigureOrderPage(testWebDriver);
  }

  public ConfigureShipmentPage navigateConfigureShipmentPage() throws IOException {
    testWebDriver.waitForElementToAppear(configureShipmentButton);
      configureShipmentButton.click();

    return new ConfigureShipmentPage(testWebDriver);
  }
}