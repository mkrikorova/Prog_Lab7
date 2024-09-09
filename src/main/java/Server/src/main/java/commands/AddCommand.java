package commands;


import managers.CollectionManager;
import statuses.Status;
import vehicleClasses.Vehicle;

/**Класс команды add {element}*/

public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     * @param vehicle объект который нужно добавить
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle) {
        return collectionManager.add(vehicle);
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "add {element}";
    }
}