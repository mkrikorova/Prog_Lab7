package utils;

import exceptions.ExitProgramException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.start();
        } catch (ExitProgramException exit) {
            ColorOutput.printlnCyan("Спасибо что использовали меня. До скорой встречи! (\u2060≧\u2060▽\u2060≦\u2060)");
        } catch (IOException e) {
            ColorOutput.printlnRed("Что-то пошло не так: " + e.getMessage() + ". Завершение работы...");
            ColorOutput.printlnCyan("Спасибо что использовали меня. До скорой встречи! (\u2060≧\u2060▽\u2060≦\u2060)");
        }
    }
}