package commands;

import managers.*;
import models.User;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.*;

import java.util.Collection;

/**Класс команды average_of_number_of_wheels*/
public class AverageOfNumberOfWheelsCommand extends Command {
    private final CollectionManager collectionManager;

    public AverageOfNumberOfWheelsCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user) {
        Collection <Vehicle>  collection = collectionManager.getCollection();
        if (collection == null) {
            return new ExceptionStatus("Коллекция пуста.");
        } else {
            double averageNumberOfWheels = collection.stream()
                    .mapToDouble(Vehicle::getNumberOfWheels)
                    .average()
                    .orElse(0.0);
            return new OKResponseStatus("Среднее значение numberOfWheels: " + averageNumberOfWheels);
        }
    }


    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести среднее значение поля numberOfWheels для всех элементов коллекции";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "average_of_number_of_wheels";
    }
}
