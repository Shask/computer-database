package com.excilys.computerdb.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SwitchAddDashTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSwitchAddAndDashBoard() throws Exception {
    driver.get(baseUrl + "/computerdb/dashboard?page=1&nbElements=");
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.linkText("3")).click();
    driver.findElement(By.linkText("4")).click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  @SuppressWarnings("unused")
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
  @SuppressWarnings("unused")
  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }
  @SuppressWarnings("unused")
  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}