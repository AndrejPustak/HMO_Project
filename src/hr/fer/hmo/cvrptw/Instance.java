package hr.fer.hmo.cvrptw;

import java.util.List;

public class Instance {

    private int vehicleNumber;
    private int capacity;

    private List<Customer> customers;

    public Instance(int vehicleNumber, int capacity, List<Customer> customers) {
        this.vehicleNumber = vehicleNumber;
        this.capacity = capacity;
        this.customers = customers;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
