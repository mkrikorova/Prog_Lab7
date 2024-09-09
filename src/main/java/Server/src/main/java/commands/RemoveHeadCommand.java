package commands;

import managers.CollectionManager;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

import java.util.Objects;

/**Класс команды remove_head*/
public class RemoveHeadCommand extends Command{
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle) {
        Vehicle minElement = collectionManager.getCollection().stream().filter(Objects::nonNull).
                min(Vehicle::compareTo).orElse(null);
        if (minElement == null) {
            return new ExceptionStatus("Коллекция пуста");
        } else {
            collectionManager.removeById(minElement.getId());
            return new OKResponseStatus(minElement.toString());
        }
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести первый элемент коллекции и удалить его";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_head";
    }
}
