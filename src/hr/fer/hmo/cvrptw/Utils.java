package hr.fer.hmo.cvrptw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Instance readInstance(String filePath){

        List<Customer> customers = new ArrayList<>();

        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] data = lines.get(2).strip().split("\\s+");

        int vehicleNumber = Integer.parseInt(data[0]);
        int capacity = Integer.parseInt(data[1]);

        for(int i = 7; i < lines.size(); i++){
            customers.add(customerFromLine(lines.get(i)));
        }

        return new Instance(vehicleNumber, capacity, customers);

    }

    private static Customer customerFromLine(String line) {

        String[] data = line.strip().split("\\s+");

        return new Customer(
                Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Integer.parseInt(data[4]),
                Integer.parseInt(data[5]),
                Integer.parseInt(data[6])
        );

    }

}
