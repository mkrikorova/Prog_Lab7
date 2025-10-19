package managers;

import database.postgres.VehicleManager;
import exceptions.NoAccessException;
import models.User;
import statuses.ExceptionStatus;
import statuses.OKStatus;
import statuses.Status;
import vehicleClasses.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;

public class CollectionManager {
    ArrayDeque<Vehicle> collection;
    LocalDateTime collectionInitialization;
    VehicleStorageManager vehicleStorageManager;


    public CollectionManager() {
        this.collection = new ArrayDeque<>();
        this.vehicleStorageManager = new VehicleManager();
        collectionInitialization = LocalDateTime.now();
    }

    /**
     * Возвращает коллекцию
     * @return ArrayDeque
     */
    public ArrayDeque<Vehicle> getCollection() {
        return this.collection;
    }

    /**
     * Выводит информацию о коллекции
     */
    public String info() {
        StringBuilder result = new StringBuilder();
        result.append("Коллекция: ");
        result.append(collection.getClass().getSimpleName());
        result.append("\nТип элементов коллекции: ");
        result.append(Vehicle.class.getName());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        result.append("\nВремя инициализации коллекции: ");
        result.append(collectionInitialization.format(timeFormatter));
        result.append("\nКолличество элементов: ");
        result.append(collection.size());
        return result.toString();
    }

    /**
     * Выводит элементы коллекции
     */
    public String show() {
        StringBuilder result = new StringBuilder();
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        } else {
            for (Vehicle i: collection) {
                result.append(i.toString()).append('\n');
            }
        }
        return result.toString();
    }

    /**
     * Возвращает элемент коллекции у которого id соответствует данному индексу
     * @param index индекс, по которому ищется элемент
     * @return найденный элемент
     */
    public Vehicle getById (int index) {
        for (Vehicle element : collection) {
            if (element.getId() == index)
                return element;
        }
        return null;
    }

    /**
     * Добавляет новый элемент в коллекцию
     * @param vehicle элемент, который нужно добавить
     */
    public Status add(Vehicle vehicle) {
        if (vehicle == null) {
            return new ExceptionStatus("Объект типа Vehicle равен null");
        } else {
            if (!vehicle.validate()) {
                return new ExceptionStatus("Объект типа Vehicle не соответствует требованиям. " +
                        "Проверьте корректность введеных значений и попробуйте еще раз.");
            } else {
                try {
                    Integer vehicle_id = vehicleStorageManager.addVehicle(vehicle);
                    if (vehicle_id == null) {
                        return new ExceptionStatus("Ошибка при добавлении элемента в базу данных");
                    }
                    vehicle.setId(vehicle_id);
                    collection.add(vehicle);
                    return new OKStatus();
                } catch (Exception e) {
                    return new ExceptionStatus("Ошибка при добавлении элемента в базу данных: " + e);
                }
            }
        }
    }

    /**
     * Проверяет, существует ли элемент, id которого равно данному
     * @param index индекс, по которому ищется элемент
     * @return true если существует, иначе false
     */
    public boolean checkExistence(int index) {
        return collection.stream().anyMatch((x) -> x.getId() == index);
    }

    /**
     * Обновляет все поля элемента коллекции, если его id равен данному
     * @param index индекс, по которому ищется элемент
     * @param newVehicle элемент который нужно добавить
     */
    public void updateById(int index, Vehicle newVehicle) {
        Vehicle lastVehicle = this.getById(index);
        if (lastVehicle.getOwnerUserId() != newVehicle.getOwnerUserId()) {
            throw new NoAccessException("Нельзя изменить элемент коллекции, которым владеет другой пользователь");
        }
        vehicleStorageManager.updateVehicle(lastVehicle.getId(), newVehicle);
        collection.remove(lastVehicle);
        collection.add(newVehicle);
    }

    /**
     * Удаляет элемент, id которого равен данному
     * @param index индекс, по которому ищется элемент
     */
    public void removeById(int index, User owner) throws NoAccessException {
        var vehicle = vehicleStorageManager.getVehicle(index);
        if (vehicle.getOwnerUserId() != owner.getId()) {
            throw new NoAccessException("Нельзя удалить элемент коллекции, которым владеет другой пользователь");
        }
        collection.remove(getById(index));
        vehicleStorageManager.deleteVehicle(vehicle.getId());
    }

    /**
     * Очищает коллекцию
     */
    public void clear(User owner) {
        collection.removeIf(vehicle -> vehicle.getOwnerUserId() == owner.getId());
        vehicleStorageManager.deleteAllUserVehicles(owner.getId());
    }

}
