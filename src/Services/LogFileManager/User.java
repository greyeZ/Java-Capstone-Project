package Services.LogFileManager;

import Enum.UserType;

public abstract class User {
    private final UserType userType;

    public User(UserType userType) {
        this.userType = userType;
    }

    public abstract boolean authenticate(String password);
}
