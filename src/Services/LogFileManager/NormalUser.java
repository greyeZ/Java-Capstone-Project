package Services.LogFileManager;

import Enum.UserType;

public class NormalUser extends User {
    public NormalUser() {
        super(UserType.NORMAL);
    }

    @Override
    public boolean authenticate(String password) {
        // Normal users don't need a password, so always return true
        return true;
    }
}
