package managers;

import commands.*;
import exceptions.*;
import statuses.*;
import vehicleClasses.*;

import java.util.*;

public class CommandManager {
    /**
     * Коллекция для хранения всех команд с объектами этих команд
     */
    private static Map<String, Command> commands;
    private final CollectionManager collectionManager;
    private final UserManager userManager;

   public CommandManager(CollectionManager collectionManager) {
       this.collectionManager = collectionManager;
       this.userManager = new database.postgres.UserManager();
       commands = new HashMap<>();
       this.setCommands();
   }


    /**
     * Метод для добавления в коллекцию команды
     * @param commandName имя команды, то, как её будет вводить пользователь
     * @param command ссылка на объект этой команды
     */
    public static void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public void setCommands() {
        commands.put("add", new AddCommand(collectionManager));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager));
        commands.put("average_of_number_of_wheels", new AverageOfNumberOfWheelsCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(collectionManager));
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("remove_any_by_fuel_type", new RemoveAnyByFuelTypeCommand(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("remove_lower", new RemoveLowerCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("update", new UpdateCommand(collectionManager));

        commands.put("login", new LoginCommand(collectionManager, userManager));
        commands.put("register", new RegisterUserCommand(collectionManager, userManager));
    }

    public Status execute(String commandName, String args, Vehicle vehicle, String login, String password) throws ExitProgramException {
        Command command = commands.get(commandName);
        var user = userManager.getUser(login, password);
        if (user == null && command != commands.get("login") && command != commands.get("register")) {
            return new ExceptionStatus("Вы не авторизованы");
        }
        Status status = command.execute(args, vehicle, user);
        return status;
    }


    /**
     * Метод для получения команды по её названию
     * @param arg название команды, введенное пользователем
     * @return ссылку на объект команды
     * @throws exceptions.UnknownCommandException выбрасывается, если такой команды не существует
     */
    public static Command getCommand(String arg) throws UnknownCommandException {
        var command = commands.get(arg);
        if (command == null)
            throw new UnknownCommandException("");
        return commands.get(arg);
    }

    /**
     * Метод для получения списка всех команд
     * @return список команд
     */
    public static Collection<Command> getCommands() {
        return commands.values();
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

}
