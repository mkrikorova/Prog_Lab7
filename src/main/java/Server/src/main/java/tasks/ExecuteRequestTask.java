package tasks;

import exceptions.ExitProgramException;
import exchange.Request;
import managers.CommandManager;
import managers.ConnectionManager;
import statuses.Status;

import java.nio.channels.SocketChannel;

public class ExecuteRequestTask implements Runnable{
    private final Request request;
    private final SocketChannel client;

    public ExecuteRequestTask(Request request, SocketChannel client) {
        this.request = request;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            CommandManager commandManager = new CommandManager(ConnectionManager.getCollectionManager());
            Status status = commandManager.execute(request.getCommand(), request.getArgs(),
                    request.getVehicle(), request.getLogin(), request.getPassword()); //исполнили команду и получили ответ

            //многопоточка 3
            WriteResponseTask task = new WriteResponseTask(status, client);
            Thread thread = new Thread(task);
            thread.start();
        } catch (ExitProgramException exit) {
            throw new RuntimeException(exit.getMessage());
        }
    }
}
