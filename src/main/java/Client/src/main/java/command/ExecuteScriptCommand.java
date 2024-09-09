package command;

import exceptions.*;
import exchange.Request;
import utils.*;
import validators.AddAndRemoveLowerValidator;

import java.io.*;
import java.util.*;

/**Класс команды execute_script script*/
public class ExecuteScriptCommand {
    private static BufferedReader reader = null;
    private static String lastReadFromFile = null;

    private static final Stack<String> files = new Stack<>();

    /**
     * Исполняет команду execute_script
     * @param filePath путь до файла
     */
    public static void execute(String filePath) throws ExitProgramException {
        try {
            File ioFile = new File(filePath);
            if (!ioFile.canWrite() || ioFile.isDirectory() || !ioFile.isFile())
                throw new IOException();
            if (files.contains(filePath))
                throw new RecursiveCallException();
            files.add(filePath);

            reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine(); //начинаем считывать файл
            while (line != null) {
                ColorOutput.printlnCyan("\nИсполнение команды " + line + " : ");
                Request request = RequestManager.lineToRequest(line, true);
                RequestManager.handleRequest(Client.getSocketChannel(), request);
                while (lastReadFromFile != null) {
                    String s = new String(lastReadFromFile);
                    lastReadFromFile = null;
                    ColorOutput.printlnCyan("\nИсполнение команды " + s + " : ");
                    request = RequestManager.lineToRequest(s, true);
                    RequestManager.handleRequest(Client.getSocketChannel(), request);
                }
                line = reader.readLine();

            }
            files.remove(filePath);
            if (files.isEmpty())
                reader.close();
        } catch (IOException ex) {
            ColorOutput.printlnRed("Доступ к файлу невозможен "+ ex.getMessage());
        } catch (RecursiveCallException r) {
            ColorOutput.printlnRed("Скрипт " + filePath + " уже был вызван (Рекурсивный вызов)");
        }
    }

    /**
     * Метод считывающий значения полей для типа Vehicle
     * @return массив полей
     */
    public static ArrayList<String> readAllArguments() {
        ArrayList<String> allArgs = new ArrayList<>();
        try {
            String line; //начинаем считывать файл
            Set<String> commands = Client.validators.keySet();
            line = reader.readLine();
            while (line != null && !commands.contains(line)) {
                allArgs.add(line);
                line = reader.readLine();
            }
            lastReadFromFile = line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allArgs;
    }
}
