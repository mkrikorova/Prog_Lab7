package commands;

import managers.CollectionManager;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

/**Класс команды update id {element}*/
public class UpdateCommand extends Command{
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle) {
        int indexOfElement = Integer.parseInt(commandParts);

        if (collectionManager.checkExistence(indexOfElement)) {
            collectionManager.updateById(indexOfElement, vehicle);
            return new OKResponseStatus("Элемент с данным индексом был обновлен");
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
        return "Обновляет значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "update id {element}";
    }
}
