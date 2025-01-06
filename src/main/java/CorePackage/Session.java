package CorePackage;

public class Session {

    private static Session instance;
    private String loggedInUsername;
    private int currentCustomerId;

    private Session() {
        loggedInUsername = null;
        currentCustomerId = -1;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }

    public int getCurrentCustomerId() {
        return currentCustomerId;
    }

    public void setCurrentCustomerId(int currentCustomerId) {
        this.currentCustomerId = currentCustomerId;
    }

    public void clearSession() {
        loggedInUsername = null;
        currentCustomerId = -1;
    }
}
