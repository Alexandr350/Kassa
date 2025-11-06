package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import view.ProductDisplayPanel;

import javax.swing.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopBasketTest {
    private ShopBasket shopBasket;
    private ProductDisplayPanel mockProductDisplayPanel;
    private JPanel mockProductPanel;

    @BeforeEach
    void setUp() {
        mockProductDisplayPanel = Mockito.mock(ProductDisplayPanel.class);
        mockProductPanel = new JPanel();
        Mockito.when(mockProductDisplayPanel.getProductPanel()).thenReturn(mockProductPanel);
        shopBasket = new ShopBasket(mockProductDisplayPanel);
    }

    @Test
    void testAddProduct_NewProduct() {
        Product product = new Product(
                "001",
                "Test Product",
                "Brand A",
                "Specification",
                100.0,
                10
        );

        shopBasket.addProduct(product);

        Map<Product, Integer> shoppingMap = ShopBasket.getShoppingMap();
        double totalSum = ShopBasket.getTotalSum();

        assertEquals(1, shoppingMap.size(), "Корзина должна содержать 1 продукт");
        assertTrue(shoppingMap.containsKey(product), "Продукт должен быть в корзине");
        assertEquals(1, shoppingMap.get(product), "Количество продукта должно быть 1");
        assertEquals(100.0, totalSum, 0.01, "Общая сумма должна равняться цене продукта");
    }

    @Test
    void testAddProduct_ExistingProduct() {
        Product product = new Product("001", "Test Product", "Brand A", "Specification", 100.0, 10);
        shopBasket.addProduct(product);
        shopBasket.addProduct(product);
        Map<Product, Integer> shoppingMap = ShopBasket.getShoppingMap();
        assertEquals(1, shoppingMap.size(), "Корзина должна содержать один продукт.");
        assertEquals(2, shoppingMap.get(product), "Количество продукта должно быть 2.");
        assertEquals(200.0, ShopBasket.getTotalSum(), "Общая сумма должна быть 200.0.");
    }

    @Test
    void testClear() {
        Product product = new Product("001", "Test Product", "Brand A", "Specification", 100.0, 10);
        shopBasket.addProduct(product);
        shopBasket.clear();

        assertTrue(ShopBasket.getShoppingMap().isEmpty());
        assertEquals(0.0, ShopBasket.getTotalSum());
    }

    @Test
    void testToString() {
        Product product = new Product("001", "Test Product", "Brand A", "Specification", 100.0, 10);
        shopBasket.addProduct(product);
        String expected = "ShopBasket{shoppingList={Product{" +
                "articleNumber='001', nameProduct='Test Product', brand='Brand A', specifications='Specification', price=100.0, count=10" +
                "}=1}, totalSum=100.0}";
        assertEquals(expected, shopBasket.toString());
    }
}
