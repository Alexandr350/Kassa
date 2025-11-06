package controller;

import model.Product;
import model.ShopBasket;
import view.Order;
import view.UserGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PaymentCard {
    private JPanel orderPanel;
    private static final String TEXT_TITLE = "Оплата банковской картой";
    private static final int DIALOG_WIDTH = 350;
    private static final int DIALOG_HEIGHT = 120;

    /**
     * Выполняет имитацию оплаты банковской картой.
     *
     * @param orderPanel панель для отображения статуса
     * @return true, если оплата завершена; false, если отменена или произошла ошибка
     */
    public boolean cardPayment(JPanel orderPanel) {
        if (orderPanel == null) {
            throw new IllegalArgumentException("orderPanel не может быть null");
        }
        this.orderPanel = orderPanel;

        int result = showPanelDialog("Приложите карту к терминалу");
        if (result != JOptionPane.OK_OPTION) {
            return false; // Отмена
        }

        JPanel statusPanel = createStatusPanel();
        JLabel statusLabel = (JLabel) statusPanel.getComponent(0);

        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(orderPanel));
        dialog.setTitle(TEXT_TITLE);
        dialog.setContentPane(statusPanel);
        dialog.setModal(true);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(orderPanel);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        Thread paymentThread = new Thread(() -> {
            try {
                updateStatusLabel(statusLabel, "Чтение карты...");
                Thread.sleep(1000);

                updateStatusLabel(statusLabel, "Списание средств...");
                Thread.sleep(1000);

                updateStatusLabel(statusLabel, "Оплата успешна!");
                Thread.sleep(500);

                SwingUtilities.invokeLater(dialog::dispose);
                Map<Product, Integer> cart = ShopBasket.getShoppingMap();
                double total = ShopBasket.getTotalSum();

                Order order = new Order(cart, total);
                order.displayReceipt(UserGUI.getFrame());

            } catch (InterruptedException e) {
                updateStatusLabel(statusLabel, "Ошибка платежа");
                SwingUtilities.invokeLater(dialog::dispose);
            }
        });

        paymentThread.start();
        dialog.setVisible(true);

        return !paymentThread.isInterrupted();
    }
    protected int showPanelDialog(String text) {
        Object[] options = {"Приложить карту"};
        return JOptionPane.showOptionDialog(
                orderPanel,
                text,
                TEXT_TITLE,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }
    protected void updateStatusLabel(JLabel label, String text) {
        SwingUtilities.invokeLater(() -> {
            label.setText(text);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.revalidate();
            label.repaint();
        });
    }
    protected JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Ожидание...", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
