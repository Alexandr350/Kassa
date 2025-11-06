package view;

import javax.swing.*;
import java.awt.*;


public class ProductDisplayPanel {
    private static final Color PANEL_BACKGROUND_COLOR = new Color(224, 224, 224); // Цвет фона панел
    private static Rectangle PANEL_LIST_PRODUCT = new Rectangle(10,120,480,520);
    private JPanel productPanel = new JPanel();
    private void setupProductPanel(){
        productPanel.setBounds(PANEL_LIST_PRODUCT);
        productPanel.setBackground(PANEL_BACKGROUND_COLOR);

    }
    public ProductDisplayPanel() {
        setupProductPanel();
    }
    public JPanel getProductPanel(){
        return productPanel;
    }



}
