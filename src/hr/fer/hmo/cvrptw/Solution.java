package hr.fer.hmo.cvrptw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution implements Comparable<Solution>{

    private List<Route> routes;

    public Solution() {

        routes = new ArrayList<>();

    }

    public Solution(List<Route> routes) {
        this.routes = routes;
    }

    public void addRoute(Route route){
        routes.add(route);
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Solution copy(){

        return new Solution(routes.stream().map(r->r.copy()).collect(Collectors.toList()));

    }

    public int totalVehicles(){
        return routes.size();
    }

    public double totalTime(){
        return routes.stream().mapToDouble(r-> r.getTotalTime()).sum();
    }


    @Override
    public int compareTo(Solution solution) {

        if(totalVehicles() < solution.totalVehicles()) return 1;
        else if(totalVehicles() > solution.totalVehicles()) return -1;
        else{
            if(totalTime() < solution.totalTime()) return 1;
            else if(totalTime() > solution.totalTime()) return -1;
            else return 0;
        }

    }
}
