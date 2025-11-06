package view;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int radius;
    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
    }
    public RoundedButton(String text, int radius, Icon icon ){
        super(text, icon);
        this.radius = radius;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }


}
