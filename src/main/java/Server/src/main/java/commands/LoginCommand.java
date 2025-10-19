package commands;

import exceptions.ExitProgramException;
import managers.CollectionManager;
import managers.UserManager;
import models.User;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

public class LoginCommand extends Command {

    public final CollectionManager collectionManager;
    public final UserManager userManager;

    public LoginCommand(CollectionManager collectionManager, UserManager userManager) {
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user) {
        try {
            var credentials = commandParts.split("\\|");
            user = userManager.getUser(credentials[0], credentials[1]);
            if (user != null) {
                return new OKResponseStatus(Integer.toString(user.getId()));
            }
            return new ExceptionStatus("Логин или пароль неверны");
        } catch (Exception e) {
            return new ExceptionStatus("Произошла ошибка при авторизации: " + e);
        }
    }

    @Override
    public String getDescription() {
        return "Авторизация пользователя";
    }

    @Override
    public String getName() {
        return "login";
    }
}