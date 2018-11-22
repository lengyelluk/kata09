import java.util.HashMap;
import java.util.Map;

public class Checkout {

    //map with the scanned items saved as key = sku and values(sku, quantity)
    private Map<String, Item> items = new HashMap<>();
    private Calculator calculator = new Calculator();

    //use to load actual prices from rulesFile
    public void loadRules(String rulesFile) {

        calculator.loadRules(rulesFile);
    }

    //used to add item to the list after scanning
    public void scan(String scannedValue) {
        //create an item to represent scanned item
        Item scannedItem = new Item(scannedValue);
        //find the item in the scanned items map using sku as a key
        Item item = items.get(scannedItem.getSku());
        //if it is not in the map = the same kind of item has not been scanned yet
        //=> set quantity to 1
        if (item == null) {
            scannedItem.setQuantity(1);
            items.put(scannedValue, scannedItem);
        }
        // if it is already in the map, increase quantity by one
         else {
            Integer quantity = item.getQuantity();
            item.setQuantity(quantity+=1);
        }
    }

    //calculate the total price
    public int total() {
        Integer totalPrice = 0;
        for(Item item : items.values()) {
            totalPrice += calculator.calculatePrice(item);
        }
        return totalPrice;
    }
}
