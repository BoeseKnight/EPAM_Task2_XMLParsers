package by.epam.lamashka;

import by.epam.lamashka.parser.DomParser;
import by.epam.lamashka.parser.SaxParser;
import by.epam.lamashka.parser.StaxParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.net.URISyntaxException;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("XML_PROJECT");
    DomParser domParser=new DomParser();
    try {
      domParser.run();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    StaxParser staxParser=new StaxParser();
    try {
      staxParser.run();
    } catch (URISyntaxException | XMLStreamException e) {
      e.printStackTrace();
    }
    SaxParser saxParser=new SaxParser();
    try {
      saxParser.run();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
