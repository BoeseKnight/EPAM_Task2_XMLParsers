package by.epam.lamashka.parser;

import by.epam.lamashka.entity.Customer;
import by.epam.lamashka.entity.Order;
import by.epam.lamashka.entity.Product;
import by.epam.lamashka.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaxParser implements Parser {
  private static final Logger logger = LogManager.getLogger(SaxParser.class);
  private List<User> userList = new ArrayList<>();
  private List<Product> productList = new ArrayList<>();
  private List<Order> orderList = new ArrayList<>();
  private String id;
  private String login;
  private String password;
  private String lastElementName;
  private String orderContent;
  private String orderDateTime;
  private String productNumber;
  private String description;

  @Override
  public List<User> run() {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = null;
    try {
      parser = factory.newSAXParser();
      XMLHandler handler = new XMLHandler();
      URL resource = getClass().getClassLoader().getResource("Users.xml");
      if (resource == null) {
        throw new IllegalArgumentException("File Not Found!");
      }
      File usersFile = null;
      usersFile = new File(resource.toURI());
      parser.parse(usersFile, handler);
    } catch (URISyntaxException | ParserConfigurationException | IOException | SAXException e) {
      e.printStackTrace();
    }
    logger.info(" SAX " + userList);
    return userList;
  }

  private class XMLHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
      lastElementName = qName;
      if (qName.equals("user")) {
        id = attributes.getValue("id");
      } else if (qName.equals("customer")) {
        id = attributes.getValue("id");
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      switch (qName) {
        case "user":
          User user = new User.UserBuilder().login(login).password(password).id(id).build();
          userList.add(user);
          break;
        case "customer":
          user =
              new Customer.CustomerBuilder()
                  .login(login)
                  .password(password)
                  .id(id)
                  .products(productList)
                  .orders(orderList)
                  .build();
          userList.add(user);
          orderList = new ArrayList<>();
          productList = new ArrayList<>();
          break;
        case "product":
          Product product =
              new Product.ProductBuilder()
                  .productNumber(Integer.parseInt(productNumber))
                  .description(description)
                  .build();
          productList.add(product);
          break;
        case "order":
          Order order =
              new Order.OrderBuilder()
                  .orderDateTime(LocalDateTime.parse(orderDateTime))
                  .orderContent(orderContent)
                  .build();
          orderList.add(order);
          break;
      }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      String information = new String(ch, start, length);
      information = information.replace("\n", "").trim();
      if (!information.isEmpty()) {
        switch (lastElementName) {
          case "login":
            login = information;
            break;
          case "password":
            password = information;
            break;
          case "productNumber":
            productNumber = information;
            break;
          case "description":
            description = information;
            break;
          case "date":
            orderDateTime = information;
            break;
          case "content":
            orderContent = information;
            break;
        }
      }
    }
  }
}
