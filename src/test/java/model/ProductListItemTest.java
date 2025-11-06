package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductListItemTest {

    private Product product;
    private ProductListItem productListItem;

    @BeforeEach
    void setUp() {
        product = new Product("12345", "Тестовый продукт", "Тестовый бренд", "Описание тестового продукта", 100.0, 10);
        productListItem = new ProductListItem(product);
    }

    @Test
    void testGetProduct() {
        assertEquals(product, productListItem.getProduct(), "Метод getProduct() должен возвращать правильный продукт");
    }

    @Test
    void testToString() {
        String expectedString = "Тестовый продукт (Бренд: Тестовый бренд, Артикул: 12345, Цена: 100.0)";
        assertEquals(expectedString, productListItem.toString(), "Метод toString() должен возвращать правильное представление продукта");
    }
}
