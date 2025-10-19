package managers;

import models.User;

public interface UserManager {
    int addUser(String login, String password);
    User getUser(int user_id);
    User getUser(String login, String password);
}
