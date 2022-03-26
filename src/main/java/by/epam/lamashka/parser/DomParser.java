package by.epam.lamashka.parser;

import by.epam.lamashka.entity.Customer;
import by.epam.lamashka.entity.Order;
import by.epam.lamashka.entity.Product;
import by.epam.lamashka.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DomParser implements Parser {
  private static final Logger logger = LogManager.getLogger(DomParser.class);
  URL resource;

  public DomParser() {}

  public DomParser(URL resource) {
    this.resource = resource;
  }

  @Override
  public List<User> run() {
    List<User> userList = new ArrayList<>();
    Document document = null;
    try {
      document = documentBuilder();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    Node users = document.getFirstChild();
    NodeList usersNodes = users.getChildNodes();
    Node userNode;
    Node customerNode;

    for (int i = 0; i < usersNodes.getLength(); i++) {
      if (usersNodes.item(i).getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      switch (usersNodes.item(i).getNodeName()) {
        case "user":
          userNode = usersNodes.item(i);
          User user = parseUser(userNode);
          userList.add(user);
          break;
        case "customer":
          customerNode = usersNodes.item(i);
          User customer = parseCustomer(customerNode);
          userList.add(customer);
          break;
      }
    }
    logger.info("DOM" + userList);
    return userList;
  }

  private Document documentBuilder() throws URISyntaxException {
    URL resource = getClass().getClassLoader().getResource("Users.xml");
    if (resource == null) {
      throw new IllegalArgumentException("File Not Found!");
    }
    File usersFile = new File(resource.toURI());
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    Document document = null;
    try {
      document = documentBuilderFactory.newDocumentBuilder().parse(usersFile);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }
    return document;
  }

  private User parseUser(Node userNode) {
    String login = "";
    String password = "";
    String id;
    id = userNode.getAttributes().item(0).getTextContent();
    NodeList userElements = userNode.getChildNodes();
    for (int i = 0; i < userElements.getLength(); i++) {
      if (userElements.item(i).getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      switch (userElements.item(i).getNodeName()) {
        case "login":
          login = userElements.item(i).getTextContent();
          break;
        case "password":
          password = userElements.item(i).getTextContent();
          break;
        case "id":
          id = userElements.item(i).getTextContent();
          break;
      }
    }
    return new User.UserBuilder().login(login).password(password).id(id).build();
  }

  private User parseCustomer(Node customerNode) {
    User user = parseUser(customerNode);
    List<Product> products = null;
    List<Order> orders = null;
    NodeList userElements = customerNode.getChildNodes();
    for (int i = 0; i < userElements.getLength(); i++) {
      if (userElements.item(i).getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      switch (userElements.item(i).getNodeName()) {
        case "products":
          Node productsNode = userElements.item(i);
          products = customerProductsParse(productsNode);
          break;
        case "orders":
          Node ordersNode = userElements.item(i);
          orders = customerOrdersParse(ordersNode);
          break;
      }
    }
    return new Customer.CustomerBuilder()
        .login(user.getLogin())
        .password(user.getPassword())
        .id(user.getId())
        .products(products)
        .orders(orders)
        .build();
  }

  private List<Product> customerProductsParse(Node productsNode) {
    int productNumber = 0;
    String description = "";
    List<Product> productsList = new ArrayList<>();
    NodeList productsNodes = productsNode.getChildNodes();
    for (int i = 0; i < productsNodes.getLength(); i++) {
      if (productsNodes.item(i).getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      if (productsNodes.item(i).getNodeName().equals("product")) {
        NodeList productElements = productsNodes.item(i).getChildNodes();
        for (int j = 0; j < productElements.getLength(); j++) {
          if (productElements.item(j).getNodeType() != Node.ELEMENT_NODE) {
            continue;
          }
          if (productElements.item(j).getNodeName().equals("productNumber")) {
            productNumber = Integer.parseInt(productElements.item(j).getTextContent());
          } else if (productElements.item(j).getNodeName().equals("description")) {
            description = productElements.item(j).getTextContent();
          }
        }
        productsList.add(
            new Product.ProductBuilder()
                .productNumber(productNumber)
                .description(description)
                .build());
      }
    }
    return productsList;
  }

  private List<Order> customerOrdersParse(Node ordersNode) {
    LocalDateTime orderDateTime = null;
    String orderContent = "";
    List<Order> orderList = new ArrayList<>();
    NodeList ordersNodes = ordersNode.getChildNodes();
    for (int i = 0; i < ordersNodes.getLength(); i++) {
      if (ordersNodes.item(i).getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      if (ordersNodes.item(i).getNodeName().equals("order")) {
        NodeList productElements = ordersNodes.item(i).getChildNodes();
        for (int j = 0; j < productElements.getLength(); j++) {
          if (productElements.item(j).getNodeType() != Node.ELEMENT_NODE) {
            continue;
          }
          if (productElements.item(j).getNodeName().equals("date")) {
            orderDateTime = LocalDateTime.parse(productElements.item(j).getTextContent());
          } else if (productElements.item(j).getNodeName().equals("content")) {
            orderContent = productElements.item(j).getTextContent();
          }
        }
        orderList.add(
            new Order.OrderBuilder()
                .orderDateTime(orderDateTime)
                .orderContent(orderContent)
                .build());
      }
    }
    return orderList;
  }
}
