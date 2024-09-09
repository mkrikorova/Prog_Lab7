package vehicleClasses;

import java.io.Serializable;
import java.util.HashMap;

/**Класс констант для типа топлива*/
public enum FuelType implements Serializable {
    /**Электричество*/
    ELECTRICITY,
    /**Дизельное топливо*/
    DIESEL,
    /**Бензин*/
    ALCOHOL,
    /**Плазма*/
    PLASMA;

    private static final HashMap<String, FuelType> fuelTypes = new HashMap<>();

    /**
     * Устанавливает константы так, чтобы было удобно вводить
     */
    public static void setFuelTypes() {
        fuelTypes.put("electricity", FuelType.ELECTRICITY);
        fuelTypes.put("diesel", FuelType.DIESEL);
        fuelTypes.put("plasma", FuelType.PLASMA);
        fuelTypes.put("alcohol", FuelType.ALCOHOL);
        fuelTypes.put("1", FuelType.ELECTRICITY);
        fuelTypes.put("2", FuelType.DIESEL);
        fuelTypes.put("3", FuelType.ALCOHOL);
        fuelTypes.put("4", FuelType.PLASMA);
    }

    /**
     * Возвращает константу типа FuelType по имени
     * @param name имя
     * @return константу
     */
    public static FuelType getFuelType(String name) {
        return fuelTypes.get(name);
    }
}