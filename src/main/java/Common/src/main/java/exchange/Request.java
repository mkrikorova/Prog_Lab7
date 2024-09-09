package exchange;

import vehicleClasses.Vehicle;

import java.io.Serializable;

/**
 * Класс запроса
 */
public class Request implements Serializable {

    private final String command;

    private final String args;

    private final Vehicle vehicle;

    public Request(String command, String args, Vehicle vehicle) {
        this.command = command;
        this.args = args;
        this.vehicle = vehicle;
    }

    public String getCommand() {
        return command;
    }

    public String getArgs() {
        return args;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String toString() {
        return "exchange.Request{" +
                "command='" + command + '\'' +
                ", args=" + args +
                ", vehicle=" + vehicle +
                '}';
    }
}