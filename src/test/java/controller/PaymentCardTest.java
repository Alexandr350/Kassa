package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PaymentCardTest {

    private PaymentCard paymentCard;
    private JPanel mockOrderPanel;
    private JFrame mockFrame;

    @BeforeEach
    void setUp() {
        paymentCard = new PaymentCard();
        mockOrderPanel = Mockito.mock(JPanel.class, Answers.RETURNS_DEEP_STUBS);
        mockFrame = Mockito.mock(JFrame.class);
    }
    @Test
    void testCardPayment_NullPanel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentCard.cardPayment(null);
        }, "cardPayment() должен бросать IllegalArgumentException при null-панели");
    }


    @Test
    void testCardPayment_Interrupted() throws InterruptedException {
        Thread paymentThread = new Thread(() -> {
            boolean success = paymentCard.cardPayment(mockOrderPanel);
            assertThat(success).isFalse();
        });

        paymentThread.start();
        Thread.sleep(100);
        paymentThread.interrupt();

        paymentThread.join(2000);
        assertThat(paymentThread.isAlive()).isFalse();
    }


    @Test
    void testUpdateStatusLabel_UpdatesTextAndRepaints() {
        JLabel mockLabel = Mockito.mock(JLabel.class);

        paymentCard.updateStatusLabel(mockLabel, "Новый статус");

        verify(mockLabel).setText("Новый статус");
        verify(mockLabel).revalidate();
        verify(mockLabel).repaint();
    }


    @Test
    void testCreateStatusPanel_ReturnsValidPanel() {
        JPanel panel = paymentCard.createStatusPanel();

        assertThat(panel).isNotNull();
        assertThat(panel.getLayout())
                .isInstanceOf(BorderLayout.class)
                .withFailMessage("Панель должна использовать BorderLayout");

        Component[] components = panel.getComponents();
        assertThat(components).hasSize(1);
        assertThat(components[0])
                .isInstanceOf(JLabel.class)
                .withFailMessage("Должна быть метка статуса");
    }

}
