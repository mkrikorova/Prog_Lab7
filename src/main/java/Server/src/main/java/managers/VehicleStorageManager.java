package managers;

import vehicleClasses.Vehicle;

import java.util.ArrayList;

public interface VehicleStorageManager {
    Vehicle getVehicle(int id);
    Integer addVehicle(Vehicle vehicle);
    void updateVehicle(int id, Vehicle vehicle);
    void deleteVehicle(int id);
    void deleteAllUserVehicles(int ownerUserId);
    ArrayList<Vehicle> getAllVehicles();
}
