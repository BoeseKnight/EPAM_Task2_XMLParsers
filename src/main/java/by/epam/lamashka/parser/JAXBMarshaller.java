package by.epam.lamashka.parser;

import by.epam.lamashka.entity.User;
import by.epam.lamashka.entity.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class JAXBMarshaller implements JAXB {
  private static final Logger logger = LogManager.getLogger(JAXBMarshaller.class);

  @Override
  public void run(List<User> userList) {
    // (1) Marshaller : Java Object to XML content.
    try {
      JAXBContext context = JAXBContext.newInstance(Users.class);
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      Users usersMarshalling = new Users(userList);
      logger.info("Users Marshalling:");
      // Write to System.out
      m.marshal(usersMarshalling, System.out);

      // Write to File
      File outFile = new File("marshall.xml");
      m.marshal(usersMarshalling, outFile);

      System.out.println("Users are written to file: " + outFile.getAbsolutePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
