package utils;

import exceptions.*;
import exchange.Request;
import validators.*;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.*;
import vehicleClasses.FuelType;

public class Client {

    public static Map<String, Validator> validators;
    private static SocketChannel socketChannel;
    private final int port = System.getenv("PORT") != null ?
            Integer.parseInt(System.getenv("PORT")) : 8080;

    private String login;
    private String password;
    private Integer userId;


    public Client() {
        FuelType.setFuelTypes();
        validators = new HashMap<>() {
            {
                put("add", new AddAndRemoveLowerValidator());
                put("add_if_min", new AddAndRemoveLowerValidator());
                put("average_of_number_of_wheels", new NoArgumentsValidator());
                put("clear", new NoArgumentsValidator());
                put("execute_script", new ExecuteScriptValidator());
                put("filter_starts_with_name", new OneStringArgValidator());
                put("help", new NoArgumentsValidator());
                put("info", new NoArgumentsValidator());
                put("remove_any_by_fuel_type", new OneStringArgValidator());
                put("remove_by_id", new OneIntArgValidator());
                put("remove_head", new NoArgumentsValidator());
                put("remove_lower", new AddAndRemoveLowerValidator());
                put("show", new NoArgumentsValidator());
                put("update", new UpdateByIdValidator());
            }
        };
    }

    private Integer processAuthorizationDialog(Scanner scanner) throws IOException, ClassNotFoundException {
        String line;

        ColorOutput.printlnCyan("Для авторизации введите 1, для регистрации введите 2:");
        ColorOutput.printCyan(">> ");
        line = scanner.nextLine();
        while (!line.equals("1") && !line.equals("2")) {
            ColorOutput.printlnRed("Некорректный ввод. Попробуйте еще раз.");
            ColorOutput.printCyan(">> ");
            line = scanner.nextLine();
        }

        ColorOutput.printlnPurple("\nВведите логин пользователя:");
        ColorOutput.printPurple(">> ");
        this.login = scanner.nextLine();
        ColorOutput.printlnPurple("Введите пароль пользователя:");
        ColorOutput.printPurple(">> ");
        this.password = scanner.nextLine();

        if (line.equals("1")) {
            var req = new Request("login",  login + "|" + password, null);
            var resp = RequestManager.getResponseStatus(socketChannel, req);
            if (resp.getResult().equals("Exception")) {
                ColorOutput.printlnRed("Ошибка авторизации: " + resp.getResponse());
                return null;
            }
            ColorOutput.printlnGreen("Авторизация прошла успешно. Ваш id: " + resp.getResponse());
            return Integer.parseInt(resp.getResponse());
        }

        var req = new Request("register",  login + "|" + password, null);
        var resp = RequestManager.getResponseStatus(socketChannel, req);
        if (resp.getResult().equals("Exception")) {
            ColorOutput.printlnRed("Ошибка при регистрации: " + resp.getResponse());
            return null;
        }
        ColorOutput.printlnGreen("Регистрация прошла успешно. Ваш id: " + resp.getResponse());
        return Integer.parseInt(resp.getResponse());
    }

    public void start () throws ExitProgramException, IOException { //тут ввод с консоли
        try {
            openSocket();
            socketChannel.connect(new InetSocketAddress("localhost", port));

            Scanner scanner = new Scanner(System.in);
            String line;

            userId = processAuthorizationDialog(scanner);
            closeSocket();
            openSocket();
            socketChannel.connect(new InetSocketAddress("localhost", port));

            if (userId == null) {
                closeSocket();
                return;
            }

            ColorOutput.printlnCyan("\nДанное консольное приложение реализует управление коллекцией объектов типа " +
                    "Vehicle.\nДля справки о командах введите help.");

            while (true) {
                try {
                    System.out.println("\nВведите название команды:");
                    System.out.print(">> ");
                    line = scanner.nextLine();
                    line = line.replaceAll("\\s{2,}", " ");
                    Request request = RequestManager.lineToRequest(line, false, userId);
                    if (request == null) {
                        continue;
                    }
                    request.setLogin(login);
                    request.setPassword(password);
                    RequestManager.handleRequest(socketChannel, request, userId);
                    closeSocket();
                    openSocket();
                    socketChannel.connect(new InetSocketAddress("localhost", port));
                } catch (NoSuchElementException e) {
                    closeSocket();
                    ColorOutput.printlnCyan("Достигнут конец ввода, завершение работы программы...");
                    throw new ExitProgramException();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            closeSocket();
        }
    }

    public void openSocket() throws IOException {
        socketChannel = SocketChannel.open();
    }

    public void closeSocket() throws IOException {
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
    }

    public static SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
