package commands;


import managers.*;
import statuses.OKStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

/**Класс команды clear*/
public class ClearCommand extends Command{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "clear";
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle) {
        collectionManager.clear();
        Vehicle.updateUniqueId(collectionManager.getCollection());
        return new OKStatus();
    }
}
