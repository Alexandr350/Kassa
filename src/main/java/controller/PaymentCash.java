package controller;

import model.Product;
import model.ShopBasket;
import view.Order;
import view.UserGUI;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

public class PaymentCash {
    protected double summa;
    protected double cashUser = 0.0;
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#.00");
    private static final int PAYMENT_DIALOG_DURATION = 2000;
    public void setSumma(double summa){
        this.summa=summa;
    }

    /**
     * Основной метод оплаты наличными.
     * @param parentComponent родительский компонент для диалоговых окон
     * @return true — оплата завершена; false — отмена
     */
    public boolean cashPayment(Component parentComponent) {
        while (true) {
            JPanel panel = createPaymentPanel();
            String buttonText = cashUser >= summa ? "Оплатить" : "Внести средства";
            Object[] options = {buttonText, "Отмена"};

            int result = JOptionPane.showOptionDialog(
                    parentComponent, panel, "Внесение средств",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);


            if (result != 0) return false;

            if ("Оплатить".equals(buttonText)) {
                if (validatePaymentComplete()) {
                    return showPaymentResult(parentComponent);
                } else {
                    showInsufficientFundsWarning(parentComponent);
                    continue;
                }
            }

            JTextField textField = (JTextField) panel.getComponent(1);
            if (!processAmountInput(textField.getText().trim(), parentComponent)) {
                continue;
            }
        }
    }
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(createPromptMessage());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        JTextField textField = new JTextField(10);
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setToolTipText("Введите сумму (например: 100.50)");
        panel.add(textField);


        return panel;
    }
    private String createPromptMessage() {
        StringBuilder msg = new StringBuilder("<html><div style='text-align: left;'>");
        msg.append(String.format("Сумма к оплате: <b>%s руб.</b><br>", MONEY_FORMAT.format(summa)));


        if (cashUser < summa) {
            msg.append(String.format(
                    "Рекомендуемая сумма: <b>%s руб.</b><br>" +
                            "Внесено: <b>%s руб.</b>",
                    MONEY_FORMAT.format(summa - cashUser),
                    MONEY_FORMAT.format(cashUser)));
        } else {
            msg.append(String.format("Внесено: <b>%s руб.</b>", MONEY_FORMAT.format(cashUser)));
        }

        msg.append("</div></html>");
        return msg.toString();
    }
    private boolean validatePaymentComplete() {
        return cashUser >= summa;
    }
    protected void showInsufficientFundsWarning(Component parentComponent) {
        showCustomMessageDialog(
                parentComponent,
                "<html>Сумма не полностью внесена.<br>Продолжайте внесение средств.</html>",
                "Недостаточно средств",
                JOptionPane.WARNING_MESSAGE);
    }
    boolean processAmountInput(String amountStr, Component parentComponent) {
        if (amountStr.isEmpty()) {
            showError(parentComponent, "Пожалуйста, введите сумму.", "Ошибка ввода");
            return false;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                showError(parentComponent, "Сумма должна быть больше нуля!", "Ошибка ввода");
                return false;
            }

            cashUser += amount;
            showAmountReceivedNotification(parentComponent, amount);
            return true;

        } catch (NumberFormatException ex) {
            showError(parentComponent,
                    "Некорректный формат суммы!<br>Используйте цифры и точку (например: 100.50).",
                    "Ошибка формата");
            return false;
        }
    }
    private void showAmountReceivedNotification(Component parentComponent, double amount) {
        String message = String.format(
                ("<html><div style='text-align: center;'>" +
                        "Принято %s руб. наличными.<br>" +
                        "Всего внесено: %s руб." +
                        "</div></html>"),
                MONEY_FORMAT.format(amount),
                MONEY_FORMAT.format(cashUser));


        showCustomMessageDialog(parentComponent, message, "Поступление средств", JOptionPane.INFORMATION_MESSAGE);
    }
    protected boolean showPaymentResult(Component parentComponent) {
        double change = cashUser - summa;

        StringBuilder message = new StringBuilder(
                ("<html><div style='text-align: center;'>" +
                        "Оплата успешна!<br>" +
                        String.format("Сумма к оплате: %s руб.<br>", MONEY_FORMAT.format(summa)) +
                        String.format("Внесено: %s руб.<br>", MONEY_FORMAT.format(cashUser))));

        if (change > 0) {
            message.append(String.format("Сдача: %s руб.", MONEY_FORMAT.format(change)));
        }

        message.append("</div></html>");

        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(parentComponent));
        dialog.setTitle("Оплата завершена");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(message.toString(), SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(label, BorderLayout.CENTER);

        dialog.setContentPane(panel);
        dialog.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        Timer timer = new Timer(PAYMENT_DIALOG_DURATION, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);

        summa = 0;
        cashUser = 0;

        Map<Product, Integer> cart = ShopBasket.getShoppingMap();
        double total = ShopBasket.getTotalSum();

        Order order = new Order(cart, total);
        order.displayReceipt(UserGUI.getFrame());

        return true;
    }
    protected void showCustomMessageDialog(Component parentComponent, String message, String title, int messageType) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(label, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(parentComponent, panel, title, messageType);
    }
    protected void showError(Component parentComponent, String message, String title) {
        showCustomMessageDialog(parentComponent, "<html>" + message + "</html>", title, JOptionPane.ERROR_MESSAGE);
    }
    public double getCashUser() {
        return cashUser;
    }
    public double getSumma() {
        return summa; }
    public void setCashUser(double cashUser) {
        this.cashUser = cashUser;
    }
}
