import by.epam.lamashka.entity.Customer;
import by.epam.lamashka.entity.User;
import by.epam.lamashka.parser.DomParser;
import by.epam.lamashka.parser.SaxParser;
import by.epam.lamashka.parser.StaxParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTests {
  @Test
  public void testUser() {
    DomParser domParser = new DomParser();
    List<User> users = domParser.run();
    Assert.assertEquals(
        users.get(0), new User.UserBuilder().login("Ilya").password("2").id("ID-1").build());
  }

  @Test
  public void testUserLogins() {
    List<String> expectedLogins =
        Arrays.asList("Ilya", "Ksenia", "Andrew", "Dima", "Egor", "Karol", "Jim", "Jack");
    DomParser domParser = new DomParser();
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
    StaxParser staxParser = new StaxParser();
    List<User> users = staxParser.run();
    List<String> parserPasswords = new ArrayList<>();
    for (User user : users) {
      parserPasswords.add(user.getPassword());
    }
    Assert.assertEquals(expectedPasswords, parserPasswords);
  }

  @Test
  public void testCustomersNumber() {
    SaxParser saxParser = new SaxParser();
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
    SaxParser saxParser = new SaxParser();
    List<User> users = saxParser.run();
    int usersNumber = users.size();
    Assert.assertEquals(8, usersNumber);
  }

  @Test
  public void testOrdersNumber() {
    StaxParser staxParser = new StaxParser();
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
    StaxParser staxParser = new StaxParser();
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
}