package by.epam.lamashka.parser;

public class ParserFactory {
  private static final ParserFactory instance = new ParserFactory();
  private final Parser domParser = new DomParser();
  private final Parser saxParser = new SaxParser();
  private final Parser staxParser = new StaxParser();

  public static ParserFactory getInstance() {
    return instance;
  }

  public Parser getStaxParser() {
    return staxParser;
  }

  public Parser getSaxParser() {
    return saxParser;
  }

  public Parser getDomParser() {
    return domParser;
  }
}
