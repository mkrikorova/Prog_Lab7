package database.postgres;

import managers.VehicleStorageManager;
import vehicleClasses.FuelType;
import vehicleClasses.Vehicle;
import vehicleClasses.Coordinates;

import java.sql.Types;
import java.util.ArrayList;

public class VehicleManager implements VehicleStorageManager {
    @Override
    public Vehicle getVehicle(int id) {
        var select_vehicle_sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        try (var conn = Database.connect(); var pstmt = conn.prepareStatement(select_vehicle_sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                var name = rs.getString(2);
                var x = rs.getInt(3);
                var y = rs.getDouble(4);
                var creation_date = rs.getTimestamp(5);
                var engine_power = rs.getDouble(6);
                var number_of_wheels = rs.getInt(7);
                var fuel_consumption = rs.getDouble(8);
                var fuel_type = rs.getString(9);
                var owner_user_id = rs.getInt(10);
                return new Vehicle(id, name, new Coordinates(x, y), creation_date.toLocalDateTime(), engine_power, number_of_wheels, fuel_consumption, fuel_type, owner_user_id);
            }
            throw new RuntimeException("Cannot find vehicle");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer addVehicle(Vehicle vehicle) {
        var insert_vehicle_sql = "INSERT INTO vehicles(name, x_coordinate, y_coordinate, engine_power, number_of_wheels, fuel_consumption, fuel_type, owner_user_id) VALUES(?, ?, ?, ?, ?, ?, ?::fuel_types, ?) RETURNING vehicle_id";
        try (var conn = Database.connect(); var pstmt = conn.prepareStatement(insert_vehicle_sql)) {
            pstmt.setString(1, vehicle.getName());
            pstmt.setInt(2, vehicle.getCoordinates().getX());
            pstmt.setDouble(3, vehicle.getCoordinates().getY());
            pstmt.setDouble(4, vehicle.getEnginePower());
            pstmt.setInt(5, vehicle.getNumberOfWheels());
            pstmt.setDouble(6, vehicle.getFuelConsumption());
            pstmt.setString(7, FuelType.getFuelTypeName(vehicle.getFuelType()));
            pstmt.setInt(8, vehicle.getOwnerUserId());
            var result = pstmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            var t = result.getInt(1);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateVehicle(int id, Vehicle vehicle) {
        var updateVehicleSQL = "UPDATE vehicles SET name = ?, x_coordinate = ?, y_coordinate = ?, engine_power = ?, number_of_wheels = ?, fuel_consumption = ?, fuel_type = ?::fuel_types, owner_user_id = ? WHERE vehicle_id = ?";
        try (var conn = Database.connect(); var pstmt = conn.prepareStatement(updateVehicleSQL)) {
            pstmt.setString(1, vehicle.getName());
            pstmt.setInt(2, vehicle.getCoordinates().getX());
            pstmt.setDouble(3, vehicle.getCoordinates().getY());
            pstmt.setDouble(4, vehicle.getEnginePower());
            pstmt.setInt(5, vehicle.getNumberOfWheels());
            pstmt.setDouble(6, vehicle.getFuelConsumption());
            pstmt.setString(7, FuelType.getFuelTypeName(vehicle.getFuelType()));
            pstmt.setInt(8, vehicle.getOwnerUserId());
            pstmt.setInt(9, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteVehicle(int id) {
        var delete_vehicle_sql = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (var conn = Database.connect(); var pstmt = conn.prepareStatement(delete_vehicle_sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Vehicle> getAllVehicles() {
        var result = new ArrayList<Vehicle>();
        try (var conn = Database.connect(); var stmt = conn.createStatement()) {
            var rs = stmt.executeQuery("SELECT vehicle_id, name, x_coordinate, y_coordinate, creation_date, engine_power, number_of_wheels, fuel_consumption, fuel_type::text, owner_user_id FROM vehicles");
            while (rs.next()) {
                var id = rs.getInt(1);
                var name = rs.getString(2);
                var x = rs.getInt(3);
                var y = rs.getDouble(4);
                var creationDate = rs.getTimestamp(5);
                var enginePower = rs.getDouble(6);
                var numberOfWheels = rs.getInt(7);
                var fuelConsumption = rs.getDouble(8);
                var fuelType = rs.getString(9);
                var ownerUserId = rs.getInt(10);
                result.add(new Vehicle(id, name, new Coordinates(x, y), creationDate.toLocalDateTime(), enginePower, numberOfWheels, fuelConsumption, fuelType, ownerUserId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void deleteAllUserVehicles(int ownerUserId) {
        var query = "DELETE FROM vehicles WHERE owner_user_id = ?";
        try (var conn = Database.connect(); var pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ownerUserId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
