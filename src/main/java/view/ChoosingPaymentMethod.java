package view;


import controller.PaymentCard;
import controller.PaymentCash;
import controller.PaymentSbp;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Map;
import java.util.UUID;


public class ChoosingPaymentMethod {
    private JPanel choosingPaymentPanel;
    private final Rectangle SIZE_ORDER_PANEL = new Rectangle(0, 0, 500, 800);
    private JButton btnCashPayment;
    private JButton btnCardPayment;
    private JButton btnSbpPayment;
    private  JLabel labelTextPayment;
    private PaymentCash paymentCash;
    private PaymentCard paymentCard;
    private PaymentSbp paymentSbp;
    private double summa;
    private Map<Product, Integer> shoppingMap;
    private final URL urlSbp = ChoosingPaymentMethod.class.getClassLoader().getResource("images/sbp-logo.png");
    private final URL urlCard = ChoosingPaymentMethod.class.getClassLoader().getResource("images/card.png");
    private final URL urlCash = ChoosingPaymentMethod.class.getClassLoader().getResource("images/cash.png");
    public void setSumma(double summa) {
        if (summa < 0) {
            throw new IllegalArgumentException("Сумма к оплате не может быть отрицательной");
        }
        this.summa = summa;
    }
    public void setShoppingMap(Map<Product, Integer> shoppingMap) {
        this.shoppingMap = shoppingMap;
    }
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    public ChoosingPaymentMethod() {
        paymentCash = new PaymentCash();
        paymentCard = new PaymentCard();
        paymentSbp = new PaymentSbp();
        choosingPaymentPanel = new JPanel();
        setupOrderPanel();
        createPaymentButtons();
        layoutComponents();
        createTextLabel();
    }
    public JPanel getChoosingPaymentPanel() {
        return choosingPaymentPanel;
    }
    private void createTextLabel() {
        labelTextPayment = new JLabel("Способ оплаты", SwingConstants.CENTER);
        labelTextPayment.setFont(new Font("Serif", Font.BOLD, 50));
        labelTextPayment.setBackground(Color.BLACK);

        int labelWidth = 400;
        int labelHeight = 50;
        int startX = (SIZE_ORDER_PANEL.width - labelWidth) / 2;
        int startY = 90;

        labelTextPayment.setBounds(startX, startY, labelWidth, labelHeight);
        labelTextPayment.setForeground(Color.DARK_GRAY);

        choosingPaymentPanel.add(labelTextPayment);
    }
    private void setupOrderPanel() {
        choosingPaymentPanel.setBounds(SIZE_ORDER_PANEL);
        choosingPaymentPanel.setLayout(null);

    }
    public ImageIcon resizeIcon(ImageIcon icon, int width, int height){
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
    private void createPaymentButtons() {
        ImageIcon icon = new ImageIcon(urlSbp);
        ImageIcon resized = resizeIcon(icon, 180,80);

        ImageIcon icon1 = new ImageIcon(urlCard);
        ImageIcon resiz = resizeIcon(icon1, 90, 80);

        ImageIcon icon2 = new ImageIcon(urlCash);
        ImageIcon resiz2 = resizeIcon(icon2, 70, 70);


        btnCashPayment = new RoundedButton("Оплата наличными", 70, resiz2);
        btnCardPayment = new RoundedButton("Оплата картой", 70, resiz);
        btnSbpPayment = new RoundedButton("", 70, resized);

        btnCashPayment.setIconTextGap(0);

        btnCashPayment.setBorder(BorderFactory.createEmptyBorder());
        btnCardPayment.setBorder(BorderFactory.createEmptyBorder());
        btnSbpPayment.setBorder(BorderFactory.createEmptyBorder());

        btnCashPayment.setBackground(new Color(192, 192, 192));
        btnCardPayment.setBackground(new Color(192, 192, 192));
        btnSbpPayment.setBackground(new Color(192, 192, 192));


        Font buttonFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 16);
        Dimension buttonSize = new Dimension(400, 400);

        for (JButton btn : new JButton[]{btnCashPayment, btnCardPayment, btnSbpPayment}) {
            btn.setFont(buttonFont);
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setMinimumSize(buttonSize);
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
        }

        setupSbpPaymentBtn(btnSbpPayment);
        setupSbpPaymentBtn(btnCardPayment);
        setupSbpPaymentBtn(btnCashPayment);

    }
    private void setupSbpPaymentBtn(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(165,165,165));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(192, 192, 192));
            }
        });
        button.repaint();
    }
    private void layoutComponents() {
        int btnWidth = 350;
        int btnHeight = 160;
        int gap = 30;
        int startX = (SIZE_ORDER_PANEL.width - btnWidth) / 2;
        int startY = ((SIZE_ORDER_PANEL.width - btnHeight) / 2) + 25;

        btnCashPayment.setBounds(startX, startY, btnWidth, btnHeight);
        btnCardPayment.setBounds(startX, startY + btnHeight + gap, btnWidth, btnHeight);
        btnSbpPayment.setBounds(startX, startY + 2 * (btnHeight + gap), btnWidth, btnHeight);

        choosingPaymentPanel.add(btnCashPayment);
        choosingPaymentPanel.add(btnCardPayment);
        choosingPaymentPanel.add(btnSbpPayment);

        // Обработчики событий
        btnCashPayment.addActionListener(e -> onCashPayment());
        btnCardPayment.addActionListener(e -> onCardPayment());
        btnSbpPayment.addActionListener(e -> onSbpPayment());
    }
    private void onCashPayment() {
        System.out.println(summa);
        paymentCash.setSumma(summa);
        paymentCash.cashPayment(choosingPaymentPanel);


    }
    private void onCardPayment() {
        paymentCard.cardPayment(choosingPaymentPanel);
    }
    private void onSbpPayment() {
        try {

            String paymentID = generateUUID();
            String qrData = String.format(
                    ("sbp://payment?id=%s&sum=%.2f"),
                    paymentID,
                    summa
            );
            ImageIcon qr = QRGenerator.generateQRCode(qrData,300,300);
            paymentSbp.setQrCode(qr);
            paymentSbp.sbpPayment(choosingPaymentPanel);

        }catch (Exception e){
            JOptionPane.showMessageDialog(
                    choosingPaymentPanel,
                    "Ошибка генерации QR-кода: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
