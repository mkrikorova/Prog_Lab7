package commands;


import managers.CollectionManager;
import models.User;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

/**Класс команды info*/
public class InfoCommand extends Command{

    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user) {
        return new OKResponseStatus(this.collectionManager.info())  ;
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции " +
                "(тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "info";
    }
}
