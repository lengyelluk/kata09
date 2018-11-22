import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rules {

    private final static String ITEMS_CONFIG_FOLDER = "config";

    //used to find a rulesFile
    private static File getConfigurationFile(String rulesFile) {
        File file = new File(Rules.class.getClassLoader().getResource(ITEMS_CONFIG_FOLDER + "/" + rulesFile).getFile());
        return file;
    }

    private static Document getConfigurationXmlDocument(String rulesFile) {
        // xml file with configuration for each item
        File xmlFile = getConfigurationFile(rulesFile);

        // build xml document
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            System.out.println("Failed to parse the xml with Items Configuration: " + xmlFile.getAbsolutePath());
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("Failed to parse the xml with items configuration: " + xmlFile.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not find the xml with items configuration: " + xmlFile.getAbsolutePath());
            e.printStackTrace();
        }
        return doc;
    }

    //used to get the rules from rulesFile in a format that can be used in Calculator
    //sku_unitPrice/specialPriceQuantity_specialPrice
    public static List<String> getRules(String rulesFile) {
        // build XML Document
        Document doc = getConfigurationXmlDocument(rulesFile);

        // create List to store item config in usable format
        List<String> itemConfig = new ArrayList<>();

        NodeList itemNodes = doc.getElementsByTagName("item");
        for(int i=0; i < itemNodes.getLength(); i++) {
            Element itemElement = (Element) itemNodes.item(i);

            Element skuElement = (Element) itemElement.getElementsByTagName("sku").item(0);
            String sku = skuElement.getTextContent();

            Element unitPriceElement = (Element) itemElement.getElementsByTagName("unitPrice").item(0);
            String unitPrice = unitPriceElement.getTextContent();

            Element specialPriceElement = (Element) itemElement.getElementsByTagName("specialPrice").item(0);
            String specialPrice = specialPriceElement.getTextContent();
            String specialPriceToStore = getSpecialPriceToStore(specialPrice);

            itemConfig.add(sku + "_" + unitPrice + "/" + specialPriceToStore);
        }

        return itemConfig;
    }

    //find a first number as specialPriceQuantity and last as specialPrice
    public static String getSpecialPriceToStore(String specialPrice) {
        String quantity = "-1";
        String price = "-1";
        String numberRegEx = "\\d+";

        Pattern p = Pattern.compile(numberRegEx);
        Matcher m = p.matcher(specialPrice);
        if(m.find()) {
            quantity = m.group(0);
        }

        while(m.find()) {
            price = m.group(0);
        }

        String finalString = quantity + "_" + price;

        return finalString;
    }

}
