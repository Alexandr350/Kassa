package model;

import view.CardProduct;
import view.ProductDisplayPanel;

import java.util.HashMap;
import java.util.Map;

public class ShopBasket {
    private ProductDisplayPanel productDisplayPanel;
    private static Map<Product, Integer> shoppingMap = new HashMap<>(); // Инициализация
    private Map<Product, CardProduct> cardMap = new HashMap<>();
    private static double summa = 0.00;

    // Конструктор, принимающий ProductDisplayPanel
    public ShopBasket(ProductDisplayPanel productDisplayPanel) {
        this.productDisplayPanel = productDisplayPanel;
    }

    public static void clear() {
        shoppingMap.clear();
        summa = 0.0;
    }

    public void addProduct(Product product) {
        if (shoppingMap.containsKey(product)) {
            int count = shoppingMap.get(product);
            shoppingMap.put(product, count+1);
            CardProduct cardProduct = cardMap.get(product);
            if (cardProduct != null) {
                cardProduct.updateCount(count + 1);
            }
        } else {
            shoppingMap.put(product, 1);
            CardProduct newCardProduct = new CardProduct(product, 1);
            productDisplayPanel.getProductPanel().add(newCardProduct.getCard());
            cardMap.put(product, newCardProduct);
        }
        summa += product.getPrice();
    }

    public static Map<Product, Integer> getShoppingMap() {
        return shoppingMap;
    }

    public static Double getTotalSum() {
        return summa;
    }

    @Override
    public String toString() {
        return "ShopBasket{" +
                "shoppingList=" + shoppingMap +
                ", totalSum=" + summa +
                '}';
    }
}
