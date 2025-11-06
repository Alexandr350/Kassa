package view;

import model.Product;
import model.ShopBasket;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Order {
    private double summa;
    private Map<Product, Integer> shoppingMap;
    private final URL paymentSuccess = StartPage.class.getClassLoader().getResource("images/vznosy.jpg");
    private final Color BACKGROUND_COLOR = new Color(244,244,244);
    private JPanel panelOrder;
    public Order(Map<Product, Integer> shoppingMap, double summa) {
        if (summa < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной");
        }
        this.summa = summa;
        this.shoppingMap = shoppingMap;

        panelOrder = new JPanel(new BorderLayout());
        panelOrder.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelOrder.setBackground(Color.WHITE);
        panelOrder.setPreferredSize(new Dimension(500, 800));
    }
    public void displayReceipt(JFrame parentFrame) {
        panelOrder.removeAll();
        panelOrder.setBackground(BACKGROUND_COLOR);
        addHeader();
        addContentPanel();
        addPaymentSuccessLogo();
        setupAndShowPanel(parentFrame);
        startRestartTimer();
    }
    private void addHeader() {
        JLabel titleLabel = new JLabel("Чек об оплате", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 100, 0));
        panelOrder.add(titleLabel, BorderLayout.NORTH);
    }
    private void addContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        JLabel dateLabel = new JLabel("Дата: " + now.format(formatter), SwingConstants.LEFT);
        dateLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(dateLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        if (shoppingMap != null && !shoppingMap.isEmpty()) {
            for (Map.Entry<Product, Integer> entry : shoppingMap.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double totalPrice = product.getPrice() * quantity;

                JLabel itemLabel = new JLabel(String.format(
                        "%s × %d = %.2f руб.",
                        product.getName(),
                        quantity,
                        totalPrice
                ));
                itemLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                itemLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                contentPanel.add(itemLabel);
            }
        } else {
            JLabel emptyLabel = new JLabel("Товары не указаны", SwingConstants.LEFT);
            emptyLabel.setFont(new Font("Dialog", Font.ITALIC, 10));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(emptyLabel);
        }

        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        JLabel totalLabel = new JLabel(String.format("Итого к оплате: %.2f руб.", summa));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        totalLabel.setForeground(new Color(0, 100, 0));
        totalLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        contentPanel.add(totalLabel);

        panelOrder.add(contentPanel, BorderLayout.CENTER);
    }
    private void addPaymentSuccessLogo() {
        if (paymentSuccess != null) {
            ImageIcon icon = new ImageIcon(paymentSuccess);
            Image scaled = icon.getImage().getScaledInstance(300, 120, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panelOrder.add(imageLabel, BorderLayout.SOUTH);
        }
    }
    private void setupAndShowPanel(JFrame parentFrame) {
        panelOrder.revalidate();
        panelOrder.repaint();

        parentFrame.setContentPane(panelOrder);
        parentFrame.pack();
        parentFrame.setLocationRelativeTo(null);
    }
    private void startRestartTimer() {
        Timer timer = new Timer(10000, e -> {
            ShopBasket.clear();
            UserGUI.genericApp();
        });
        timer.setRepeats(false);
        timer.start();
    }

}
