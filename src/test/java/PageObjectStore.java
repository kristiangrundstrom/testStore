import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class PageObjectStore {

    ChromeDriver driver;

    public PageObjectStore(ChromeDriver driver) {
        this.driver = driver;
    }

    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();
        List<WebElement> webElements;

        webElements = driver.findElements(By.cssSelector("#productList tr"));

        webElements.forEach(e -> products.add(
                new Product(
                        e.findElement(By.id("prod-name")).getText(),
                        Double.parseDouble(e.findElement(By.id("prod-price")).getText()))
                )
        );

        return products;
    }

    public void selectProductToPurchase(String product) {
        Select selectProductToPurchase = new Select(driver.findElement(By.id("select-product")));
        selectProductToPurchase.selectByVisibleText(product);
    }

    public double getCurrentAmountOfMoney() {
        return Double.parseDouble(driver.findElement(By.id("money")).getText());
    }

    public void clickBuyButton() {
        driver.findElement(By.id("button-buy-product")).click();
    }

    public void setNumberOfItemsToBuy(int numberOfProductsToBuy) {
        driver.findElement(By.id("buyAmount")).sendKeys(String.valueOf(numberOfProductsToBuy));
    }

    public double getTotalVATFromReceipt() {
        return Double.parseDouble(driver.findElement(By.id("totalVAT")).getText());
    }

    public double getTotalSumFromReceipt() {
        return Double.parseDouble(driver.findElement(By.id("totalPrice")).getText());
    }

    public void sellAllProducts() {
        driver.findElements(By.cssSelector("#bought button")).forEach(e -> e.click());
    }

}

