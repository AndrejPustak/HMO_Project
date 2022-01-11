package hr.fer.hmo.cvrptw;

import hr.fer.hmo.cvrptw.UI.Display;
import hr.fer.hmo.cvrptw.algorithms.Greedy;
import hr.fer.hmo.cvrptw.algorithms.TabuSearch;

import javax.swing.*;
import java.nio.file.Paths;

public class CVRPTW {

    public static void main(String[] args) {


        runInstance("i1", "un", false);
        runInstance("i2", "un", false);
        runInstance("i3", "un", false);
        runInstance("i4", "un", false);
        runInstance("i5", "un", false);
        runInstance("i6", "un", false);



    }

    public static void runInstance(String instance, String time, boolean ui){
        Instance inst = Utils.readInstance(String.format("instances/%s.txt", instance));
        int totalCustomers = inst.getCustomers().size();
        Algorithm greedy = new Greedy();
        TabuSearch taboo = new TabuSearch(inst);

        taboo.setMaxIter(-1);

        if(time.equals("1m")) taboo.setTimeLimit(60);
        if(time.equals("5m")) taboo.setTimeLimit(300);
        if(time.equals("un")) {
            taboo.setTimeLimit(600);
            taboo.setMaxIter(10000);
        }

        long start = System.currentTimeMillis();
        Solution intial = greedy.getInitialSolution(inst);
        long end = System.currentTimeMillis();
        Solution solution = taboo.execute(intial, totalCustomers/2, end - start);

        System.out.println("Greedy:");
        System.out.println("    Vehicles: "+intial.totalVehicles());
        System.out.println("    Time: "+intial.totalTime());
        System.out.println("    Customers: "+intial.totalCustomers());
        System.out.println(intial);

        System.out.println("Taboo:");
        System.out.println("    Vehicles: "+solution.totalVehicles());
        System.out.println("    Time: "+solution.totalTime());
        System.out.println("    Customers: "+solution.totalCustomers());
        System.out.println(solution);

        if(ui) SwingUtilities.invokeLater(()->{
            JFrame frame = new Display(solution);
            frame.setVisible(true);
        });

        Utils.saveSolution(String.format("out/res-%s-%s.txt", time, instance), solution);
    }
}
