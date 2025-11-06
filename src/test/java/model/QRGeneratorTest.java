package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class QRGeneratorTest {
    private static final String QR_CODE_DIRECTORY = "src/main/resources/images";
    private static final String QR_CODE_FILE = QR_CODE_DIRECTORY + "/qrcode.png";

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(Paths.get(QR_CODE_FILE));
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(QR_CODE_FILE));
    }

    @Test
    void testGenerateQRCode_createsDirectoryAndFile() throws Exception {

        ImageIcon qrCode = QRGenerator.generateQRCode("Test QR Code", 200, 200);
        assertTrue(Files.exists(Paths.get(QR_CODE_FILE)), "QR-код файл должен быть создан");
        assertNotNull(qrCode, "QR-код не должен быть null");
    }

    @Test
    void testGenerateQRCode_withInvalidText() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            QRGenerator.generateQRCode("", 200, 200);
        });

        String expectedMessage = "Пустой текст";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Сообщение об ошибке должно содержать текст о пустом значении");
    }

    @Test
    void testGenerateQRCode_withInvalidDimensions() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            QRGenerator.generateQRCode("Some text", 0, 200);
        });

        String expectedMessage = "Недопустимые значения";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Сообщение об ошибке должно содержать информацию о недопустимых размерах");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            QRGenerator.generateQRCode("Some text", 200, -1);
        });

        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Сообщение об ошибке должно содержать информацию о недопустимых размерах");
    }

}
