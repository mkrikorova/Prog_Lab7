package commands;

import managers.CollectionManager;
import statuses.ExceptionStatus;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

import java.util.Collection;
import java.util.Objects;

/**Класс команды remove_lower {element}*/
public class RemoveLowerCommand extends Command{
    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle compareVehicle) {
        Collection <Vehicle> collection = collectionManager.getCollection();
        if (collection == null) {
            return new ExceptionStatus("Коллекция пуста");
        } else {
            Collection<Vehicle> toRemove = collection.stream().filter(Objects::nonNull)
                    .filter(vehicle -> vehicle.compareTo(compareVehicle) < 0).toList();
            for (Vehicle v: toRemove) {
                collectionManager.removeById(v.getId());
            }
            return new OKResponseStatus("Элементы, меньшие, чем заданный, найдены и удалены");
        }
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удаляет из коллекции все элементы, меньшие, чем заданный";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_lower {element}";
    }}
