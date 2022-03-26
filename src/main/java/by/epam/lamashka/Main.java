package by.epam.lamashka;

import by.epam.lamashka.entity.User;
import by.epam.lamashka.parser.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("XML_PROJECT");
    Parser domParser = ParserFactory.getInstance().getDomParser();
    List<User> userList = domParser.run();
    Parser staxParser = ParserFactory.getInstance().getStaxParser();
    staxParser.run();
    Parser saxParser = ParserFactory.getInstance().getSaxParser();
    saxParser.run();
    JAXB marshaller = new JAXBMarshaller();
    JAXB unmarshaller = new JAXBUnmarshaller();
    marshaller.run(userList);
    unmarshaller.run(userList);
  }
}
