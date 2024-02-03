package Services.LogFileManager;

public abstract class User {
    private final UserType userType;

    public User(UserType userType) {
        this.userType = userType;
    }

    public abstract boolean authenticate(String password);
}
