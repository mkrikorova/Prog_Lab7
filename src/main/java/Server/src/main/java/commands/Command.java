package commands;

import exceptions.ExitProgramException;
import models.User;
import statuses.Status;
import vehicleClasses.Vehicle;

public abstract class Command implements Executable {

    boolean anArgument = false;
    /**
     * Абстрактный метод для выполнения тела команды
     */
    public Status execute(String commandParts, Vehicle vehicle, User user) throws ExitProgramException {
        return null;
    }


    /**
     * Абстрактный метод для получения описания команды
     * @return описание команды
     */
    public abstract String getDescription();

    /**
     * Абстрактный метод для получения названия команды
     * @return название команды
     */
    public abstract String getName();

    /**
     * Метод, возвращающий проверку функции на наличие/отсутствие аргумента
     * @return true, если наличие/отсутствие аргумента корректно, false в обратном случае
     */
    public boolean hasAnArgument(int length) {
        if (anArgument && (length > 1)) return true;
        if (!anArgument && (length == 1)) return true;
        return false;
    }
}
