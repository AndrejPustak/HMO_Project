package hr.fer.hmo.cvrptw.algorithms;

import hr.fer.hmo.cvrptw.Customer;
import hr.fer.hmo.cvrptw.Instance;
import hr.fer.hmo.cvrptw.SearchAlgorithm;
import hr.fer.hmo.cvrptw.Solution;

import java.util.*;
import java.util.function.Predicate;

public class TabuSearch extends SearchAlgorithm {

    int tabooLife;
    public TabuSearch(Instance instance){
        tabooLife = instance.getCustomers().size()/5;
    }

    private class Move{

        public int customerId;
        public int life;

        public Move(int customerId){
            this.customerId = customerId;
            life = tabooLife;
        }

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

    private int iteration;

    @Override
    public Solution execute(Solution solution) {
        return execute(solution, 1000);
    }

    @Override
    public Solution execute(Solution inital, int neighbourhoodSize) {

        Set<Move> tabuList = new HashSet<>();

        Solution incumbent = inital.copy();
        Solution current = incumbent.copy();

        while(!stopingCondition()){

            List<Solution> neighborhood = generateNeighborhood(current, neighbourhoodSize, tabuList);
            Optional<Solution> optionalBest = neighborhood.stream().max(Solution::compareTo);

            Solution best;
            if(optionalBest.isPresent()){
                best = optionalBest.get();
                current = best;

                tabuList.add(new Move(current.getLastChangedId()));

                if(incumbent.compareTo(current)<0){
                    incumbent = current;
                }
            }

            Iterator<Move> moveIterator = tabuList.iterator();
            while(moveIterator.hasNext()){
                Move move = moveIterator.next();
                if(move.life==0){
                    moveIterator.remove();
                }
                else {
                    move.life--;
                }
            }

            if(iteration++ %100 ==0){
                System.out.println("Tabu search, iteration = " + iteration);
            }
        }

        return incumbent;
    }

    private List<Solution> generateNeighborhood(Solution solution, int size, Set<Move> tabuList) {
        List<Solution> solutions = new ArrayList<>();

        while(solutions.size()<size){
            Solution temp = solution.copy();

            Random random = new Random();

            int r,c;

            do {
                r = random.nextInt(temp.totalVehicles());
                c = random.nextInt(temp.getRoutes().get(r).getCustomers().size());
            }while (tabuList.contains(new Move(temp.getRoutes().get(r).getCustomers().get(c).getId())));

            Customer removed = temp.getRoutes().get(r).removeCustomer(c);
            if(temp.getRoutes().get(r).getCustomers().size() == 0) temp.getRoutes().remove(r);

            for(int i = 0; i<temp.totalVehicles(); i++){
                if(i == r) continue;
                for(int j = 0; j<temp.getRoutes().get(i).getCustomers().size()+1;j++){
                    Solution possible = temp.copy();
                    if(possible.getRoutes().get(i).insertCustomer(removed, j)){

                        solutions.add(possible);
                        possible.setLastChangedId(removed.getId());
                    }
                }
            }


        }

        return solutions;
    }

    private boolean stopingCondition() {

        if(iteration >= 2000) return true;

        return false;

    }
}
