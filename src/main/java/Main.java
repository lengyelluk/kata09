public class Main {

    public static void main(String[] args) {
        Checkout co = new Checkout();
        //can be changed with a new rules
        String rulesFile = "itemsConfig.xml";
        //load new rules
        co.loadRules(rulesFile);
        //scan items
        co.scan("A");
        co.scan("B");
        co.scan("C");
        co.scan("B");
        //calculate the total price
        Integer totalPrice = co.total();
        //display the total price
        System.out.println("Total price of \"ABCB\" is: " + totalPrice);
    }


}
