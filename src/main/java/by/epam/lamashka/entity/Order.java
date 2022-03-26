package by.epam.lamashka.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Objects;
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
  @XmlTransient
  private LocalDateTime orderDateTime;
  private String orderContent;

  private Order(OrderBuilder builder) {
    this.orderDateTime = builder.orderDateTime;
    this.orderContent = builder.orderContent;
  }

  public Order() {}

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }

  public static class OrderBuilder {
    private LocalDateTime orderDateTime;
    private String orderContent;

    public OrderBuilder() {}

    public OrderBuilder orderContent(String orderContent) {
      this.orderContent = orderContent;
      return this;
    }

    public OrderBuilder orderDateTime(LocalDateTime orderDateTime) {
      this.orderDateTime = orderDateTime;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return orderDateTime.equals(order.orderDateTime) && orderContent.equals(order.orderContent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderDateTime, orderContent);
  }

  @Override
  public String toString() {
    return "Order{" + "orderDateTime=" + orderDateTime + ", orderContent='" + orderContent + '\'' + '}';
  }
}
