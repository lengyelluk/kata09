# Attempt to solve Kata09: Back to the Checkout

## Kata description
The detailed description can be found here: (http://codekata.com/kata/kata09-back-to-the-checkout/)

## To check the solution
1. Clone the repo

2. In the project folder run **mvn test** to verify if two basic tests are still passing

3. To add/change an item modify the file **itemsConfig.xml** under **src/main/java/resources/config**. Each item has following structure:
```xml
  <item>
        <sku>B</sku>
        <unitPrice>30</unitPrice>
        <specialPrice>2 for 45</specialPrice>
  </item>
```
4. To calculate the total price of the purchase, execute **Main class**. To add a new item to your purchase, use: 
```java
co.scan(sku)
```

5. Total price will be printed to the console after executing main class. 
