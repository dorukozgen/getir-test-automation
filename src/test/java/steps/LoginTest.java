package steps;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.DriverManagement;
import org.openqa.selenium.WebElement;
import pages.MainPage;
import utils.ReusableMethods;

public class LoginTest {

    private MainPage mainPage;

    @Given("Navigate to {string}")
    public void goToUrl(String url) {
        DriverManagement.getDriver().get(url);
        ReusableMethods.waitFor(2);
        mainPage = new MainPage();
    }

    @Then("Enter {string} to phone number input on website")
    public void enterPhoneDesktop(String phoneNumber) {
        mainPage.phoneNumberInput.sendKeys(phoneNumber);
        ReusableMethods.waitFor(2);
    }

    @Then("Click \"Continue\" button on website")
    public void clickContinueButtonDesktop() {
        mainPage.continueButton.click();
        ReusableMethods.waitFor(3);
    }

    @Given("Click \"Get Started\" button when app is opened")
    public void clickGetStartedButton() {
        WebElement getStartedButton = DriverManagement.getWait().until(driver -> driver.findElement(AppiumBy.id("com.getir:id/get_started")));
        getStartedButton.click();
    }

    @When("popup is displayed dismiss it")
    public void dismissPopup() {
        ReusableMethods.waitFor(2);
        DriverManagement.getDriver().switchTo().alert().dismiss();
        ReusableMethods.waitFor(2);
    }

    @Then("Click \"Continue with phone\" button")
    public void clickContinueWithPhoneButton() {
        WebElement continueWithPhoneButton = DriverManagement.getWait().until(driver -> driver.findElement(AppiumBy.id("com.getir:id/phone_button")));
        continueWithPhoneButton.click();
    }

    @Then("Enter {string} to phone number input on app")
    public void enterPhoneMobile(String phone) {
        WebElement phoneInput = DriverManagement.getWait().until(driver -> driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.widget.EditText")));
        phoneInput.sendKeys(phone);
    }

    @Then("Click \"Continue\" button on app")
    public void clickContinueButtonMobile() {
        WebElement continueButton = DriverManagement.getWait().until(driver -> driver.findElement(AppiumBy.id("com.getir:id/get_started")));
        continueButton.click();
        ReusableMethods.waitFor(3);
    }


}
