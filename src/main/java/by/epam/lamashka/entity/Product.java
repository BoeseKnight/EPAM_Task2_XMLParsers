package by.epam.lamashka.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
  private int productNumber;
  private String description;

  private Product(ProductBuilder builder) {
    this.productNumber = builder.productNumber;
    this.description = builder.description;
  }

  public Product() {}

  public int getProductNumber() {
    return productNumber;
  }

  public String getDescription() {
    return description;
  }

  public static class ProductBuilder {
    private int productNumber;
    private String description;

    public ProductBuilder() {}

    public ProductBuilder description(String description) {
      this.description = description;
      return this;
    }

    public ProductBuilder productNumber(int productNumber) {
      this.productNumber = productNumber;
      return this;
    }

    public Product build() {
      return new Product(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return productNumber == product.productNumber && description.equals(product.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productNumber, description);
  }

  @Override
  public String toString() {
    return "Product{"
        + "productNumber="
        + productNumber
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
