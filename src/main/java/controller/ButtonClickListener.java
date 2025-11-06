package controller;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ButtonClickListener {
    private static final Logger LOGGER = Logger.getLogger(ButtonClickListener.class.getName());
    private final JFrame frame;
    public ButtonClickListener(JFrame frame) {
        if (frame == null) {
            throw new IllegalArgumentException("Фрейм не может быть null");
        }
        this.frame = frame;
    }
    public boolean changePage(Component newPage) {
        if (newPage == null) {
            LOGGER.log(Level.SEVERE, "Ошибка: newPage == null");
            return false;
        }

        try {
            frame.getContentPane().removeAll();
            frame.add(newPage);
            frame.revalidate();
            frame.repaint();

            if (!frame.isVisible()) {
                frame.setVisible(true);
            }

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при изменении страницы", e);
            return false;
        }
    }
}
