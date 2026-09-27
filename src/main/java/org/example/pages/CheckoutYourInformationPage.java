package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutYourInformationPage {

    private WebDriver driver;

    public CheckoutYourInformationPage(WebDriver driver) {
        this.driver = driver;
    }

    public static String getLastNameValue(String lastname) {
        return lastname.toString();
    }


    public boolean isCheckoutInformationPageDisplayed() {
        return true;
    }

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private static WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    public void enterFirstName(String firstname) {
        firstNameField.sendKeys(firstname);
    }

    public static void enterLastName(String lastname) {
        lastNameField.sendKeys(lastname);
    }

    public void enterPostalCode(String postalcode) {
        postalCodeField.sendKeys(postalcode);
    }

    public void clickContinue() {
        continueButton.sendKeys();
    }

    public String getErrorMessage(String message) {
        return message;
    }

    public void clickCancel() {
        cancelButton.click();
    }
}
