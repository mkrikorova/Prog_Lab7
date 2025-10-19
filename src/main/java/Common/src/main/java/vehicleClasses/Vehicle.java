package vehicleClasses;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Objects;

/**Класс транспорта*/
public class Vehicle implements Validator, Comparable<Vehicle>, Serializable {
    public static int uniqueId;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double enginePower; //Значение поля должно быть больше 0
    private int numberOfWheels; //Значение поля должно быть больше 0
    private Double fuelConsumption; //Поле не может быть null, Значение поля должно быть больше 0
    private FuelType fuelType; //Поле может быть null
    private int ownerUserId;


    /**
     * Конструктор класса без id и creationDate
     * @param name имя
     * @param coordinates координаты
     * @param enginePower мощность двигателя
     * @param numberOfWheels количество колес
     * @param fuelConsumption потребление топлива
     * @param nameOfFuelType имя одной из констант типа топлива
     * @param ownerUserId id пользователя-владельца
     */
    public Vehicle(String name, Coordinates coordinates,
                   double enginePower, int numberOfWheels, Double fuelConsumption, String nameOfFuelType, int ownerUserId) {
        this.id = uniqueId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.fuelConsumption = fuelConsumption;
        if (nameOfFuelType == null)
            this.fuelType = null;
        else
            this.fuelType = FuelType.getFuelType(nameOfFuelType.toLowerCase());
        this.ownerUserId = ownerUserId;
    }

    /**
     * Конструктор класса с id и creationDate
     * @param id айди
     * @param name имя
     * @param coordinates координаты
     * @param creationDate дата создания
     * @param enginePower мощность двигателя
     * @param numberOfWheels количество колес
     * @param fuelConsumption потребление топлива
     * @param nameOfFuelType имя одной из констант типа топлива
     * @param ownerUserId id пользователя-владельца
     */
    public Vehicle(int id, String name, Coordinates coordinates, LocalDateTime creationDate,
                          double enginePower, int numberOfWheels, Double fuelConsumption, String nameOfFuelType, int ownerUserId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.fuelConsumption = fuelConsumption;
        if (nameOfFuelType == null)
            this.fuelType = null;
        else
            this.fuelType = FuelType.getFuelType(nameOfFuelType.toLowerCase());
        this.ownerUserId = ownerUserId;
    }

    /**
     * Возвращает id объекта
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Устанавливает id равное данному
     * @param newId новое id
     */
    public void setId(int newId) {
        this.id = newId;
    }

    /**
     * Обновляет минимальный не использованный id
     * @param collection коллекция, для которой происходит обновление уникального id
     */
    public static void updateUniqueId (ArrayDeque<Vehicle> collection) {
        if (collection == null)
            uniqueId = 1;
        else
            uniqueId = collection.stream().filter(Objects::nonNull)
                    .map(Vehicle::getId)
                    .mapToInt(Integer::intValue)
                    .max().orElse(0) + 1;
    }

    /**
     * Возвращает имя объекта
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Устанавливает новое имя для объекта
     * @param newName новое имя
     */
    public void setName(String newName) {
        if (!newName.isEmpty()){
            this.name = newName;
        }
    }

    /**
     * Возвращает координаты объекта
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Устанавливает координаты для объекта
     * @param newCoordinates новые координаты
     */
    public void setCoordinates(Coordinates newCoordinates) {
        this.coordinates = newCoordinates;
    }

    /**
     * Возвращает дату создания объекта
     * @return дату
     */
    public String getCreationDate() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.creationDate.format(timeFormatter);
    }

    /**
     * Возвращает мощность двигателя объекта
     * @return мощность двигателя
     */
    public double getEnginePower() {
        return enginePower;
    }

    /**
     * Устанавливает мощность двигателя для объекта
     * @param enginePower новая мощность двигателя
     */
    public void setEnginePower(double enginePower) {
        if (enginePower != 0)
            this.enginePower = enginePower;
    }

    /**
     * Возвращает количество колес объекта
     * @return количество колес
     */
    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    /**
     * Устанавливает количество колес для объекта
     * @param numberOfWheels новое количество колес
     */
    public void setNumberOfWheels(int numberOfWheels) {
        if (numberOfWheels != 0)
            this.numberOfWheels = numberOfWheels;
    }


    /**
     * Возвращает потребление топлива объекта
     * @return потребление топлива
     */
    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * Устанавливает значение потребление топлива для объекта
     * @param fuelConsumption новое потребление топлива
     */
    public void setFuelConsumption(Double fuelConsumption) {
        if (fuelConsumption > 0)
            this.fuelConsumption = fuelConsumption;
    }

    /**
     * Возвращает тип топлива объекта
     * @return тип топлива
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Устанавливает тип топлива для объекта
     * @param fuelType новый тип топлива
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Возвращает id пользователя-владельца объекта
     * @return id пользователя-владельца
     */
    public int getOwnerUserId() {
        return this.ownerUserId;
    }

    /**
     * Устанавливает id пользователя-владельца для объекта
     * @param ownerUserId новый id пользователя-владельца
     */
    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * Возвращает объект типа Vehicle в виде строки
     * @return описание объекта
     */
    @Override
    public String toString() {
        DateTimeFormatter europeanDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String newDateFormat = creationDate.format(europeanDateFormat);
        return "Vehicle {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", coordinates = " + coordinates +
                ", creationDate = " + newDateFormat +
                ", enginePower = " + enginePower +
                ", numberOfWheels = " + numberOfWheels +
                ", fuelConsumption = " + fuelConsumption +
                ", fuelType = " + fuelType +
                ", ownerUserId = " + ownerUserId +
                '}';
    }

    /**
     * Метод валидирующие поля по условию
     * @return true если поля валидные, false иначе
     */
    @Override
    public boolean validate() {
        if (this.name == null || this.name.isEmpty()) return false;
        if (this.coordinates == null) return false;
        if (this.enginePower <= 0) return false;
        if (this.numberOfWheels <= 0) return false;
        if (this.fuelConsumption == null || this.fuelConsumption <= 0) return false;
        return true;
    }


    /**
     * Сравнивает два объекта на основе разницы мощности двигателя и потребления топлива
     * @param that объект для сравнения
     */
    @Override
    public int compareTo(Vehicle that) {
        if (Objects.isNull(that)) return 1;
        int thisCoolness = (int) ((this.enginePower - this.fuelConsumption));
        int thatCoolness = (int) ((that.enginePower - that.fuelConsumption));

        return Integer.compare(thisCoolness, thatCoolness);
    }

    /**
     * Проверяет эквивалентность двух объектов
     * @param o объект для сравнения
     * @return true если эквиваленты, false иначе
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle that = (Vehicle) o;
        if (!this.name.equals(that.getName())) return false;
        if (this.enginePower != that.getEnginePower()) return false;
        if (!this.fuelConsumption.equals(that.getFuelConsumption())) return false;
        if (this.numberOfWheels != that.getNumberOfWheels()) return false;
        if (this.fuelType != that.getFuelType()) return false;
        return true;
    }

    /**
     * Считает хэш-код для объекта
     * @return хэш-код
     */
    @Override
    public int hashCode() {
        int result = id * 2203909; //дата рождения Уильяма

        result = result * 22 + name.hashCode();
        result = result * 22 + coordinates.hashCode();
        result = result * 22 + creationDate.hashCode();
        result = (int) (result * 22 + enginePower * 2203909);
        result = result * 22 + numberOfWheels * 2203909;
        result = result * 22 + fuelConsumption.hashCode();
        result = result * 22 + fuelType.hashCode();
        return result;
    }
}
