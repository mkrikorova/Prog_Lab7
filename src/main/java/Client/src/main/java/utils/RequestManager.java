package utils;

import command.ExecuteScriptCommand;
import exceptions.*;
import exchange.Request;
import org.apache.commons.lang3.SerializationUtils;
import statuses.Status;
import validators.Validator;

import java.io.*;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.util.*;

public class RequestManager {
    private static int depth = 0;

    private static boolean isInFile = false;

    /**
     * Метод обрабатывающий строку и составляющий запрос из этой строки
     * @param line строка для обработки
     * @param isInFile находимся ли мы в файле. От этого зависит способ обработки
     * @return готовый запрос с верными аргументами
     * @throws ExitProgramException исключение для выхода из программы
     * @throws NoSuchElementException исключение если не существует такого элемента
     */
    public static Request lineToRequest(String line, boolean isInFile) throws ExitProgramException, NoSuchElementException {
        if (line.isEmpty()) {
            return null;
        }
        String[] wordsOfLine = line.trim().split(" ");
        String commandName = wordsOfLine[0].toLowerCase(Locale.ROOT);
        if (wordsOfLine.length > 2) {
            ColorOutput.printlnRed("Некорректное количество аргументов. Для справки введите help");
        } else if (wordsOfLine.length == 1) {
            try {
                Validator.checkIfValidCommand(commandName, Client.validators.keySet());
                Validator validator = Client.validators.get(commandName);

                if (validator.getNeedParse()) {
                    return validator.validate(commandName, null, isInFile);
                }
                return validator.validate(commandName, null);
            } catch (UnknownCommandException e) {
                ColorOutput.printlnRed("Неизвестная команда, напишите help для просмотра доступных");
            }
        } else {
            try {
                if (commandName.equals("execute_script")) {
                    handleExecuteScript(wordsOfLine[1]);
                    return null;
                } else {
                    Validator.checkIfValidCommand(commandName, Client.validators.keySet());
                    Validator validator = Client.validators.get(commandName);

                    if (validator.getNeedParse()) {
                        return validator.validate(commandName, wordsOfLine[1], isInFile);
                    }
                    return validator.validate(commandName, wordsOfLine[1]);
                }
            } catch (UnknownCommandException e) {
                ColorOutput.printlnRed("Неизвестная команда, напишите help для просмотра доступных");
            }
        }
        return null;
    }

    /**
     * Метод для обработки запроса
     * @param socketChannel сокет через который передаются данные
     * @param request запрос
     * @throws ExitProgramException исключение для выхода из программы
     */
    public static void handleRequest(SocketChannel socketChannel, Request request) throws IOException, ExitProgramException {
        if (request == null)
            return;
        if (request.getCommand().equals("execute_script")) {
            handleExecuteScript(request.getArgs());
        } else {
            makeRequest(socketChannel, request);
        }
    }

    /**
     * Метод для обработки команды execute_script
     * @param filename имя скрипта
     * @throws ExitProgramException исключение для выхода из программы
     */
    public static void handleExecuteScript(String filename) throws ExitProgramException {
        ExecuteScriptCommand.execute(filename);
    }

    /**
     * Метод отправляющий запрос и получающий ответ
     * @param socketChannel сокет через который передаются данные
     * @param request запрос
     */
    public static void makeRequest(SocketChannel socketChannel, Request request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(baos);
        toServer.writeObject(request);
        socketChannel.write(ByteBuffer.wrap(baos.toByteArray()));


        ByteBuffer fromServer = ByteBuffer.allocate(4096);
        socketChannel.read(fromServer);
        Status status = SerializationUtils.deserialize(fromServer.array());
        if (status.getResult().equals("Exception")) {
            ColorOutput.printlnRed(status.getResponse());
        } else {
            System.out.println(status.getResponse());
        }
    }


    public static void setIsInFile(boolean isInFile) {
        RequestManager.isInFile = isInFile;
    }

    public static boolean isIsInFile() {
        return isInFile;
    }
}
