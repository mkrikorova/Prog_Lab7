import database.postgres.VehicleManager;
import exceptions.*;
import managers.*;
import tasks.ReadRequestTask;
import utils.ColorOutput;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final CollectionManager collectionManager;
    private final VehicleStorageManager vehicleStorageManager;

    /**
     * Конструктор приложения
     */
    public Server() {
        this.collectionManager = new CollectionManager();
        this.vehicleStorageManager = new VehicleManager();
        ConnectionManager.setCollectionManager(collectionManager);
    }


    public void run() throws IOException, ExitProgramException {
        for (var v : vehicleStorageManager.getAllVehicles()) {
            collectionManager.getCollection().add(v);
        }
        ColorOutput.printlnCyan("Подключение к базе данных выполнено успешно!");

        ExecutorService executor1 = Executors.newCachedThreadPool();
        ExecutorService executor2 = Executors.newFixedThreadPool(8);
        SocketChannel socketChannel;
        try {
            ConnectionManager.connect();
            while (true) {
                ServerConsoleInputManager.checkConsoleInput();

                socketChannel = ConnectionManager.getClientSocket();
                if (socketChannel == null) {
                    continue;
                }
                handlerSocketChannel(socketChannel, executor1, executor2);
            }
        } catch (IOException e) {
            ColorOutput.printlnRed(e.toString());
        } catch (RuntimeException e) {
            ColorOutput.printlnRed(e.toString());
        }
    }

    public void handlerSocketChannel(SocketChannel socketChannel, ExecutorService executor1, ExecutorService executor2) throws IOException {
        //многопоточка 1
        ReadRequestTask task = new ReadRequestTask(socketChannel, executor2);
        executor1.submit(task);
    }
}

// /Users/m.krikorova/IdeaProjects/Lab7