package validators;

import exceptions.*;
import exchange.Request;
import vehicleClasses.*;

import java.util.Set;

public abstract class Validator implements IValidator {
    protected boolean needParse = false;

    /**
     * Метод для валидации команд с аргументами в строке и создания запроса для данной команды
     * @param command имя команды
     * @param args строка с аргументами
     * @return запрос
     */
    public Request validate(String command, String args) {
        return new Request(command, args, null);
    }

    /**
     * Метод для валидации команд с Vehicle и создания запроса для данной команды
     * @param command имя команды
     * @param args строка с аргументами
     * @param vehicle аргумент транспорта
     * @return запрос
     */
    public Request validate(String command, String args, Vehicle vehicle) {
        return new Request(command, args, vehicle);
    }

    /**
     * Метод для валидации команд считывающей аргументы в следующих строках и создания запроса для данной команды
     * @param command имя команды
     * @param args строка с аргументами
     * @param parse true если значения полей для Vehicle нужно считывать из файла (для команды execute_script); false - иначе
     * @return запрос
     * @throws ExitProgramException исключение для выхода из программы
     */
    public Request validate(String command, String args, boolean parse) throws ExitProgramException {
        return new Request(command, args, null);
    }

    /**
     * Проверяет существует ли данная команда
     * @param command имя команды
     * @param validCommands список существующих команд
     * @throws UnknownCommandException исключение для несуществующих команд
     */
    public static void checkIfValidCommand(String command, Set<String> validCommands) throws UnknownCommandException {
        if (!command.equals("execute_script") && !validCommands.contains(command.toLowerCase())) {
            throw new UnknownCommandException(command);
        }
    }

    /**
     * Проверяет, чтобы команде не пришли аргументы если они не требуются
     * @param commandName имя команды
     * @param commandParts строка с аргументами
     * @throws WrongArgumentsException исключение для неверных аргументов
     */
    public static void checkIfNoArguments(String commandName, String commandParts) throws WrongArgumentsException {
        if (commandParts != null && !commandParts.isEmpty()) {
            throw new WrongArgumentsException("Команда %s не принимает аргументы".formatted(commandName));
        }
    }

    /**
     * Проверяет, чтобы команде пришел один аргумент
     * @param commandName имя команды
     * @param commandParts строка с аргументами
     * @throws WrongArgumentsException исключение для неверных аргументов
     */
    public static void checkIfOneArgument(String commandName, String commandParts) throws WrongArgumentsException {
        if (commandParts == null) {
            throw new WrongArgumentsException("Команда %s принимает ровно 1 аргумент".formatted(commandName));
        }
    }

    public static String checkName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }
        return name;
    }

    public static void checkAnswer(String answer) {
        if (!answer.equals("yes") && !answer.equals("no")) {
            throw new IllegalArgumentException("Введите yes или no");
        }
    }

    public boolean getNeedParse() {
        return needParse;
    }
}
