package hr.fer.hmo.cvrptw;

import hr.fer.hmo.cvrptw.UI.Display;
import hr.fer.hmo.cvrptw.algorithms.Greedy;
import hr.fer.hmo.cvrptw.algorithms.TabuSearch;

import javax.swing.*;
import java.nio.file.Paths;

public class CVRPTW {

    public static void main(String[] args) {

        Instance i1 = Utils.readInstance("instances/i1.txt");

        Algorithm greedy = new Greedy();
        SearchAlgorithm taboo = new TabuSearch(i1);

        int neigbourhoodSize = 1000;

        Solution inital = greedy.getInitialSolution(i1);
        Solution tabooSolution = taboo.execute(inital, neigbourhoodSize);

        SwingUtilities.invokeLater(()->{
            JFrame frame = new Display(tabooSolution);
            frame.setVisible(true);
        });


        System.out.println("Greedy:");
        System.out.println("    Vehicles: "+tabooSolution.totalVehicles());
        System.out.println("    Time: "+tabooSolution.totalTime());

        System.out.println("Taboo:");
        System.out.println("    Vehicles: "+tabooSolution.totalVehicles());
        System.out.println("    Time: "+tabooSolution.totalTime());

    }
}
