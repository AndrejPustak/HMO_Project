package hr.fer.hmo.cvrptw.UI;

import hr.fer.hmo.cvrptw.Customer;
import hr.fer.hmo.cvrptw.Route;
import hr.fer.hmo.cvrptw.Solution;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Display extends JFrame {

    private Solution solution;
    private JPanel panel;

    public Display(Solution solution){

        this.solution = solution;

        panel = new Map(solution);

        add(panel);
        setSize(new Dimension(1000, 1000));


    }

}
