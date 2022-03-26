package by.epam.lamashka.parser;

import by.epam.lamashka.entity.Customer;
import by.epam.lamashka.entity.User;
import by.epam.lamashka.entity.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JAXBMarshaller implements JAXB {
  private static final Logger logger = LogManager.getLogger(JAXBMarshaller.class);

  @Override
  public void run(List<User> userList) {
    List<Customer> customerList = new ArrayList<>();
    // (1) Marshaller : Java Object to XML content.
    for (User user : userList) {
      if (user instanceof Customer) {
        customerList.add((Customer) user);
      }
    }
    try {
      JAXBContext context = JAXBContext.newInstance(Users.class);
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      Users usersMarshalling = new Users(userList);
      logger.info("Users Marshalling:");
      m.marshal(usersMarshalling, System.out);
      File outFile = new File("src/main/resources/marshall.xml");
      m.marshal(usersMarshalling, outFile);

      System.out.println("Users are written to file: " + outFile.getAbsolutePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
