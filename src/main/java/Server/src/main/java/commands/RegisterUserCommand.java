package commands;

import managers.CollectionManager;
import managers.UserManager;
import models.User;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

public class RegisterUserCommand extends Command {

    public final CollectionManager collectionManager;
    public final UserManager userManager;

    public RegisterUserCommand(CollectionManager collectionManager, UserManager userManager) {
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user) {
        try {
            var credentials = commandParts.split("\\|");
            var userId = userManager.addUser(credentials[0], credentials[1]);
            return new OKResponseStatus(Integer.toString(userId));
        } catch (Exception e) {
            return new ExceptionStatus("Произошла ошибка при авторизации: " + e);
        }
    }

    @Override
    public String getDescription() {
        return "Регистрация пользователя";
    }

    @Override
    public String getName() {
        return "register";
    }
}