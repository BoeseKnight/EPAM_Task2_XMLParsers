package by.epam.lamashka.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
  protected String login;
  protected String password;
  protected String id;

  public User() {}

  User(UserBuilder builder) {
    this.login = builder.login;
    this.password = builder.password;
    this.id = builder.id;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public String getId() {
    return id;
  }

  public static class UserBuilder<S extends UserBuilder> {
    protected String login;
    protected String password;
    protected String id;

    public UserBuilder() {}

    public S login(String login) {
      this.login = login;
      return (S) this;
    }

    public S password(String password) {
      this.password = password;
      return (S) this;
    }

    public S id(String id) {
      this.id = id;
      return (S) this;
    }

    public User build() {
      return new User(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return login.equals(user.login) && password.equals(user.password) && id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, password, id);
  }

  @Override
  public String toString() {
    return "User{"
        + "login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + ", id='"
        + id
        + '\''
        + '}';
  }
}
