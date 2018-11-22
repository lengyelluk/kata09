import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item {

    private String sku;
    private Integer quantity;


    public Item(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

}
