package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void testProductCreation() {
        Product product = new Product("12345", "Laptop", "BrandA", "16GB RAM, 512GB SSD", 999.99, 10);

        Assertions.assertEquals("12345", product.getArticleNumber());
        Assertions.assertEquals("Laptop", product.getNameProduct());
        Assertions.assertEquals("BrandA", product.getBrand());
        Assertions.assertEquals("16GB RAM, 512GB SSD", product.getSpecifications());
        Assertions.assertEquals(999.99, product.getPrice());
        Assertions.assertEquals(10, product.getCount());
    }

    @Test
    public void testSettersAndGetters() {
        Product product = new Product("12345", "Laptop", "BrandA", "16GB RAM, 512GB SSD", 999.99, 10);

        product.setNameProduct("Gaming Laptop");
        product.setPrice(1099.99);
        product.setCount(5);

        Assertions.assertEquals("Gaming Laptop", product.getNameProduct());
        Assertions.assertEquals(1099.99, product.getPrice());
        Assertions.assertEquals(5, product.getCount());
    }

    @Test
    public void testEqualsAndHashCode() {
        Product product1 = new Product("12345", "Laptop", "BrandA", "16GB RAM, 512GB SSD", 999.99, 10);
        Product product2 = new Product("12345", "Laptop", "BrandA", "16GB RAM, 512GB SSD", 999.99, 10);
        Product product3 = new Product("67890", "Smartphone", "BrandB", "128GB Storage", 499.99, 20);

        Assertions.assertEquals(product1, product2); // Должны быть равны
        Assertions.assertNotEquals(product1, product3); // Не равны
        Assertions.assertEquals(product1.hashCode(), product2.hashCode()); // Хэш-коды должны совпадать
    }

    @Test
    public void testToString() {
        Product product = new Product("12345", "Laptop", "BrandA", "16GB RAM, 512GB SSD", 999.99, 10);
        String expectedString = "Product{articleNumber='12345', nameProduct='Laptop', brand='BrandA', specifications='16GB RAM, 512GB SSD', price=999.99, count=10}";
        Assertions.assertEquals(expectedString, product.toString());
    }
}
