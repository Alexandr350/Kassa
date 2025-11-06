package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ButtonClickListenerTest {
    private JFrame mockFrame;
    private JPanel mockContentPane;
    private ButtonClickListener buttonClickListener;

    @BeforeEach
    void setUp() {
        mockFrame = Mockito.mock(JFrame.class);
        mockContentPane = Mockito.mock(JPanel.class);
        buttonClickListener = new ButtonClickListener(mockFrame);
        when(mockFrame.getContentPane()).thenReturn(mockContentPane);
    }

    @Test
    void testConstructor_NullFrame() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ButtonClickListener(null);
        });

        String expectedMessage = "Фрейм не может быть null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Сообщение об ошибке должно содержать текст о null фрейме");
    }

    @Test
    void testChangePage_NullComponent() {
        boolean result = buttonClickListener.changePage(null);
        assertFalse(result, "Метод должен вернуть false при передаче null компонента");
        verify(mockFrame, never()).getContentPane();
    }
}


