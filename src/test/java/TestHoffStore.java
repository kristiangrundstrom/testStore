import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestHoffStore {

    String url = "http://hoff.app/store";
    ChromeDriver driver;
    PageObjectStore store;
    WebDriverWait wait;
    List<Product> allProducts;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        store = new PageObjectStore(driver);
        driver.navigate().to(url);
        allProducts = store.getAllProducts();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

    @Test(dataProvider = "Samsung S5")
    void testApple(String productType, double productPrice) {

        int amountOfItemsToBuy = 1;
        double initialSumOfMoney = store.getCurrentAmountOfMoney();
        store.selectProductToPurchase(productType);
        store.setNumberOfItemsToBuy(amountOfItemsToBuy);
        store.clickBuyButton();

        double expectedMoneyLeftAfterPurchase = (initialSumOfMoney - (productPrice * amountOfItemsToBuy));
        double actualMoneyLeftAfterPurchase = store.getCurrentAmountOfMoney();

        List<ProductReceiptLine> productReceiptLines = new ArrayList<>();
        productReceiptLines.add(new ProductReceiptLine(new Product(productType, productPrice), amountOfItemsToBuy));

        Receipt receipt = new Receipt();
        receipt.setVat(0.25);
        receipt.setProductReceiptLines(productReceiptLines);

        double actualVat = store.getTotalVATFromReceipt();
        double expectedVat = receipt.getTotalPrice() * receipt.getVat();
        double actualTotalPrice = store.getTotalSumFromReceipt();
        double expectedTotalPrice = receipt.getTotalPrice();

        store.sellAllProducts();

        double actualVATAfterSellingAllProducts = store.getTotalVATFromReceipt();
        double actualPriceAfterSellingAllProducts = store.getTotalSumFromReceipt();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualTotalPrice, expectedTotalPrice, "Total price is not matching the total price on the receipt.");
        softAssert.assertEquals(actualVat, expectedVat, "Wrong VAT was calculated for" + productType);
        softAssert.assertEquals(actualMoneyLeftAfterPurchase, expectedMoneyLeftAfterPurchase, "Wrong amount of money was deducted than expected.");
        softAssert.assertEquals(actualVATAfterSellingAllProducts, 0.0, "VAT should be 0.0 after selling all products!");
        softAssert.assertEquals(actualPriceAfterSellingAllProducts, 0.0, "Total price should be 0.0 after selling all products!");
        softAssert.assertAll();
    }


    public Product findProductByName(String productType) {
        return store.getAllProducts().stream()
                .filter(product -> productType.equalsIgnoreCase(product.getType())).collect(Collectors.toList()).get(0);
    }

    @DataProvider(name = "Apple")
    public Object[][] provideApple() {
        Product product = findProductByName("Apple");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Banana")
    public Object[][] provideBanana() {
        Product product = findProductByName("Banana");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Orange")
    public Object[][] provideOrange() {
        Product product = findProductByName("Orange");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Grape")
    public Object[][] provideGrape() {
        Product product = findProductByName("Grape");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Bicycle")
    public Object[][] provideBicycle() {
        Product product = findProductByName("Bicycle");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Samsung S5")
    public Object[][] provideSamsungS5() {
        Product product = findProductByName("Samsung S5");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Toy train")
    public Object[][] provideToytrain() {
        Product product = findProductByName("Toy train");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Cup of Coffee")
    public Object[][] provideCupOfCoffee() {
        Product product = findProductByName("Cup of Coffee");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "Chair")
    public Object[][] provideChair() {
        Product product = findProductByName("Chair");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }

    @DataProvider(name = "TV")
    public Object[][] provideTV() {
        Product product = findProductByName("TV");
        return new Object[][]{{product.getType(), product.getPrice()}};
    }
}

