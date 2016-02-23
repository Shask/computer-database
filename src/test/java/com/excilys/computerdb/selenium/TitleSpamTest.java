package com.excilys.computerdb.selenium;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class TitleSpamTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * SetUp.
   * 
   * @throws Exception
   *           if fails
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTitle() throws Exception {
    driver.get(baseUrl + "/computerdb/dashboard?search=As");
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
    driver.findElement(By.linkText("Application - Computer Database")).click();
  }

  /**
   * tearDown.
   * @throws Exception if fails
   */
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if ( !"".equals(verificationErrorString) ) {
      fail(verificationErrorString);
    }
  }

  @SuppressWarnings("unused")
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch ( NoSuchElementException e ) {
      return false;
    }
  }

  @SuppressWarnings("unused")
  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch ( NoAlertPresentException e ) {
      return false;
    }
  }

  @SuppressWarnings("unused")
  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if ( acceptNextAlert ) {
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
