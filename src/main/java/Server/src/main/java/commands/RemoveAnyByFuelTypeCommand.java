package commands;

import exceptions.NoAccessException;
import managers.CollectionManager;
import models.User;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.FuelType;
import vehicleClasses.Vehicle;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**Класс команды remove_any_by_fuel_type fuelType*/
public class RemoveAnyByFuelTypeCommand extends Command{
    private final CollectionManager collectionManager;

    public RemoveAnyByFuelTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user) {
        FuelType fuelTypeToRemove = FuelType.getFuelType(commandParts.toLowerCase());
        if (fuelTypeToRemove == null) {
            return new ExceptionStatus("Элемент с заданным FuelType не найден");
        } else {
            Collection <Vehicle> collection = collectionManager.getCollection();
            if (collection == null) {
                return new ExceptionStatus("Коллекция пуста");
            } else {
                Optional<Vehicle> vehicleFound = collection.stream().filter(Objects::nonNull).
                        filter(checkVehicle -> checkVehicle.getFuelType() == fuelTypeToRemove &&
                                checkVehicle.getOwnerUserId() == user.getId()).findFirst();
                if (vehicleFound.isPresent()) {
                    Vehicle vehicleToRemove =  vehicleFound.get();
                    try {
                        collectionManager.removeById(vehicleToRemove.getId(), user);
                    } catch (NoAccessException e) {
                        return new ExceptionStatus(e.toString());
                    }
                    return new OKResponseStatus("Элемент с заданным FuelType найден и удален");
                } else  {
                    return new OKResponseStatus("Элемент с заданным FuelType не найден");
                }
            }
        }
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удалить из коллекции один элемент, значение поля fuelType которого эквивалентно заданному.\n" +
                "Возможные варианты: ELECTRICITY, DIESEL, ALCOHOL, PLASMA";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_any_by_fuel_type fuelType";
    }
}
