package managers;


import exceptions.ExitProgramException;
import exchange.Request;
import org.apache.commons.lang3.SerializationUtils;
import statuses.Status;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ConnectionManager {
    private static ServerSocketChannel serverSocket;
    private static Selector selector;
    private static int port;
    private static CollectionManager collectionManager;

    /**
     * Метод для установки подключения
     */
    public static void connect() throws IOException {
        port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);

        selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * Подтверждает подключение и переводит в режим чтения
     * @param key ключ канала в селекторе
     */
    public static void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverChannel.accept();
        client.configureBlocking(false);
        client.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(4096));
    }


    /**
     * Считывает данные из сокета и переводит в режим записи
     * @param key ключ канала в селекторе
     * @throws ExitProgramException исключение для выхода из программы
     */
    public static void read(SelectionKey key) throws IOException, ClassNotFoundException, ExitProgramException {
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false); // неблокирующий режим

        ByteBuffer fromClientBuffer = (ByteBuffer) key.attachment();
        client.read(fromClientBuffer);
        try {
            ObjectInputStream fromClient = new ObjectInputStream(new ByteArrayInputStream(fromClientBuffer.array()));

            Request request = (Request) fromClient.readObject();
            fromClientBuffer.clear();
            System.out.println(request);

            CommandManager commandManager = new CommandManager(getCollectionManager());
            Status status = commandManager.execute(request.getCommand(), request.getArgs(),
                    request.getVehicle()); //исполнили команду и получили ответ

            byte[] response = SerializationUtils.serialize(status); //сериализуем его
            ByteBuffer responseBuffer = ByteBuffer.wrap(response);
            client.register(key.selector(), SelectionKey.OP_WRITE, responseBuffer);
        } catch (IOException e) {
            if (e.getMessage().equals("Connection reset")) {
                key.cancel();
            }
        }
    }

    /**
     * Отправляет данные клиенту и переводит в режим чтения
     * @param key ключ канала в селекторе
     */
    public static void write(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false); // неблокирующий режим
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        client.write(buffer);
        client.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(4096));
    }

    public static Selector getSelector() {
        return selector;
    }

    public static ServerSocketChannel getServerSocket() {
        return serverSocket;
    }

    public static void setCollectionManager(CollectionManager collectionManager) {
        ConnectionManager.collectionManager = collectionManager;
    }

    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
