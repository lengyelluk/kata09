import java.util.List;

public class Calculator {

    //list of unit prices and special prices of each sku loaded from rulesFile
    private List<String> itemsConfig;

    //use to load actual prices from rulesFile
    public void loadRules(String rulesFile) {
        this.itemsConfig = Rules.getRules(rulesFile);
    }

    //this method in an over-complicated way calculate the price of an item
    public Integer calculatePrice(Item item) {
        Integer total = 0;
        String unitPriceString = "";
        String specialPriceString = "";
        String specialPriceQuantityString = "";

        String sku = item.getSku();
        Integer quantity = item.getQuantity();

        //loop through rulesFile until matching sku is found
        for(String itemInConfig : itemsConfig) {
            String skuInList = itemInConfig.substring(0, itemInConfig.indexOf("_"));
            //get the unit price, specialPrice and specialPriceQuantity
            if(skuInList.equals(sku)) {
                unitPriceString = itemInConfig.substring(skuInList.length() + 1, itemInConfig.indexOf("/"));
                specialPriceQuantityString = itemInConfig.substring(itemInConfig.indexOf("/") + 1, itemInConfig.lastIndexOf("_"));
                specialPriceString = itemInConfig.substring(itemInConfig.lastIndexOf("_") + 1);
                break;
            }
        }

        Integer unitPrice = Integer.parseInt(unitPriceString);
        Integer specialPrice = Integer.parseInt(specialPriceString);
        Integer specialPriceQuantity = Integer.parseInt(specialPriceQuantityString);

        //no specialPrice or not enough quantity to meet specialPriceQuantity limit
        //=> standard unit price should be used
        if(specialPrice == -1 || quantity < specialPriceQuantity)
            total = unitPrice * quantity;
        //calculate price using specialPrice
        else {
            int specialTotal = quantity / specialPriceQuantity;
            total = specialTotal * specialPrice;
            int remainder = quantity % specialPriceQuantity;
            total += (unitPrice * remainder);
        }
        return total;
    }



}
