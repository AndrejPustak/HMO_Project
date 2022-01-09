package hr.fer.hmo.cvrptw.algorithms;

import hr.fer.hmo.cvrptw.Customer;
import hr.fer.hmo.cvrptw.SearchAlgorithm;
import hr.fer.hmo.cvrptw.Solution;

import java.util.*;

public class TabuSearch extends SearchAlgorithm {

    private class Move{
        public int customerId;
        public int life = 0;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return customerId == move.customerId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(customerId);
        }
    }

    private int iterations;

    @Override
    public Solution execute(Solution inital) {

        Random random = new Random();
        Set<Move> tabuList = new HashSet<>();

        Solution incumbent = inital.copy();
        Solution current = incumbent.copy();

        while(!stopingCondition()){

            List<Solution> neighborhood = generateNeighborhood(current, 1000);


        }

        return null;
    }

    private List<Solution> generateNeighborhood(Solution solution, int size) {

        return null;

    }

    private boolean stopingCondition() {

        if(iterations >= 10000) return true;

        return false;

    }
}
