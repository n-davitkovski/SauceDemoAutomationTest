package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    Actions actions;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    private By productsTitle = By.className("title");
    private By dropdownSorting = By.className("product_sort_container");
    private By productsNamesList = By.className("inventory_item_name");
    private By backbackProductTitle = By.xpath("//*[@id=\"item_4_title_link\"]");
    private By productsPriceList = By.className("inventory_item_price");

    public String productsTextDisplayed() {
        return driver.findElement(productsTitle).getText();
    }

    public boolean isProductsTextDisplayed() {
        return driver.findElement(productsTitle).getText().equals("Products");
    }

    public List<WebElement> getAllOptionsFromOrderingDropDown() {
        Select orderingDropDown = new Select(driver.findElement(dropdownSorting));

        return orderingDropDown.getOptions();
    }

    public void selectOrderingDropdownOption(int optionIndex) {
        Select orderingDropDown = new Select(driver.findElement(dropdownSorting));
        orderingDropDown.selectByIndex(optionIndex);
    }

    public String getTextFromOrderingDropDown() {
        Select orderingDropDown = new Select(driver.findElement(dropdownSorting));
        return orderingDropDown.getFirstSelectedOption().getText();
    }

    public boolean areAllProductsPricesDescending() {
        List<Double> productPrice = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(productsPriceList);

        for (int i = 0; i < priceElements.size(); i++) {
            productPrice.add(Double.parseDouble(priceElements.get(i).getText().substring(1)));
        }

        for (int i = 0; i < productPrice.size() - 1; i++) {
            if (productPrice.get(i) < productPrice.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllProductsPricesAscending() {
        List<Double> productPrice = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(productsPriceList);

        for (int i = 0; i < priceElements.size(); i++) {
            productPrice.add(Double.parseDouble(priceElements.get(i).getText().substring(1)));
        }

        for (int i = 0; i < productPrice.size() - 1; i++) {
            if (productPrice.get(i) > productPrice.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getAllProductsNames() {
        List<String> productNames = new ArrayList<>();

        List<WebElement> nameElements = driver.findElements(productsNamesList);

        for (int i = 0; i < nameElements.size(); i++) {
            productNames.add(nameElements.get(i).getText());
        }
        return productNames;
    }

    public void hoverBackpackTitle(){
        WebElement backPackTitle = driver.findElement(backbackProductTitle);

        actions.moveToElement(backPackTitle).perform();
    }

    public String getColorFromBackPackTitle(){
        Color loginBtnBackgroundColor = Color.fromString(driver.findElement(backbackProductTitle).getCssValue("color"));
        return loginBtnBackgroundColor.asHex();
    }



    public void addBackpackToCart() {

    }

    public void openCart() {

    }

    public List<String> getAllProductImageUrls() {
        List<WebElement> images = driver.findElements(By.cssSelector(".inventory_item_img img"));

        List<String> imageUrls = new ArrayList<>();

        for (WebElement image : images) {
            imageUrls.add(image.getAttribute("src"));
        }

        return imageUrls;
    }

    public Dimension getBackpackImageSize() {
        WebElement image = driver.findElement(
                By.cssSelector("#item_4_img_link img"));
        return image.getSize();
    }

    public List<String> getImageUniqueUrls(){
        List<WebElement> uniqueImages = driver.findElements(By.cssSelector(".inventory_item_img img"));

        List<String> uniqueimageUrls = new ArrayList<>();

        for(WebElement uniqueimage : uniqueImages){
            uniqueimageUrls.add(uniqueimage.getAttribute("src"));
        }

        return uniqueimageUrls;
    }

    public String getLastNameValue(){
        WebElement lastname = driver.findElement(
                By.name("Test")
        );
        return lastname.getText();
    }

    public String isDetailPageCorrespondable(){
        WebElement title = driver.findElement(
                By.name("Sauce Labs Bolt T-Shirt")
        );
        if(title.isDisplayed()){
            return "The title of the product is Sauce Labs Bolt T-Shirt";
        }
        return title.getText();
    }

    public String addAndRemoveProductFromCart(){
        WebElement title = driver.findElement(
          By.name("Sauce Labs Onesie")
        );
        if(title.isSelected()){
            return "The cart contains a product name";
        } else{
            return "The cart has no product name";
        }
    }

    public String loginAsperformanceglitchuser(){
        WebElement username = driver.findElement(
                By.name("performance_glitch_user")
        );
        if(username.isSelected()){
            return "The login is asserted as threshold";
        }
        return username.getText();
    }

    public String addToCart(){
        WebElement product = driver.findElement(
                By.name("Sauce Labs Backpack")
        );
        return product.getText();
    }

    public String sortDropdown(){
        WebElement dropdown = driver.findElement(
                By.name("Name A to Z")
        );
        return dropdown.getText();
    }

    public String checkout(){
        WebElement checkout = driver.findElement(
                By.name("Checkout: Your Information Page")
        );
        return checkout.getText();
    }

    public String getBackpackTitle() {
        WebElement productTitle = driver.findElement(
                By.name("Sauce Labs Backpack")
        );
        return productTitle.getText();
    }

    public void clickBackpackTitle() {
    }

    public List<String> getProductNames() {
        return Collections.singletonList(productsTitle.toString());
    }

    public Object getSelectedOrderingOption(Object product) {
        return productsNamesList.toString();
    }
}
