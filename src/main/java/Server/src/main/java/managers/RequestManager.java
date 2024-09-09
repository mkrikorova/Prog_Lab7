package managers;

import exceptions.ExitProgramException;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;

public class RequestManager {

    /**
     * Метод получающий и исполняющий запрос
     * @throws ExitProgramException исключение для выхода из программы
     */
    public static void receiveAndExecuteRequest() throws IOException, ClassNotFoundException, ExitProgramException {
        int readyChannels = ConnectionManager.getSelector().selectNow();
        if (readyChannels == 0) {
            ServerConsoleInputManager.checkConsoleInput();
            return;
        }

        Set<SelectionKey> selectedKeys = ConnectionManager.getSelector().selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
        ServerConsoleInputManager.checkConsoleInput();

        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            keyIterator.remove();
            ServerConsoleInputManager.checkConsoleInput();
            try {
                if (key.isAcceptable()) {
                    ConnectionManager.acceptConnection(key);
                } else if (key.isReadable()) {
                    ConnectionManager.read(key);
                } else if (key.isWritable()) {
                    ConnectionManager.write(key);
                }
            } catch (IOException e) {
                key.cancel();
            }
        }
    }
}
