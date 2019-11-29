public class ProductReceiptLine {

    private Product product;
    private int amountOfItems;

    public ProductReceiptLine(Product product, int amountOfItems) {
        this.product = product;
        this.amountOfItems = amountOfItems;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }
}
