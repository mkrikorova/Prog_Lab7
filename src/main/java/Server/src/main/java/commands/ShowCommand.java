package commands;


import managers.CollectionManager;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

/**Класс команды show*/
public class ShowCommand extends Command {

    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle) {
        return new OKResponseStatus(collectionManager.show());
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "show";
    }
}
