package commands;

import exceptions.NoAccessException;
import managers.CollectionManager;
import models.User;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

import java.util.Scanner;

/**Класс команды remove_by_id id*/
public class RemoveById extends Command{
    CollectionManager collectionManager;
    Scanner scanner;

    public RemoveById(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user)  {
        int index = Integer.parseInt(commandParts);
        if (collectionManager.checkExistence(index)) {
            try {
                collectionManager.removeById(index, user);
            } catch (NoAccessException e) {
                return new ExceptionStatus(e.toString());
            }
            return new OKResponseStatus("Элемент с заданным индексом найден и удален");
        } else {
            return new ExceptionStatus("Такого индекса не существует");
        }
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Удаляет элемент из коллекции по его id";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_by_id id";
    }
}
