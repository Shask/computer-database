package com.excilys.computerdb.selenium;

import static org.junit.Assert.fail;

import org.junit.After ;
import org.junit.Before ;
import org.junit.Test ;
import org.openqa.selenium.Alert ;
import org.openqa.selenium.By ;
import org.openqa.selenium.NoAlertPresentException ;
import org.openqa.selenium.NoSuchElementException ;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class SearchTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * Set Up test.
   * @throws Exception if not good
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSearch() throws Exception {
    driver.get(baseUrl + "/computerdb/dashboard?page=1&nbElements=");
    driver.findElement(By.id("searchbox")).clear();
    driver.findElement(By.id("searchbox")).sendKeys("As");
    driver.findElement(By.id("searchsubmit")).click();
  }

  /**
   * tear down test.
   * @throws Exception if not good
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
