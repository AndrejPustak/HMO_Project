package hr.fer.hmo.cvrptw;

import java.util.Objects;

public class Customer {

    private int id;
    private int xCoord;
    private int yCoord;
    private int demand;
    private int readyTime;
    private int dueDate;
    private int serviceTime;

    public Customer(int id, int xCoord, int yCoord, int demand, int readyTime, int dueDate, int serviceTime) {
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.demand = demand;
        this.readyTime = readyTime;
        this.dueDate = dueDate;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getDemand() {
        return demand;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public int getDueDate() {
        return dueDate;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
