import exceptions.*;
import managers.*;

import java.io.*;

public class Server {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    /**
     * Конструктор приложения
     */
    public Server() {
        this.collectionManager = new CollectionManager();
        this.fileManager = new FileManager(collectionManager);
        ConnectionManager.setCollectionManager(collectionManager);
    }


    public void run() throws IOException, ClassNotFoundException, ExitProgramException {
        fileManager.findFile();
        fileManager.createObjects();

        ConnectionManager.connect();

        while (true) {
            ServerConsoleInputManager.checkConsoleInput();
            RequestManager.receiveAndExecuteRequest();
        }
    }
}
