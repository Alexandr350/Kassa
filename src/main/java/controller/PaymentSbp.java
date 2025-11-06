package controller;

import model.Product;
import model.ShopBasket;
import view.Order;
import view.UserGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PaymentSbp {
    private ImageIcon qrCode;
    public void setQrCode(ImageIcon icon) {
        this.qrCode = icon;
    }
    public void sbpPayment(JPanel orderPanel) {
        int result = showSbpDialog(orderPanel);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        JPanel statusPanel = createStatusPanel();
        JLabel statusLabel = (JLabel) statusPanel.getComponent(0);

        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(orderPanel));
        dialog.setTitle("Оплата через СБП");
        dialog.setContentPane(statusPanel);
        dialog.setModal(true);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(orderPanel);

        new Thread(() -> {
            try {
                updateStatus(statusLabel, "Поиск счёта...");
                Thread.sleep(1200);

                updateStatus(statusLabel, "Отправка платежа...");
                Thread.sleep(1800);

                updateStatus(statusLabel, "Подтверждение платежа...");
                Thread.sleep(1000);

                updateStatus(statusLabel, "Оплата через СБП успешна!");
                Thread.sleep(600);

                dialog.dispose();
                Map<Product, Integer> cart = ShopBasket.getShoppingMap();
                double total = ShopBasket.getTotalSum();

                Order order = new Order(cart, total);
                order.displayReceipt(UserGUI.getFrame());

            } catch (InterruptedException e) {
                updateStatus(statusLabel, "Ошибка платежа через СБП");
            }
        }).start();

        dialog.setVisible(true);
    }
    private void updateStatus(JLabel label, String text) {
        label.setText(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.revalidate();
        label.repaint();
    }
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Dialog", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    private int showSbpDialog(JPanel orderPanel) {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel instructionLabel = new JLabel("Сканируйте QR-code", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.PLAIN + Font.BOLD, 14));
        instructionLabel.setForeground(new Color(60, 60, 60));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialogPanel.add(instructionLabel);

        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel qrPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel qrLabel = new JLabel(qrCode);
        qrPanel.add(qrLabel);
        dialogPanel.add(qrPanel);

        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Object[] options = {"Отсканировать QR-code"};
        JOptionPane pane = new JOptionPane(
                (dialogPanel),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[0]
        );

        JDialog dialog = pane.createDialog(orderPanel, "Оплата через СБП");
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(orderPanel);
        dialog.setVisible(true);

        Object selectedValue = pane.getValue();
        for (int i = 0; i < options.length; i++) {
            if (options[i] == selectedValue) {
                return i;
            }
        }
        return JOptionPane.CLOSED_OPTION;
    }
}
