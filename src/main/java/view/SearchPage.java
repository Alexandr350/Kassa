package view;


import controller.Search;
import model.Product;
import model.ProductListItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    private JFrame frame;
    private JLayeredPane searchLayeredPane = new JLayeredPane();
    private JLabel labelTitleSearchLayeredPane = new JLabel("Ваши покупки");
    private JLabel labelCentreSearchLayeredPane = new JLabel("Отсканируйте \n товар");
    private JLabel labelFon = new JLabel();
    private JLabel priceSearchLayeredPane = new JLabel();
    private JTextField productInputSearch = new RoundedTextField(10, 20, "Найти товар");
    private JButton buttonBuyProduct = new RoundedButton("Оплатить товар", 50);
    private List<Product> products = new ArrayList<>();
    private JList<ProductListItem> suggestionList;
    private DefaultListModel<ProductListItem> suggestionModel;
    private Search searchController;
    private ProductDisplayPanel productPanel = new ProductDisplayPanel();
    private JPopupMenu suggestionPopup;


    public SearchPage(JFrame frame) {
        this.frame = frame;
        this.searchController = new Search(productPanel);
        initialize();
    }

    private void initialize() {
        addLayeredPane();
        setupFon();
        setupProductInputSearch();
        setupButtonBuyProduct();
        setupPriceLabel();
        setupListeners();
        setupTitle();
        setupScanProduct();
        setupSuggestionList();
    }

    private void setupSuggestionList() {
        suggestionModel = new DefaultListModel<>();
        suggestionList = new JList<>(suggestionModel);
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.setFocusable(false);

        suggestionPopup = new JPopupMenu();
        suggestionPopup.setFocusable(false);
        suggestionList.setFixedCellHeight(15);

        suggestionList.addListSelectionListener(e -> updateSuggestionPopup());

        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ProductListItem selectedItem = suggestionList.getSelectedValue();
                    if (selectedItem != null) {
                        Product product = selectedItem.getProduct();
                        searchController.addToBasket(product);
                        products.add(product);
                        setupButtonBuyProduct();
                        suggestionModel.clear();
                        suggestionPopup.setVisible(false);
                        productInputSearch.setText("");
                        updatePriceLabel();
                    }
                }
            }
        });

        suggestionPopup.add(suggestionList, Integer.valueOf(2));
    }

    private void updateSuggestionPopup() {
        int size = suggestionModel.getSize();
        suggestionPopup.removeAll();

        if (size > 10) {
            JScrollPane scrollPane = new JScrollPane(suggestionList);
            scrollPane.setPreferredSize(new Dimension(380, 10 * suggestionList.getFixedCellHeight()));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            suggestionPopup.add(scrollPane, Integer.valueOf(2));
        } else {
            suggestionPopup.add(suggestionList, Integer.valueOf(2));
        }

        suggestionPopup.setPreferredSize(new Dimension(380, Math.min(10, size) * suggestionList.getFixedCellHeight()));
        suggestionPopup.revalidate();
        suggestionPopup.repaint();
    }

    private void updateSuggestions(String input) {
        suggestionModel.clear();

        if (input.isEmpty()) {
            suggestionPopup.setVisible(false);
            return;
        }

        List<Product> products = searchController.searchProducts(input);

        for (Product product : products) {
            suggestionModel.addElement(new ProductListItem(product));
        }

        int maxVisibleRows = Math.min(10, suggestionModel.getSize());
        suggestionList.setFixedCellHeight(20);
        int height = maxVisibleRows * suggestionList.getFixedCellHeight();
        suggestionPopup.setPopupSize(380, height);

        if (!suggestionModel.isEmpty()) {
            suggestionPopup.show(productInputSearch, 0, -height);
        } else {
            suggestionPopup.setVisible(false);
        }
    }

    private void updatePriceLabel() {
        double totalSum = searchController.getShopBasket().getTotalSum();
        priceSearchLayeredPane.setText(new DecimalFormat("0.00").format(totalSum) + " ₽");
    }

    private void setupProductInputSearch() {
        int fieldWidth = 380;
        int fieldHeight = 30;
        int xPosition = (frame.getWidth() - fieldWidth) / 2;
        int yPosition = 650;

        productInputSearch.setBounds(xPosition, yPosition, fieldWidth, fieldHeight);
        productInputSearch.setFont(new Font("Arial", Font.PLAIN, 15));
        productInputSearch.setOpaque(false);
        searchLayeredPane.add(productInputSearch, Integer.valueOf(3));
    }
    private void setupFon() {
        labelFon.setBounds(0, 0, 500, 800);
        labelFon.setBackground(new Color(224, 224, 224));
        labelFon.setOpaque(true);
    }
    private void setupPriceLabel() {
        priceSearchLayeredPane.setText("0,00 ₽");
        priceSearchLayeredPane.setFont(new Font("Arial", Font.BOLD, 55));
        priceSearchLayeredPane.setForeground(Color.BLACK);
        priceSearchLayeredPane.setBounds(0, 55, frame.getWidth() - 10, 60);
        priceSearchLayeredPane.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    private void setupTitle() {
        labelTitleSearchLayeredPane.setFont(new Font("Arial", Font.BOLD, 30));
        labelTitleSearchLayeredPane.setForeground(Color.BLACK);
        labelTitleSearchLayeredPane.setBounds(0, 3, frame.getWidth(), 60);
        labelTitleSearchLayeredPane.setHorizontalAlignment(SwingConstants.CENTER);
    }
    private void setupScanProduct() {
        labelCentreSearchLayeredPane.setFont(new Font("Arial", Font.BOLD, 24));
        labelCentreSearchLayeredPane.setForeground(new Color(192, 192, 192));
        labelCentreSearchLayeredPane.setBounds(0, 400, frame.getWidth(), 200);
        labelCentreSearchLayeredPane.setHorizontalAlignment(SwingConstants.CENTER);
    }
    private void setupButtonBuyProduct() {
        buttonBuyProduct.setBounds(50, 700, 400, 60);
        buttonBuyProduct.setContentAreaFilled(false);
        buttonBuyProduct.setBorderPainted(false);
        buttonBuyProduct.setOpaque(false);

        buttonBuyProduct.setForeground(Color.WHITE);
        buttonBuyProduct.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBuyProduct.setBorder(BorderFactory.createEmptyBorder());
        buttonBuyProduct.setFocusPainted(false);
        buttonBuyProduct.setFocusable(false);


        buttonBuyProduct.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        if(products.isEmpty()){
            buttonBuyProduct.setEnabled(false);
            buttonBuyProduct.setBackground(new Color(181,184,177));
        }
        if(!products.isEmpty()){
            buttonBuyProduct.setEnabled(true);
            buttonBuyProduct.setBackground(new Color(0, 100, 0));
            buttonBuyProduct.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    buttonBuyProduct.setBackground(new Color(0, 153, 0));
                }

                public void mouseExited(MouseEvent evt) {
                    buttonBuyProduct.setBackground(new Color(0, 100, 0));
                }

            });
            buttonBuyProduct.repaint();
        }

    }
    private void setupListeners() {
        productInputSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = productInputSearch.getText();
                updateSuggestions(input);
            }
        });
    }
    private void addLayeredPane() {
        searchLayeredPane.add(labelFon, Integer.valueOf(1));
        searchLayeredPane.add(buttonBuyProduct, Integer.valueOf(4));
        searchLayeredPane.add(priceSearchLayeredPane, Integer.valueOf(3));
        searchLayeredPane.add(labelTitleSearchLayeredPane, Integer.valueOf(3));
        searchLayeredPane.add(labelCentreSearchLayeredPane, Integer.valueOf(2));
        searchLayeredPane.add(productPanel.getProductPanel(), Integer.valueOf(3));

    }
    public JLayeredPane getSearchLayeredPane() {
        return searchLayeredPane;
    }
    public JButton getButtonBuyProduct() {
        return buttonBuyProduct;
    }

}
