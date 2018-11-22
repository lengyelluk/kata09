import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

class CheckoutTest {

    public int price(String items) {
        Checkout co = new Checkout();
        String rulesFile = "itemsConfig.xml";
        co.loadRules(rulesFile);
        for(char ch : items.toCharArray()) {
            co.scan(String.valueOf(ch));
        }
        int total = co.total();
        return total;
    }

    @Test
    public void calculationTotalTest() {
        assertEquals(0, price(""));
        assertEquals(50, price("A"));
        assertEquals(80, price("AB"));
        assertEquals(115, price("CDBA"));
        assertEquals(100, price("AA"));
        assertEquals(130, price("AAA"));
        assertEquals(180, price("AAAA"));
        assertEquals(230, price("AAAAA"));
        assertEquals(260, price("AAAAAA"));
        assertEquals(160, price("AAAB"));
        assertEquals(175, price("AAABB"));
        assertEquals(190, price("AAABBD"));
        assertEquals(190, price("DABABA"));
    }

    @Test
    public void calculationIncrementalTest() {
        Checkout co = new Checkout();
        String rulesFile = "itemsConfig.xml";
        co.loadRules(rulesFile);
        assertEquals(0, co.total());

        co.scan("A");
        assertEquals(50, co.total());

        co.scan("B");
        assertEquals(80, co.total());

        co.scan("A");
        assertEquals(130, co.total());

        co.scan("A");
        assertEquals(160, co.total());

        co.scan("B");
        assertEquals(175, co.total());
    }
}