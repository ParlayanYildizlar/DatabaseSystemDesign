package CorePackage;

import java.util.ArrayList;

/**
 *
 * @author sare
 */
public class Database {

    private static final ArrayList<User> user = new ArrayList<>();

    public static ArrayList<User> getUser() {
        return user;
    }

    public static User loginVerification(String username, String password) {
        User account = null;
        for (User user : user) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                account = user;
                break;
            }
        }

        return account;
    }

    public static boolean checkUsername(String username) {
        boolean flag = false;
        for (User user : Database.getUser()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
