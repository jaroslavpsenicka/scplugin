package cz.csas.smart.idea;

public class UserComponent {

    private static UserComponent instance = new UserComponent();
    private String user = System.getenv().get("USERNAME");

    public static final UserComponent getInstance() {
        return instance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
