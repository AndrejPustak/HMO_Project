package hr.fer.hmo.cvrptw;

import hr.fer.hmo.cvrptw.UI.Display;
import hr.fer.hmo.cvrptw.algorithms.Greedy;

import javax.swing.*;
import java.nio.file.Paths;

public class CVRPTW {

    public static void main(String[] args) {

        Algorithm greedy = new Greedy();
        Instance i1 = Utils.readInstance("instances/i1.txt");

        Solution inital = greedy.getInitialSolution(i1);

        SwingUtilities.invokeLater(()->{
            JFrame frame = new Display(inital);
            frame.setVisible(true);
        });

        System.out.println("Start");

    }
}
