package view;

import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CardProduct {
    private final Color COLOR_CARD_PRODUCT = new Color(224, 224, 224);
    private int cardY = 110;
    private final Rectangle SIZE_CARD_PRODUCT = new Rectangle(0,cardY,480,15);
    private JPanel card = new JPanel();
    private int countProduct;
    private Product product;
    public CardProduct(Product product, int countProduct){
        this.countProduct = countProduct;
        this.product = product;
        setupCardProduct();
        addTable(countProduct);

    }
    public void updateCount(int count){
        card.removeAll();
        addTable(count);
    }
    private void setupCardProduct() {
        card.setBounds(SIZE_CARD_PRODUCT);
        card.setBackground(COLOR_CARD_PRODUCT);
        card.setLayout(new GridLayout(1,4));

    }
    public JPanel getCard(){
        return card;
    }
    private void addTable(int count){

        String[] columnNames = {
                product.getName(),
                product.getBrand(),
                String.valueOf(count),
                product.getPrice().toString()};
        card.add(setupTable(columnNames));
        card.revalidate();
        card.repaint();

    }
    private JTable setupTable(String[] columnNames){
        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        JTable table = new JTable(model);
        table.setBorder(null);
        table.setShowGrid(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.RIGHT);

        table.getColumnModel().getColumn(0).setPreferredWidth(230);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.setFont(new Font("Times New Roman", Font.BOLD, 20));
        table.setBackground(COLOR_CARD_PRODUCT);
        table.setRowHeight(20);
        model.addRow(columnNames);
        return table;
    }


}
