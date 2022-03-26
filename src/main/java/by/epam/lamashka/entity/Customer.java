package by.epam.lamashka.entity;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer extends User {
  @XmlElementWrapper(name = "products")
  @XmlElement(name = "product")
  private List<Product> products;

  @XmlElementWrapper(name = "orders")
  @XmlElement(name = "order")
  private List<Order> orders;

  public Customer() {
  }

  private Customer(CustomerBuilder builder) {
    super(builder);
    this.products = builder.products;
    this.orders=builder.orders;
  }

  public List<Product> getProducts() {
    return products;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public static class CustomerBuilder extends User.UserBuilder<CustomerBuilder> {
    private List<Product> products;
    private List<Order> orders;

    public CustomerBuilder() {}

    public CustomerBuilder products(List<Product> products) {
      this.products = products;
      return this;
    }

    public CustomerBuilder orders(List<Order> orders) {
      this.orders = orders;
      return this;
    }

    public Customer build() {
      return new Customer(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Customer customer = (Customer) o;
    return Objects.equals(products, customer.products) && Objects.equals(orders, customer.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), products, orders);
  }

  @Override
  public String toString() {
    return "Customer{"
        + "products="
        + products
        + ", orders="
        + orders
        + ", login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + ", id='"
        + id
        + '\''
        + '}';
  }
}
