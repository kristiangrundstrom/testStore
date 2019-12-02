import lombok.Data;

import java.util.List;

@Data
public class Receipt {

    private List<ProductReceiptLine> productReceiptLines;
    private double vat;
    private double totalPrice;

    public void setProductReceiptLines(List<ProductReceiptLine> productReceiptLines) {
        this.productReceiptLines = productReceiptLines;
        setTotalPrice();
    }

    public void setTotalPrice() {
        totalPrice = productReceiptLines.stream()
                .map(e -> e.getProduct().getPrice() * e.getAmountOfItems())
                .reduce(0.0, (a, b) -> a + b);
    }

}
