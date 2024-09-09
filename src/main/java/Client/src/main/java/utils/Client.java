package utils;

import exceptions.*;
import exchange.Request;
import validators.*;

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

    public void start () throws ExitProgramException, IOException { //тут ввод с консоли
        try {
            openSocket();
            socketChannel.connect(new InetSocketAddress("localhost", port));

            ColorOutput.printlnCyan("\nДанное консольное приложение реализует управление коллекцией объектов типа " +
                    "Vehicle.\nДля справки о командах введите help.");
            Scanner scanner = new Scanner(System.in);
            String line;
            while (true) {
                try {
                    System.out.println("\nВведите название команды:");
                    System.out.print(">> ");
                    line = scanner.nextLine();
                    line = line.replaceAll("\\s{2,}", " ");
                    Request request = RequestManager.lineToRequest(line, false);

                    RequestManager.handleRequest(socketChannel, request);
                } catch (NoSuchElementException e) {
                    closeSocket();
                    ColorOutput.printlnCyan("Достигнут конец ввода, завершение работы программы...");
                    throw new ExitProgramException();
                }
            }
        } catch (IOException e) {
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
