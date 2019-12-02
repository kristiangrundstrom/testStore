import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class TestHoffStore {

    String url = "http://hoff.app/store";
    ChromeDriver driver;
    PageObjectStore store;
    List<Product> allProducts = new ArrayList<>();

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        store = new PageObjectStore(driver);
        driver.navigate().to(url);
        allProducts = store.getAllProducts();
    }

    @AfterMethod
    public void refresh() {
        driver.navigate().refresh();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

    @DataProvider(name = "Products")
    public Object[] allProducts() {
        return allProducts.toArray();
    }

    @Test(dataProvider = "Products")
    void testApple(Product product) {

        int amountOfItemsToBuy = 1;
        double initialSumOfMoney = store.getCurrentAmountOfMoney();
        store.selectProductToPurchase(product.getType());
        store.setNumberOfItemsToBuy(amountOfItemsToBuy);
        store.clickBuyButton();

        double expectedMoneyLeftAfterPurchase = (initialSumOfMoney - (product.getPrice() * amountOfItemsToBuy));
        double actualMoneyLeftAfterPurchase = store.getCurrentAmountOfMoney();

        List<ProductReceiptLine> productReceiptLines = new ArrayList<>();
        productReceiptLines.add(new ProductReceiptLine(product, amountOfItemsToBuy));

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
        softAssert.assertEquals(actualTotalPrice, expectedTotalPrice, "Total price for " + product.getType() + " is not matching the total price on the receipt.");
        softAssert.assertEquals(actualVat, expectedVat, "Wrong VAT was calculated for " + product.getType());
        softAssert.assertEquals(actualMoneyLeftAfterPurchase, expectedMoneyLeftAfterPurchase, "Wrong amount of money for " + product.getType() + " was deducted than expected.");
        softAssert.assertEquals(actualVATAfterSellingAllProducts, 0.0, "VAT should be 0.0 after selling " + product.getType());
        softAssert.assertEquals(actualPriceAfterSellingAllProducts, 0.0, "Total price should be 0.0 after selling " + productType);
        softAssert.assertEquals(store.getCurrentAmountOfMoney(), 10000.0, "Not all money was returned to inital sum of 10.000:-");
        softAssert.assertAll();
    }

}

