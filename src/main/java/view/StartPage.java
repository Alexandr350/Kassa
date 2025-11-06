package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartPage {
    private final URL urlFon = StartPage.class.getClassLoader().getResource("images/fon.jpg");
    private JLayeredPane layeredPane = new JLayeredPane();
    private JLabel fonLabel = new JLabel();
    private JLabel scanLabel = new JLabel("Отсканируйте товар");
    private JButton buttonBuy = new RoundedButton("Выбрать товар", 50);
    private JPanel keyPanel = new JPanel();
    public StartPage() {
        initialize();
    }
    private void initialize() {
        checkImage();
        setupFonLabel();
        setupScanLabel();
        setupButtonBuy();
        setupKeyPanel();
        addComponentsToLayeredPane();
    }
    private void checkImage() {
        if (urlFon == null) {
            System.err.println("Изображение не найдено. Проверьте путь к файлу.");
            System.exit(1);
        }
    }
    private void setupFonLabel() {
        ImageIcon fonIcon = new ImageIcon(urlFon);
        Image scaledImage = fonIcon.getImage().getScaledInstance(500, 800, Image.SCALE_SMOOTH);
        fonLabel.setIcon(new ImageIcon(scaledImage));
        fonLabel.setBounds(0, 0, 500, 800);
    }
    private void setupScanLabel() {
        scanLabel.setBounds(50, 620, 400, 30);
        scanLabel.setHorizontalAlignment(JLabel.CENTER);
        scanLabel.setForeground(new Color(0, 100, 0));
        scanLabel.setFont(new Font("Arial", Font.BOLD, 28));
    }
    private void setupButtonBuy() {
        buttonBuy.setBounds(50, 700, 400, 60);
        buttonBuy.setContentAreaFilled(false);
        buttonBuy.setBorderPainted(false);
        buttonBuy.setOpaque(false);
        buttonBuy.setBackground(new Color(0, 100, 0));
        buttonBuy.setForeground(Color.WHITE);
        buttonBuy.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBuy.setBorder(BorderFactory.createEmptyBorder());
        buttonBuy.setFocusPainted(false);
        buttonBuy.setFocusable(false);
        buttonBuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonBuy.setBackground(new Color(0, 153, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonBuy.setBackground(new Color(0, 100, 0));
            }
        });
        buttonBuy.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
    private void setupKeyPanel() {
        keyPanel.setBounds(0, 0, 500, 800);
        keyPanel.setOpaque(false);
        keyPanel.setFocusable(true);
        keyPanel.requestFocusInWindow();
    }
    private void addComponentsToLayeredPane() {
        layeredPane.setPreferredSize(new Dimension(500, 800));
        layeredPane.add(fonLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(scanLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(buttonBuy, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(keyPanel, JLayeredPane.PALETTE_LAYER);

    }
    public JLayeredPane mainStartPage() {
        return layeredPane;
    }
    public JButton getButtonBuy(){return buttonBuy;}
}
