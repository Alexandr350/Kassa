package model;

public class ProductListItem {
    private Product product;

    public ProductListItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return product.getNameProduct() + " (Бренд: " + product.getBrand() + ", Артикул: " + product.getArticleNumber() + ", Цена: " + product.getPrice() + ")";
    }
}
