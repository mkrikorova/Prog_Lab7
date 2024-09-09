package commands;

import managers.*;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

import java.util.Collection;
import java.util.List;

/**Класс команды filter_starts_with_name name*/
public class FilterStartsWithNameCommand extends Command {
    private final CollectionManager collectionManager;

    public FilterStartsWithNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle) {
        Collection <Vehicle> collection = collectionManager.getCollection();
        if (collection == null) {
            return new ExceptionStatus("Коллекция пуста");
        } else {
            List<Vehicle> found = collection.stream()
                    .filter(checkVehicle -> checkVehicle.getName().startsWith(commandParts))
                    .toList();
            if (found.isEmpty()) {
                return new ExceptionStatus("Таких элементов нет :(");
            } else {
                for (Vehicle v : found) {
                    return new OKResponseStatus(v.toString());
                }
            }
        }
        return null;
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "filter_starts_with_name name";
    }
}
