package utils;


/**Класс для раскрашивания вывода на консоль*/
public class ColorOutput {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";


    /**
     * Метод выводит строку s и переводит каретку на следующую строку
     * @param s строка
     */
    public static void println(String s) {
        System.out.println(s);
    }

    /**
     * Метод выводит строку s зеленым цветом и переводит каретку на следующую строку
     * @param s строка
     */
    public static void printlnGreen(String s) {
        System.out.println(ANSI_GREEN + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s красным цветом и переводит каретку на следующую строку
     * @param s строка
     */
    public static void printlnRed(String s) {
        System.out.println(ANSI_RED + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s синим цветом и переводит каретку на следующую строку
     * @param s строка
     */

    public static void printlnBlue(String s) {
        System.out.println(ANSI_BLUE + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s голубым цветом и переводит каретку на следующую строку
     * @param s строка
     */
    public static void printlnCyan(String s) {
        System.out.println(ANSI_CYAN + s + ANSI_RESET);
    }
    /**
     * Метод выводит строку s желтым цветом и переводит каретку на следующую строку
     * @param s строка
     */
    public static void printlnYellow(String s) {
        System.out.println(ANSI_YELLOW + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s фиолетовым цветом и переводит каретку на следующую строку
     * @param s строка
     */
    public static void printlnPurple(String s) {
        System.out.println(ANSI_PURPLE + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s зеленым цветом
     * @param s строка
     */
    public static void printGreen(String s) {
        System.out.print(ANSI_GREEN + s + ANSI_RESET);
    }
    /**
     * Метод выводит строку s красным цветом
     * @param s строка
     */
    public static void printRed(String s) {
        System.out.print(ANSI_RED + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s синим цветом
     * @param s строка
     */
    public static void printBlue(String s) {
        System.out.print(ANSI_BLUE + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s голубым цветом
     * @param s строка
     */
    public static void printCyan(String s) {
        System.out.print(ANSI_CYAN + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s желтым цветом
     * @param s строка
     */
    public static void printYellow(String s) {
        System.out.print(ANSI_YELLOW + s + ANSI_RESET);
    }

    /**
     * Метод выводит строку s фиолетовым цветом
     * @param s строка
     */
    public static void printPurple(String s) {
        System.out.print(ANSI_PURPLE + s + ANSI_RESET);
    }
}
