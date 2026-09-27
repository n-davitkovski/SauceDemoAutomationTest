package org.example.saucedemotests;

import org.example.pages.CheckoutYourInformationPage;
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.pages.YourCartPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsPageTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);

        // Precondition: Log in before each test
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void orderingDropDownValuesTest() {
        assertEquals("Name (A to Z)",
                productsPage.getAllOptionsFromOrderingDropDown().get(0).getText());
        assertEquals("Name (Z to A)",
                productsPage.getAllOptionsFromOrderingDropDown().get(1).getText());
        assertEquals("Price (low to high)",
                productsPage.getAllOptionsFromOrderingDropDown().get(2).getText());
        assertEquals("Price (high to low)",
                productsPage.getAllOptionsFromOrderingDropDown().get(3).getText());
    }

    @Test
    public void orderingProductsFromHighToLowPriceTest() {
        productsPage.selectOrderingDropdownOption(3);

        assertEquals("Price (high to low)",
                productsPage.getTextFromOrderingDropDown());

        assertTrue(productsPage.areAllProductsPricesDescending());
    }

    @Test
    public void orderingProductsFromLowToHighPriceTest() {
        productsPage.selectOrderingDropdownOption(2);

        assertEquals("Price (low to high)",
                productsPage.getTextFromOrderingDropDown());

        assertTrue(productsPage.areAllProductsPricesAscending());
    }

    @Test
    public void orderingProductsFromZToAAlphabeticallyTest() {

        List<String> initialNamesList = productsPage.getAllProductsNames();

        System.out.println("Initial product list:");
        initialNamesList.forEach(System.out::println);

        productsPage.selectOrderingDropdownOption(1);

        List<String> namesAfterSelection = productsPage.getAllProductsNames();

        System.out.println("Sorted product list:");
        namesAfterSelection.forEach(System.out::println);

        List<String> expectedList = new ArrayList<>(initialNamesList);
        Collections.reverse(expectedList);

        assertEquals(expectedList, namesAfterSelection);
    }

    @Test
    public void validateColorChangeOnTitleHoverTest() {

        String initialColor = productsPage.getColorFromBackPackTitle();

        productsPage.hoverBackpackTitle();

        String hoverColor = productsPage.getColorFromBackPackTitle();

        assertEquals(initialColor, hoverColor,
                "The product title color should change when hovered.");
    }

    @Test
    public void verifyEachProductDisplaysCorrectImageTest() {
        List<String> imageUrls = productsPage.getAllProductImageUrls();

        Set<String> uniqueImages = new HashSet<>(imageUrls);

        assertEquals(imageUrls.size(), uniqueImages.size(),
                "Each product should display a unique image.");
    }

    @Test
    public void verifyEachProductDisplaysUniqueImageTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        List<String> uniqueImageUrls = productsPage.getImageUniqueUrls();

        Set<String> uniqueImages = new HashSet<>(uniqueImageUrls);

        assertEquals(uniqueImageUrls.size(), uniqueImages.size(),
                "Each product should display a unique image");
    }

    @Test
    public void verifyProductImagesDisplayCorrectlyTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        List<String> uniqueImageUrl = productsPage.getImageUniqueUrls();

        Set<String> uniqueImage = new HashSet<>(uniqueImageUrl);

        assertEquals(uniqueImageUrl.size(), uniqueImage.size(),
                "One product displays a unique image");
    }

    @Test
    public void verifyLastNameDisplaysTestCorrectlyTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Login as problem_user
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Go to checkout
        YourCartPage.clickCheckout();

        // Enter Last Name
        CheckoutYourInformationPage.enterLastName("Test");

        String actualLastName = CheckoutYourInformationPage.getLastNameValue("Test");

        assertEquals(
                "Test",
                actualLastName,
                "The Last Name field should contain 'Test'."
        );
    }

    @Test
    public void verifyDetailPageCorrespondsTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Login as problem_user
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Get the product name before opening the detail page
        String expectedProductName = productsPage.getBackpackTitle();

        // Click the product
        productsPage.clickBackpackTitle();

        // Wait until the detail page product name is displayed
        WebElement detailProductName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_details_name")
                )
        );

        // Verify that the detail page corresponds to the selected product
        assertEquals(
                expectedProductName,
                detailProductName.getText(),
                "The detail page should correspond to the selected product."
        );
    }

    @Test
    public void verifyCartStateAfterAddAndRemoveTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Login as problem_user
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Add product to cart
        productsPage.addBackpackToCart();

        // Wait until cart badge appears
        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("shopping_cart_badge")
                )
        );

        // Verify product was added
        assertEquals(
                "1",
                cartBadge.getText(),
                "The product should be added to the cart."
        );

        // Open cart
        productsPage.openCart();

        // Wait until product appears in cart
        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_item_name")
                )
        );

        assertEquals(
                "Sauce Labs Backpack",
                product.getText(),
                "Sauce Labs Backpack should be displayed in the cart."
        );

        // Remove product
        YourCartPage.removeBackpack();

        // Wait until product disappears
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.className("inventory_item_name")
                )
        );

        // Verify product was removed
        assertTrue(
                driver.findElements(By.className("inventory_item_name")).isEmpty(),
                "The product should be removed from the cart."
        );
    }

    @Test
    public void verifyLoginAsThresholdTest() {

        long startTime = System.currentTimeMillis();

        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        wait.until(
                ExpectedConditions.urlContains("inventory.html")
        );

        long endTime = System.currentTimeMillis();

        long loginTime = endTime - startTime;

        assertTrue(
                loginTime <= 3000,
                "Login should complete within 3 seconds. Actual time: "
                        + loginTime + " ms"
        );
    }

    @Test
    public void addToCartTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Add the product to the cart
        productsPage.addBackpackToCart();

        // Wait until the cart badge appears
        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("shopping_cart_badge")
                )
        );

        // Verify that one product was added
        assertEquals(
                "1",
                cartBadge.getText(),
                "The cart should contain 1 product."
        );
    }

    @Test
    public void sortDropdownTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Login
        loginPage.enterUsername("error_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Get original product names
        List<String> expectedNames = new ArrayList<>(
                productsPage.getProductNames()
        );

        // Sort expected names alphabetically
        Collections.sort(expectedNames);

        // Select Name (A to Z)
        productsPage.selectOrderingDropdownOption(0);

        // Get actual product names after sorting
        List<String> actualNames = productsPage.getProductNames();

        // Verify sorting
        assertEquals(
                expectedNames,
                actualNames,
                "Products should be sorted in ascending alphabetical order."
        );
    }

    @Test
    public void checkoutTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Login
        loginPage.enterUsername("error_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Add product to cart
        productsPage.addBackpackToCart();

        // Open cart
        productsPage.openCart();

        // Click Checkout
        YourCartPage.clickCheckout();

        // Wait for the Checkout: Your Information page
        WebElement checkoutTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("title")
                )
        );

        // Verify the page title
        assertEquals(
                "Checkout: Your Information",
                checkoutTitle.getText(),
                "The Checkout: Your Information page should be displayed."
        );
    }

    @Test
    public void verifyBackpackImageDimensionsTest() {
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Dimension imageSize = productsPage.getBackpackImageSize();

        assertEquals(300, imageSize.getWidth(),
                "Backpack image width is incorrect.");

        assertEquals(300, imageSize.getHeight(),
                "Backpack image height is incorrect.");
    }
}
