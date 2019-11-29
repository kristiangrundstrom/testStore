import java.util.List;

public class Receipt {

    private List<ProductReceiptLine> productReceiptLines;
    private double vat;
    private double totalPrice;

    public List<ProductReceiptLine> getProductReceiptLines() {
        return productReceiptLines;
    }

    public void setProductReceiptLines(List<ProductReceiptLine> productReceiptLines) {
        this.productReceiptLines = productReceiptLines;
        setTotalPrice();
    }

    public double getVat() {
        return this.vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        totalPrice = productReceiptLines.stream()
                .map(e -> e.getProduct().getPrice() * e.getAmountOfItems())
                .reduce(0.0, (a, b) -> a + b);
    }

}
