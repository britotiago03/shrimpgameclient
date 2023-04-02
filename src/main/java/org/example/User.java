package org.example;

/**
 * The User class represents a user in the system with a name and an isAdmin flag indicating whether
 * the user has administrator privileges.
 *
 * @author Tiago Brito
 * @version 1.3.0
 * @since 2023-04-02
 */
public class User {
  private String name;
  private boolean isAdmin;

  /**
   * Constructs a new User object with the specified name and isAdmin flag.
   *
   * @param name    the name of the user
   * @param isAdmin a boolean value indicating whether the user has administrator privileges
   */
  public User(String name, boolean isAdmin) {
    this.setName(name);
    this.setIsAdmin(isAdmin);
  }

  /**
   * Gets the name of the user.
   *
   * @return the name of the user
   */
  public String getName() {
    return this.name;
  }

  /**
   * Determines whether the user has administrator privileges.
   *
   * @return true if the user has administrator privileges, false otherwise
   */
  public boolean isAdmin() {
    return this.isAdmin;
  }

  /**
   * Sets the name of the user.
   *
   * @param name the name of the user
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets whether the user has administrator privileges.
   *
   * @param isAdmin a boolean value indicating whether the user has administrator privileges
   */
  public void setIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
}
