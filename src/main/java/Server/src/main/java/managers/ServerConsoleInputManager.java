package managers;

import exceptions.*;
import utils.*;

import java.io.*;
import java.util.*;

public class ServerConsoleInputManager {
    public static void checkConsoleInput() throws ExitProgramException{
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            if (reader.ready()) {
                String arg = reader.readLine();
                if (arg.equals("exit")) {
                    throw new ExitProgramException();
                } else {
                    ColorOutput.printlnRed("Такой команды не существует.");
                }
            }
        } catch (NoSuchElementException e) {
            ColorOutput.printlnCyan("Достигнут конец ввода, завершение работы программы...");
            throw new ExitProgramException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
