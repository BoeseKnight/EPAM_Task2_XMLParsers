import by.epam.lamashka.entity.Customer;
import by.epam.lamashka.entity.Order;
import by.epam.lamashka.entity.User;
import by.epam.lamashka.parser.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTests {
  @Test
  public void testUser() {
    Parser domParser = ParserFactory.getInstance().getDomParser();
    List<User> users = domParser.run();
    Assert.assertEquals(
        users.get(0), new User.UserBuilder().login("Ilya").password("2").id("ID-1").build());
  }

  @Test
  public void testUserLogins() {
    List<String> expectedLogins =
        Arrays.asList("Ilya", "Ksenia", "Andrew", "Dima", "Egor", "Karol", "Jim", "Jack");
    Parser domParser = ParserFactory.getInstance().getDomParser();
    List<User> users = domParser.run();
    List<String> loginsFromParser = new ArrayList<>();
    for (User user : users) {
      loginsFromParser.add(user.getLogin());
    }
    Assert.assertEquals(expectedLogins, loginsFromParser);
  }

  @Test
  public void testUserPasswords() {
    List<String> expectedPasswords =
        Arrays.asList("2", "3", "8", "6", "6", "45g", "jim223", "7892jack");
    Parser staxParser = ParserFactory.getInstance().getStaxParser();
    List<User> users = staxParser.run();
    List<String> parserPasswords = new ArrayList<>();
    for (User user : users) {
      parserPasswords.add(user.getPassword());
    }
    Assert.assertEquals(expectedPasswords, parserPasswords);
  }

  @Test
  public void testCustomersNumber() {
    Parser saxParser = ParserFactory.getInstance().getSaxParser();
    List<User> users = saxParser.run();
    int customersNumber = 0;
    for (User user : users) {
      if (user instanceof Customer) {
        customersNumber++;
      }
    }
    Assert.assertEquals(5, customersNumber);
  }

  @Test
  public void testUsersNumber() {
    Parser saxParser = ParserFactory.getInstance().getSaxParser();
    List<User> users = saxParser.run();
    int usersNumber = users.size();
    Assert.assertEquals(8, usersNumber);
  }

  @Test
  public void testOrdersNumber() {
    Parser staxParser = ParserFactory.getInstance().getStaxParser();
    List<User> users = staxParser.run();
    int ordersNumber = 0;
    for (User user : users) {
      if (user instanceof Customer) {
        Customer customer = (Customer) user;
        if (customer.getOrders().size() > 0) {
          ordersNumber += customer.getOrders().size();
        }
      }
    }
    Assert.assertEquals(4, ordersNumber);
  }
  @Test
  public void testProductsNumber() {
    Parser staxParser = ParserFactory.getInstance().getStaxParser();
    List<User> users = staxParser.run();
    int productsNumber = 0;
    for (User user : users) {
      if (user instanceof Customer) {
        Customer customer = (Customer) user;
        if (customer.getProducts().size() > 0) {
          productsNumber += customer.getProducts().size();
        }
      }
    }
    Assert.assertEquals(9, productsNumber);
  }
  @Test
  public void testNotNullUsers() {
    DomParser domParser = new DomParser();
    List<User> users = domParser.run();
    for (User user : users) {
      Assert.assertNotNull(user);
    }
  }

  @Test
  public void testCustomersWithoutOrders() {
    Parser domParser = ParserFactory.getInstance().getDomParser();
    List<User> users = domParser.run();
    int customersNumberWithoutOrders=0;
    for (User user : users) {
      if (user instanceof Customer) {
        Customer customer = (Customer) user;
        if(customer.getOrders()==null){
          customersNumberWithoutOrders++;
        }
      }
    }
    Assert.assertEquals(3,customersNumberWithoutOrders);
  }
}