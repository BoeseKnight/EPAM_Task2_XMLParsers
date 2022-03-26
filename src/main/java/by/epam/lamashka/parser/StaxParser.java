package by.epam.lamashka.parser;

import by.epam.lamashka.entity.Customer;
import by.epam.lamashka.entity.Order;
import by.epam.lamashka.entity.Product;
import by.epam.lamashka.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StaxParser implements Parser {
  private static final Logger logger = LogManager.getLogger(StaxParser.class);
  private String id;
  private String login;
  private String password;
  private String description;
  private String content;
  private int productNumber;
  private List<User> userList = new ArrayList<>();
  private List<Product> productList = new ArrayList<>();
  private List<Order> orderList = new ArrayList<>();
  LocalDateTime date;

  @Override
  public List<User> run() {
    InputStream resource = getClass().getClassLoader().getResourceAsStream("Users.xml");
    if (resource == null) {
      throw new IllegalArgumentException("File Not Found!");
    }
    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    try {
      XMLEventReader reader = xmlInputFactory.createXMLEventReader(resource);

      while (reader.hasNext()) {

        XMLEvent xmlEvent = reader.nextEvent();
        if (xmlEvent.isStartElement()) {
          StartElement startElement = xmlEvent.asStartElement();
          switch (startElement.getName().getLocalPart()) {
            case "user":
            case "customer":
              Attribute userId = startElement.getAttributeByName(new QName("id"));
              if (userId != null) {
                id = userId.getValue();
              }
              break;
            case "login":
              xmlEvent = reader.nextEvent();
              login = xmlEvent.asCharacters().getData();
              break;
            case "password":
              xmlEvent = reader.nextEvent();
              password = xmlEvent.asCharacters().getData();
              break;
            case "productNumber":
              xmlEvent = reader.nextEvent();
              productNumber = Integer.parseInt(xmlEvent.asCharacters().getData());
              break;
            case "description":
              xmlEvent = reader.nextEvent();
              description = xmlEvent.asCharacters().getData();
              break;
            case "date":
              xmlEvent = reader.nextEvent();
              date = LocalDateTime.parse(xmlEvent.asCharacters().getData());
              break;
            case "content":
              xmlEvent = reader.nextEvent();
              content = xmlEvent.asCharacters().getData();
              break;
          }
        }

        if (xmlEvent.isEndElement()) {
          EndElement endElement = xmlEvent.asEndElement();
          switch (endElement.getName().getLocalPart()) {
            case "user":
              User user = new User.UserBuilder().login(login).password(password).id(id).build();
              userList.add(user);
              break;

            case "customer":
              User customer =
                  new Customer.CustomerBuilder()
                      .login(login)
                      .password(password)
                      .id(id)
                      .products(productList)
                      .orders(orderList)
                      .build();
              userList.add(customer);
              orderList = new ArrayList<>();
              productList = new ArrayList<>();
              break;
            case "order":
              Order order =
                  new Order.OrderBuilder().orderDateTime(date).orderContent(content).build();
              orderList.add(order);
              break;
            case "product":
              Product product =
                  new Product.ProductBuilder()
                      .productNumber(productNumber)
                      .description(description)
                      .build();
              productList.add(product);
              break;
          }
        }
      }

    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    logger.info("StAX " + userList);
    return userList;
  }
}
