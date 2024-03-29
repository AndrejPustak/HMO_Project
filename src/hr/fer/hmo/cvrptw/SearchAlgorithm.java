package hr.fer.hmo.cvrptw;

public abstract class SearchAlgorithm {

    public abstract Solution execute(Solution solution);
    public abstract Solution execute(Solution solution, int neighborhoodSize);

    public abstract Solution execute(Solution solution, int neighborhoodSize, long startTime);
}
