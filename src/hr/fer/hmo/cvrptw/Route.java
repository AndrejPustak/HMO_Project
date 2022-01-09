package hr.fer.hmo.cvrptw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Route {

    private Customer base;
    private int capacity;
    private List<Customer> customers;

    private int totalDemand;
    private float totalTime;
    private float lastCustomerTime;
    private float timeDifference;

    public Route(int capacity) {

        this.capacity = capacity;

        base = new Customer(-1, 0 , 0,0,0,0,0);
        customers = new ArrayList<>();

    }

    public Route(int capacity, List<Customer> customers) {

        this.capacity = capacity;
        this.customers = customers;

        base = new Customer(-1, 0 , 0,0,0,0,0);

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

        totalDemand = demand;
        totalTime = ct;

        return true;
    }

    public float timeDifference(){
        return timeDifference;
    }

    public Route copy(){

        return new Route(capacity, customers.stream().map(Customer::copy).collect(Collectors.toList()));

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getBase() {
        return base;
    }
}
