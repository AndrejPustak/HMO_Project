package hr.fer.hmo.cvrptw;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private Customer base;
    private int capacity;
    private List<Customer> customers;

    private int totalDemand;
    private float totalTime;

    public Route(int capacity) {

        this.capacity = capacity;

        base = new Customer(-1, 0 , 0,0,0,0,0);
        customers = new ArrayList<>();

    }

    public int totalDemand(){
        return customers.stream().mapToInt(c->c.getDemand()).sum();
    }


    public boolean addCustomer(Customer customer){
        customers.add(customer);

        return true;
    };

    public double calculateRoute(){
        //current time
        int ct = 0;
        int demand = 0;

        Customer current = base;

        for(int i = 0; i < customers.size(); i++){
            Customer c = customers.get(i);
            ct += current.distance(c);

            if(ct > c.getDueDate()) return -1;

            if(ct < c.getReadyTime()) ct = c.getReadyTime();
            ct+= c.getServiceTime();

            demand+= c.getDemand();
            current = c;
        }

        ct += current.distance(base);

        return ct;
    }

    public boolean validRoute(){

        if(customers.size() == 0) return true;

        return true;

    }

}
