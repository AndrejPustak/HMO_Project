package hr.fer.hmo.cvrptw.algorithms;

import hr.fer.hmo.cvrptw.*;

public class Greedy extends Algorithm {


    @Override
    public Solution getInitialSolution(Instance instance) {

        Solution solution = new Solution();

        Route route = new Route(instance.getCapacity(), instance.getBase());
        while(instance.getCustomers().size() != 0){

            Customer best = findBest(route, instance);

            if(best != null){
               route.addCustomer(best);
                instance.getCustomers().remove(best);
            } else {
                solution.addRoute(route);
                route = new Route(instance.getCapacity(), instance.getBase());
            }

        }

        if(route.getCustomers().size() != 0) solution.addRoute(route);
        return solution;
    }

    private Customer findBest(Route route, Instance instance) {

        Customer best = null;
        double bh = -1;


        for(int i = 0; i < instance.getCustomers().size(); i++){
            Route copyRoute = route.copy();

            if(!copyRoute.addCustomer(instance.getCustomers().get(i))) continue;
            double h = heuristic(copyRoute.timeDifference(), instance.getCustomers().get(i).getDemand());
            if(h > bh){
                bh = h;
                best = instance.getCustomers().get(i);
            }
        }

        return best;

    }

    private double heuristic(double time, int demand){

        return Math.pow(demand, 1) / Math.pow(time, 1.8);

    }
}
