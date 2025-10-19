package commands;

import managers.CommandManager;
import models.User;
import statuses.OKResponseStatus;
import statuses.Status;
import vehicleClasses.Vehicle;

/**Класс команды help*/
public class HelpCommand extends Command{
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Исполняет команду
     */
    @Override
    public Status execute(String commandParts, Vehicle vehicle, User user) {
        StringBuilder result = new StringBuilder();
        for (Command c : CommandManager.getCommands()) {
            if (!c.getName().equals("login") && !c.getName().equals("register")) {
                result.append(c.getName());
                result.append(":  ");
                result.append(c.getDescription());
                result.append("\n");
            }
        }
        result.append("execute_script script: " + "Считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, \n" +
                "в котором их вводит пользователь в интерактивном режиме. " +
                "Указывать абсолютный путь до скрипта\n");
        result.append("Замечание: аргументы в фигурных скобках вводятся с новой строки.");
        return new OKResponseStatus(result.toString());
    }


    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "help";
    }
}
