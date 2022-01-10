package hr.fer.hmo.cvrptw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution implements Comparable<Solution>{

    private List<Route> routes;

    private int lastChangedId;

    public Solution() {

        routes = new ArrayList<>();
        lastChangedId =-1;

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

    public int getLastChangedId() {
        return lastChangedId;
    }

    public void setLastChangedId(int lastChangedId) {
        this.lastChangedId = lastChangedId;
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

    public int totalCustomers(){
        return routes.stream().mapToInt(r->r.getCustomers().size()).sum();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        int totalDistance = 0;

        sb.append(routes.size()+"\r\n");

        for(int i=0; i<routes.size(); i++){
            sb.append(i + 1).append(": ");
            sb.append("0(0)->");
            Route r = routes.get(i);
            Route r1 = r.copy();
            r1.getCustomers().clear();
            for(int j=0; j<r.getCustomers().size(); j++){
                Customer c = r.getCustomers().get(j);
                r1.addCustomer(c);
                sb.append(c.getId()).append("(").append((int)(r1.getTotalTime() - c.getServiceTime())).append(")");
                if(j!=r.getCustomers().size()-1){
                    sb.append("->");
                }
                else {
                    sb.append("->0(0)");
                }
            }
            sb.append("\r\n");
        }

        sb.append(totalDistance);

        return sb.toString();
    }
}
