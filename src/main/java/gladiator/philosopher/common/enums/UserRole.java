package gladiator.philosopher.common.enums;

public enum UserRole {
  ROLE_USER(Authority.ROLE_USER),
  ROLE_ADMIN(Authority.ROLE_ADMIN);

  private final String authority;

  UserRole(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }

  public static class Authority {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
  }
}
