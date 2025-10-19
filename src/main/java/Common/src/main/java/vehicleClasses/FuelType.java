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
    private static final HashMap<FuelType, String> fuelTypesNames = new HashMap<>();

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

        if (fuelTypes.isEmpty()) {
            setFuelTypes();
        }
        return fuelTypes.get(name);
    }

    /**
     * Возвращает строковое представление константы
     * @param fuelType константа
     * @return строковое представление
     */
    public static String getFuelTypeName(FuelType fuelType) {
        if (fuelTypesNames.isEmpty()) {
            fuelTypesNames.put(FuelType.ELECTRICITY, "ELECTRICITY");
            fuelTypesNames.put(FuelType.DIESEL, "DIESEL");
            fuelTypesNames.put(FuelType.ALCOHOL, "ALCOHOL");
            fuelTypesNames.put(FuelType.PLASMA, "PLASMA");
        }
        return fuelTypesNames.get(fuelType);
    }
}