package by.epam.lamashka.jaxb;

import by.epam.lamashka.entity.User;
import by.epam.lamashka.entity.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.util.List;

public class JAXBUnmarshaller implements JAXB {
  private static final Logger logger = LogManager.getLogger(JAXBUnmarshaller.class);

  @Override
  public void run(List<User> userList) {
    try {
      JAXBContext context = JAXBContext.newInstance(Users.class);

      Unmarshaller unmarshaller = context.createUnmarshaller();

      Users deptFromFile =
          (Users) unmarshaller.unmarshal(new FileReader("src/main/resources/marshall.xml"));
      userList = deptFromFile.getUsers();
      logger.info("Unmarshall Users:");
      for (User user : userList) {
        logger.info(user);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
