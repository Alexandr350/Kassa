package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ConnectBDTest {

    private ConnectBD connectBD;

    @BeforeEach
    void setUp() {
        connectBD = ConnectBD.getInstance();
    }

    @AfterEach
    void tearDown() {
        connectBD = null;
    }

    @Test
    void testGetInstance() {
        ConnectBD anotherInstance = ConnectBD.getInstance();
        assertSame(connectBD, anotherInstance, "Должны получать один и тот же экземпляр");
    }

    @Test
    void testGetConnection() {
        Connection connection = connectBD.getConnection();
        assertNotNull(connection, "Соединение не должно быть null");

        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
