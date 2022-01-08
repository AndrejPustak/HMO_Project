package hr.fer.hmo.cvrptw;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    private List<Route> routes;

    public Solution() {

        routes = new ArrayList<>();

    }

    public void addRoute(Route route){
        routes.add(route);
    }

    public List<Route> getRoutes() {
        return routes;
    }


}
