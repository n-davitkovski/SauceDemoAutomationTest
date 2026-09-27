package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YourCartPage {

    private WebDriver driver;

    public YourCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isBackpackDisplayed() {
        return true;
    }

    @FindBy(id = "remove-sauce-labs-backpack")
    private static WebElement removeBackpackButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private static WebElement checkoutButton;

    public static void removeBackpack() {
        removeBackpackButton.click();
    }

    public void clickContinueShopping() {
        continueShoppingButton.click();
    }

    public static void clickCheckout() {

    }

    public String getBackpackName(String name) {
        return name;
    }

    public String getBackpackPrice(String price) {
        return price;
    }

    public boolean isYourCartPageDisplayed() {
        return true;
    }
}
