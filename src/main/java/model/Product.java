package model;

import java.util.Objects;

public class Product {
    private String articleNumber;
    private String nameProduct;
    private String brand;
    private String specifications;
    private Double price;
    private Integer count;

    public Product(String articleNumber, String nameProduct, String brand, String specifications, Double price, Integer count) {
        this.articleNumber = articleNumber;
        this.nameProduct = nameProduct;
        this.brand = brand;
        this.specifications = specifications;
        this.price = price;
        this.count = count;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getBrand() {
        return brand; // Геттер для brand
    }

    public String getSpecifications() {
        return specifications;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(articleNumber, product.articleNumber) &&
                Objects.equals(nameProduct, product.nameProduct) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(specifications, product.specifications) &&
                Objects.equals(price, product.price) &&
                Objects.equals(count, product.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleNumber, nameProduct, brand, specifications, price, count);
    }

    @Override
    public String toString() {
        return "Product{" +
                "articleNumber='" + articleNumber + '\'' +
                ", nameProduct='" + nameProduct + '\'' +
                ", brand='" + brand + '\'' +
                ", specifications='" + specifications + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    public String getName() {
        return nameProduct;
    }
}
