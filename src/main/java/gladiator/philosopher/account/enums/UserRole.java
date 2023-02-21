package gladiator.philosopher.account.enums;

public enum UserRole {
  ROLE_USER(Authority.ROLE_USER),
  ROLE_ADMIN(Authority.ROLE_ADMIN),
  ROLE_MANAGER(Authority.ROLE_MANAGER);

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
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
  }
}
