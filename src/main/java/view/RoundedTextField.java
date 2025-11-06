package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundedTextField extends JTextField {
    private int cornerRadius;
    private String placeholder;
    private Color placeholderColor = Color.GRAY;
    public RoundedTextField(int size, int cornerRadius, String placeholder) {
        super(size);
        this.cornerRadius = cornerRadius;
        this.placeholder = placeholder;
        setOpaque(false);


        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        super.paintComponent(g);


        if (getText().isEmpty()) {
            g2.setColor(placeholderColor);
            g2.drawString(placeholder, 10, g.getFontMetrics().getHeight());
        }
    }
    @Override
    protected void paintBorder(Graphics g) {

    }
}
