package hr.fer.hmo.cvrptw.UI;

import hr.fer.hmo.cvrptw.Customer;
import hr.fer.hmo.cvrptw.Route;
import hr.fer.hmo.cvrptw.Solution;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Map extends JPanel {

    Solution solution;

    public Map(Solution solution) {
        super();
        this.solution = solution;

    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        List<Route> routeList = solution.getRoutes();

        for (Route route : routeList) {

            Random rand = new Random();

            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();

            g2d.setColor(new Color(r, g, b));

            Customer c = route.getBase();
            for (int i = 0; i < route.getCustomers().size(); i++) {

                g2d.drawLine(c.getxCoord() * 10, c.getyCoord() * 10, route.getCustomers().get(i).getxCoord() * 10, route.getCustomers().get(i).getyCoord() * 10);

                c = route.getCustomers().get(i);

            }

            g2d.drawLine(c.getxCoord() * 10, c.getyCoord() * 10, route.getBase().getxCoord()*10, route.getBase().getyCoord()*10);


        }
    }
}
