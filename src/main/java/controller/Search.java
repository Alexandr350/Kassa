package controller;

import model.ConnectBD;
import model.Product;
import model.ShopBasket;
import view.ProductDisplayPanel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {
    private ShopBasket shopBasket;

    public Search(ProductDisplayPanel productDisplayPanel) {
        shopBasket = new ShopBasket(productDisplayPanel);

    }
    public List<Product> searchProducts(String productName) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name_product LIKE ?";

        try (Connection connection = ConnectBD.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String articleNumber = resultSet.getString("article_number");
                String nameProduct = resultSet.getString("name_product");
                String brand = resultSet.getString("brand");
                String specifications = resultSet.getString("specifications");
                double price = resultSet.getDouble("price");
                int count = resultSet.getInt("count");

                Product product = new Product(articleNumber, nameProduct, brand, specifications, price, count);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public void addToBasket(Product product) {
        shopBasket.addProduct(product);

    }
    public ShopBasket getShopBasket() {
        return shopBasket;

    }

}
