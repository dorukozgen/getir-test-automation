package pages;

import managers.DriverManagement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    @FindBy(xpath = "//input[@type='tel']")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement continueButton;

    public MainPage() {
        PageFactory.initElements(DriverManagement.getDriver(), this);
    }
}
