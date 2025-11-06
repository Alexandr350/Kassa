package view;

import controller.ButtonClickListener;
import model.ShopBasket;

import javax.swing.*;

public class UserGUI {
    static JFrame frame;
    public static void main(String[] args) {
        genericApp();
    }
    public static void genericApp() {
        if (frame != null) {
            frame.dispose();
        }
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        ButtonClickListener buttonClickListener = new ButtonClickListener(frame);
        StartPage startPage = new StartPage();
        SearchPage searchPage = new SearchPage(frame);
        ChoosingPaymentMethod choosingPaymentMethod = new ChoosingPaymentMethod();

        startPage.getButtonBuy().addActionListener(e -> buttonClickListener.changePage(searchPage.getSearchLayeredPane()));
        searchPage.getButtonBuyProduct().addActionListener(e -> {
            buttonClickListener.changePage(choosingPaymentMethod.getChoosingPaymentPanel());
            choosingPaymentMethod.setSumma(ShopBasket.getTotalSum());
            choosingPaymentMethod.setShoppingMap(ShopBasket.getShoppingMap());
            new Order(ShopBasket.getShoppingMap(), ShopBasket.getTotalSum());
        });

        frame.getContentPane().removeAll();
        frame.add(startPage.mainStartPage());
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
    public static JFrame getFrame() {
        return frame;
    }
}
