package hr.fer.hmo.cvrptw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Route {

    private Customer base;
    private int capacity;
    private List<Customer> customers;

    private int totalDemand;
    private double totalTime;
    private double lastCustomerTime;
    private double timeDifference;

    public Route(int capacity, Customer base) {

        this.capacity = capacity;

        this.base = base;
        customers = new ArrayList<>();

    }

    public Route(int capacity,Customer base, List<Customer> customers) {

        this.capacity = capacity;
        this.base = base;
        this.customers = customers;

        calculateRoute();

    }

    public int totalDemand(){
        return customers.stream().mapToInt(c->c.getDemand()).sum();
    }


    public boolean addCustomer(Customer customer){
        customers.add(customer);

        if(!calculateRoute()) {
            customers.remove(customer);
            return false;
        }

        return true;
    };

    public boolean insertCustomer(Customer customer, int index){
        customers.add(index, customer);

        if(!calculateRoute()) {
            customers.remove(customer);
            return false;
        }

        return true;
    };

    public boolean calculateRoute(){

        if(customers.size() == 0) return true;

        //current time
        int ct = 0;
        int demand = 0;

        Customer current = base;

        int i;
        for(i = 0; i < customers.size(); i++){
            Customer c = customers.get(i);
            ct += current.distance(c);

            if(ct > c.getDueDate()) return false;

            if(ct < c.getReadyTime()) ct = c.getReadyTime();
            ct+= c.getServiceTime();

            demand+= c.getDemand();
            if(demand > capacity) return false;
            current = c;
        }

        timeDifference = ct - customers.get(i-1).getServiceTime() - lastCustomerTime;
        lastCustomerTime = ct;

        ct += current.distance(base);

        if(ct > base.getDueDate()) return false;

        totalDemand = demand;
        totalTime = ct;

        return true;
    }

    public Customer removeCustomer(int index){
        Customer c = customers.remove(index);
        calculateRoute();

        return c;
    }

    public double timeDifference(){
        return timeDifference;
    }

    public Route copy(){

        return new Route(capacity,base.copy(), customers.stream().map(Customer::copy).collect(Collectors.toList()));

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getBase() {
        return base;
    }

    public double getTotalTime(){
        return totalTime;
    }
}
