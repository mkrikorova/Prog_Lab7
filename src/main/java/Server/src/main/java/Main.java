import exceptions.ExitProgramException;
import managers.ConnectionManager;
import utils.ColorOutput;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            Server server = new Server();
            server.run();
        } catch (ExitProgramException exit) {
            ConnectionManager.getServerSocket().close();
            ColorOutput.printlnCyan("Спасибо что использовали меня. До скорой встречи! (\u2060≧\u2060▽\u2060≦\u2060)");
        } catch (IOException | IllegalArgumentException e) {
            ConnectionManager.getServerSocket().close();
            ColorOutput.printlnRed("Что-то пошло не так: " + e.getMessage() + ".\nЗавершение работы...");
            ColorOutput.printlnCyan("Спасибо что использовали меня. До скорой встречи! (\u2060≧\u2060▽\u2060≦\u2060)");
        }
    }
}