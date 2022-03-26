package by.epam.lamashka.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
  //    @XmlElementWrapper(name = "users")
  @XmlElement(name = "user")
  private List<User> users;

  @XmlElement(name = "customer")
  private List<Customer> customers;

  public Users(List<User> users) {
    this.users = users;
  }

  public Users() {}

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

//  public List<Customer> getCustomers() {
//    return customers;
//  }
//
//  public void setCustomers(List<Customer> customers) {
//    this.customers = customers;
//  }

  @Override
  public String toString() {
    return "Users{" + "users=" + users + '}';
  }
}
