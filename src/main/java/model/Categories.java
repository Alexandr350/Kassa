package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Categories {
    private int id;
    private String name;
    public Categories(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public static List<Categories> getAllCategories(Connection connection) throws SQLException {
        List<Categories> categoriesList = new ArrayList<>();
        String query = "SELECT * FROM categories";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                categoriesList.add(new Categories(id, name));
            }
        }
        return categoriesList;
    }
    public static void addCategory(Connection connection, String name) throws SQLException {
        String query = "INSERT INTO categories (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
    public static void deleteCategory(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
