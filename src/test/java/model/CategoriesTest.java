package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;

public class CategoriesTest {

    @Test
    public void testGetAllCategories() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM categories")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false); // Две записи
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Category1", "Category2");

        List<Categories> categoriesList = Categories.getAllCategories(mockConnection);

        Assertions.assertEquals(2, categoriesList.size());
        Assertions.assertEquals(1, categoriesList.get(0).getId());
        Assertions.assertEquals("Category1", categoriesList.get(0).getName());
        Assertions.assertEquals(2, categoriesList.get(1).getId());
        Assertions.assertEquals("Category2", categoriesList.get(1).getName());

        verify(mockConnection).createStatement();
        verify(mockStatement).executeQuery("SELECT * FROM categories");
        verify(mockResultSet, times(3)).next(); // Должен быть вызван 3 раза
        verify(mockResultSet, times(2)).getInt("id"); // Должен быть вызван 2 раза
        verify(mockResultSet, times(2)).getString("name"); // Должен быть вызван 2 раза
    }

    @Test
    public void testAddCategory() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement("INSERT INTO categories (name) VALUES (?)")).thenReturn(mockPreparedStatement);

        Categories.addCategory(mockConnection, "NewCategory");

        verify(mockPreparedStatement).setString(1, "NewCategory");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteCategory() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement("DELETE FROM categories WHERE id = ?")).thenReturn(mockPreparedStatement);

        Categories.deleteCategory(mockConnection, 1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}
