package validators;

import command.ExecuteScriptCommand;
import exceptions.*;
import exchange.Request;
import utils.*;
import vehicleClasses.*;

import java.util.*;
import java.util.regex.Pattern;

public class ReadValidator extends Validator {
    protected boolean needParse = true;

    private Vehicle newVehicle = null;

    private final static int maxCountOfAttempts = 50000;

    @Override
    public Request validate(String command, String args, boolean parse) throws ExitProgramException {
        try {
            Vehicle vehicle = null;
            if (!parse)
                vehicle = readVehicle();
            else {
                vehicle = parseVehicle(ExecuteScriptCommand.readAllArguments());
            }

            return super.validate(command, args, vehicle);
        } catch (WrongArgumentsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Метод считывающий значения полей для типа Vehicle из консоли
     * @return объект Vehicle с полями, прошедшими валидацию
     * @throws WrongArgumentsException  исключение выбрасывающееся при неверном типе аргументов
     * @throws ExitProgramException исключение для выхода из программы
     */
    public Vehicle readVehicle() throws WrongArgumentsException, ExitProgramException {
        Scanner scanner = new Scanner(System.in);
        String name = null;
        int x = 0;
        double y = 0;
        Coordinates coordinates = null;
        double enginePower = 0;
        int numberOfWheels = 0;
        Double fuelConsumption = null;
        String nameOfFuelType = null;

        try {
            name = askName(scanner,"");
            x = askCoordinateX(scanner, "");
            y = askCoordinateY(scanner, "");
            coordinates = new Coordinates(x, y);
            enginePower = askEnginePower(scanner, "");
            numberOfWheels = askNumberOfWheels(scanner, "");
            fuelConsumption = askFuelConsumption(scanner, "");
            nameOfFuelType = askFuelType(scanner, "");

            newVehicle = new Vehicle(name, coordinates, enginePower, numberOfWheels,
                    fuelConsumption, nameOfFuelType);
        } catch (ToMuchAttemptsException t) {
            ColorOutput.printlnRed("Ты совсем долбаёб? Блять так сложно что ли прочитать что от тебя просят. " +
                    "Нет конечно интереснее долбиться до последнего. Мало ли сработает?))\n" +
                    "Используй свой мозг по назначению, пожалуйста.");
        } catch (NumberFormatException n) {
            ColorOutput.printlnRed("Введенные значения не соответствуют типам Java.");
        } catch (NoSuchElementException e) {
            ColorOutput.printlnRed("Выход из программы...");
            throw new ExitProgramException();
        }
        return newVehicle;
    }

    /**
     * Метод для получения значений полей для типа Vehicle из файла
     * @param args аргументы считанные из файла
     * @return объект Vehicle с полями, прошедшими валидацию
     * @throws WrongArgumentsException исключение выбрасывающееся при неверном типе аргументов
     */
    public Vehicle parseVehicle(ArrayList<String> args) throws WrongArgumentsException {
        String name = null;
        int x = 0;
        double y = 0;
        Coordinates coordinates = null;
        double enginePower = 0;
        int numberOfWheels = 0;
        Double fuelConsumption = null;
        String nameOfFuelType = null;

        if (args == null) {
            ColorOutput.printlnRed("Аргументы для команды не указаны. " +
                    "Исправьте файл и попробуйте ещё.");
        } else if (args.size() < 6 || args.size() > 7) {
            ColorOutput.printlnRed("В файле неверное количество аргументов " +
                    "для команды. Исправьте файл и попробуйте ещё.");
        } else {
            try {
                name = askName(null, args.get(0));
                x = askCoordinateX(null, args.get(1));
                y = askCoordinateY(null, args.get(2));
                coordinates = new Coordinates(x, y);
                enginePower =  askEnginePower(null, args.get(3));
                numberOfWheels =  askNumberOfWheels(null, args.get(4));
                fuelConsumption = askFuelConsumption(null, args.get(5));
                if (args.size() == 7) {
                    nameOfFuelType = askFuelType(null, args.get(6));
                }
                newVehicle = new Vehicle(name, coordinates, enginePower, numberOfWheels, fuelConsumption, nameOfFuelType);
            } catch (ToMuchAttemptsException t) {
                ColorOutput.printlnRed("Ты совсем долбаёб? Блять так сложно что ли прочитать что от тебя просят. " +
                        "Нет конечно интереснее долбиться до последнего. Мало ли сработает?))\n" +
                        "Используй свой мозг по назначению, пожалуйста.");
            } catch (NumberFormatException n) {
                ColorOutput.printlnRed("Введенные значения не соответствуют типам Java.");
            }
        }
        return newVehicle;
    }


    /**
     * Запрашивает имя
     * @param str строка для имени
     * @return верное имя
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public String askName(Scanner scanner, String str) throws ToMuchAttemptsException, WrongArgumentsException {
        String name;
        if (str.isEmpty()) {
            System.out.println("Введите имя транспорта (поле не может быть null, строка не может быть пустой): ");
            System.out.print(">> ");
            str = scanner.nextLine();
            name = checkName(str, scanner);
        } else {
            name = checkName(str, scanner);
        }
        return name;
    }

    /**
     * Запрашивает координату X
     * @param argForX строка для координаты X
     * @return коордианату подходящую под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public Integer askCoordinateX(Scanner scanner, String argForX) throws ToMuchAttemptsException, WrongArgumentsException {
        int x = 0;
        if (argForX.isEmpty()) {
            System.out.println("Введите координату X (типа int, значение поля должно быть больше -971):");
            System.out.print(">> ");
            argForX = scanner.nextLine();
        }
        x = checkIfInteger(argForX, scanner, "Координата X");
        while (x <= -971) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            x = checkIfInteger(scanner.nextLine(), scanner, "Координата X");
        }
        return x;
    }

    /**
     * Запрашивает координату Y
     * @param argForY строка для координаты Y
     * @return коордианату подходящую под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public Double askCoordinateY(Scanner scanner, String argForY) throws ToMuchAttemptsException, WrongArgumentsException {
        double y = 0;
        if (argForY.isEmpty()) {
            System.out.println("Введите координату Y (типа double):");
            System.out.print(">> ");
            argForY = scanner.nextLine();
        }
        y = checkIfDouble(argForY, scanner, "Координата Y");
        return y;
    }

    /**
     * Запрашивает мощность двигателя
     * @param arg строка для мощности двигателя
     * @return мощность двигателя подходящую под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public double askEnginePower(Scanner scanner, String arg) throws ToMuchAttemptsException, WrongArgumentsException {
        double enginePower;
        if (arg.isEmpty()) {
            System.out.println("Введите мощность двигателя (типа double, значение поля должно быть больше 0):");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        enginePower = checkIfDouble(arg, scanner, "Мощность двигателя");
        while (enginePower <= 0) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            enginePower = checkIfDouble(scanner.nextLine(), scanner, "Мощность двигателя");
        }
        return enginePower;
    }

    /**
     * Запрашивает количество колес
     * @param arg строка для количества колес
     * @return количсетво колес подходящее под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public int askNumberOfWheels(Scanner scanner, String arg) throws ToMuchAttemptsException, WrongArgumentsException {
        int numberOfWheels;
        if (arg.isEmpty()) {
            System.out.println("Введите количество колес (типа int, значение поля должно быть больше 0):");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        numberOfWheels = checkIfInteger(arg, scanner, "Количество колес");
        while (numberOfWheels <= 0) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            numberOfWheels = checkIfInteger(scanner.nextLine(), scanner, "Количество колес");
        }
        return numberOfWheels;
    }

    /**
     * Запрашивает потребление топлива
     * @param arg строка для потребления топлива
     * @return потребление топлива подходящее под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public Double askFuelConsumption(Scanner scanner, String arg) throws ToMuchAttemptsException, WrongArgumentsException {
        double fuelConsumption;
        if (arg.isEmpty()) {
            System.out.println("Введите потребление топлива (типа Double, поле не может быть null, значение поля должно быть больше 0):");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        fuelConsumption = checkIfDouble(arg, scanner, "Потребление топлива");
        while (fuelConsumption <= 0) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            fuelConsumption = checkIfDouble(scanner.nextLine(), scanner, "Потребление топлива");
        }
        return fuelConsumption;
    }

    /**
     * Запрашивает тип топлива
     * @param arg строка для типа топлива
     * @return строку с именем типа топлива подходящего под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public String askFuelType(Scanner scanner, String arg) throws ToMuchAttemptsException, WrongArgumentsException {
        String nameOfFuelType;
        if (arg.isEmpty()) {
            System.out.println("Введите тип топлива (выберите один из перечисленных:  ELECTRICITY, DIESEL, ALCOHOL, PLASMA): ");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        nameOfFuelType = checkIfConstantFuelType(arg, scanner);
        if (nameOfFuelType.isEmpty()) {
            return null;
        } else {
            return nameOfFuelType;
        }
    }

    /**
     * Проверяет, является ли данная строка str числом типа int
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @param fieldName имя поля, для которого идет проверка
     * @return числовое значение для поля
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static Integer checkIfInteger (String str, Scanner scanner, String fieldName) throws ToMuchAttemptsException,
            WrongArgumentsException {
        int n = 0;
        str = str.toLowerCase(Locale.ROOT);
        if (!str.isEmpty() && ('i' == str.charAt(str.length() - 1) || 'l' == str.charAt(str.length() - 1))) {
            str = str.substring(0, str.length() - 1);
        }
        while (str.isEmpty() || !(Pattern.matches("-\\d+|\\d+", str))) {
            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            if (scanner.equals(null)) {
                throw new WrongArgumentsException(fieldName + " не типа int. Команда не будет выполнена.");
            }
            ColorOutput.printlnRed(fieldName + " не типа int. Попробуйте еще:");
            System.out.print(">> ");
            str = scanner.nextLine();
            if (!str.isEmpty() && ('i' == str.charAt(str.length() - 1) || 'l' == str.charAt(str.length() - 1))) {
                str = str.substring(0, str.length() - 1);
            }
            n++;
        }
        return Integer.parseInt(str);
    }

    /**
     * Проверяет, является ли данная строка str числом типа double
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @param fieldName имя поля, для которого идет проверка
     * @return числовое значение для поля
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static Double checkIfDouble (String str, Scanner scanner, String fieldName) throws ToMuchAttemptsException {
        int n = 0;
        str = str.toLowerCase(Locale.ROOT);
        str = str.replace(",", ".");

        if (!str.isEmpty() && ('f' == str.charAt(str.length() - 1) || 'd' == str.charAt(str.length() - 1))) {
            str = str.substring(0, str.length() - 1);
        }
        while (str.isEmpty() || !(Pattern.matches("^[-+]?[0-9]*[.,]?[0-9]+(?:[eE][-+]?[0-9]+)?$", str))) {
            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            ColorOutput.printlnRed(fieldName + " в неправильном формате. Попробуйте еще:");
            System.out.print(">> ");
            str = scanner.nextLine();
            if (!str.isEmpty() && ('f' == str.charAt(str.length() - 1) || 'd' == str.charAt(str.length() - 1))) {
                str = str.substring(0, str.length() - 1);
            }
            n++;
        }
        return Double.parseDouble(str);
    }

    /**
     * Проверяет, является ли данная строка str одной из констант FuelType
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @return строку с именем константы
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static String checkIfConstantFuelType (String str, Scanner scanner) throws ToMuchAttemptsException,
            WrongArgumentsException {
        int n = 0;

        class IfConstant {
            final ArrayList<String> constants = new ArrayList<>();

            IfConstant () {
                constants.add("electricity");
                constants.add("diesel");
                constants.add("alcohol");
                constants.add("plasma");
                constants.add("1");
                constants.add("2");
                constants.add("3");
                constants.add("4");
            }

            public boolean checker (String str) {
                return constants.contains(str.toLowerCase());
            }
        }

        IfConstant ifConstant = new IfConstant();
        while (! (ifConstant.checker(str))) {
            if (str.isEmpty())
                return str;

            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            if (scanner.equals(null)) {
                throw new WrongArgumentsException("Такой константы нет.Команда не будет выполнена.");
            }
            ColorOutput.printlnRed("Такой константы нет. Попробуйте еще. Если ни одна константа не подходит, нажмите ENTER");
            System.out.print(">> ");
            str = scanner.nextLine();
            n++;
        }
        return str;
    }

    /**
     * Проверяет, соответствует ли строка str требованиям к полю имени
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @return верное имя
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static String checkName(String str, Scanner scanner) throws ToMuchAttemptsException,
            WrongArgumentsException {
        int n = 0;
        while (str == null || str.isEmpty()) {
            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            if (scanner.equals(null)) {
                throw new WrongArgumentsException("Имя в неправильном формате. Команда не будет выполнена.");
            }
            ColorOutput.printlnRed("Имя в неправильном формате. Попробуйте еще:");
            System.out.print(">> ");
            str = scanner.nextLine();
            n++;
        }
        return str;
    }

    public boolean getNeedParse() {
        return needParse;
    }
}
