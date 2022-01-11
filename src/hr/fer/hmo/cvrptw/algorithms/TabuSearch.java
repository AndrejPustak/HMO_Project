package hr.fer.hmo.cvrptw.algorithms;

import hr.fer.hmo.cvrptw.Customer;
import hr.fer.hmo.cvrptw.Instance;
import hr.fer.hmo.cvrptw.SearchAlgorithm;
import hr.fer.hmo.cvrptw.Solution;

import java.util.*;
import java.util.function.Predicate;

public class TabuSearch extends SearchAlgorithm {

    int maxIter = 500;

    long startTime;
    long endTime;

    int timeLimit;

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
        return execute(solution, 1000, System.currentTimeMillis());
    }

    @Override
    public Solution execute(Solution solution, int neighborhoodSize) {
        return execute(solution, neighborhoodSize, System.currentTimeMillis());
    }

    @Override
    public Solution execute(Solution inital, int neighbourhoodSize, long startTime) {

        this.startTime = System.currentTimeMillis() - startTime;

        Set<Move> tabuList = new HashSet<>();

        Solution incumbent = inital.copy();
        Solution current = incumbent.copy();

        while(!stopingCondition()){

            List<Solution> neighborhood = generateNeighborhood(current, neighbourhoodSize, tabuList);
            if(neighborhood.size() == 0) continue;
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

        endTime = System.currentTimeMillis();
        if(timeLimit > 0){
            if((endTime-startTime) / 1000 > timeLimit) return true;
        }

        if(maxIter > 0)
            if(iteration >= maxIter) return true;

        return false;

    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }
}
