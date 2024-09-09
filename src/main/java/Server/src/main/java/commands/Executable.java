package commands;

public interface Executable {
    /**
     * Метод для получения описания команды
     * @return строку -- описание команды
     */
    String getDescription();

    /**
     * Метод для получения имени команды (так, как она должна вводиться)
     * @return строу -- имя команды
     */
    String getName();

    /**
     * Метод, определяющий, корректен ли аргумент команды
     * @param length длина аргумента (его наличие)
     * @return корректно ли наличие/отсутствие аргумента
     */
    boolean hasAnArgument(int length);
}
