package Services.LogFileManager;

import java.util.Objects;

public class AdminUser extends User {
    private static final String ADMIN_PASSWORD = "1234";

    public AdminUser() {
        super(UserType.ADMIN);
    }

    @Override
    public boolean authenticate(String password) {
        return Objects.equals(password, ADMIN_PASSWORD);
    }
}
