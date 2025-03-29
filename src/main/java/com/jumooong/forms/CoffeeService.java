package com.jumooong.forms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeService {
    private List<Coffee> coffees;
    private final String FILE_NAME = "coffee_database.csv";

    public CoffeeService() {
        coffees = new ArrayList<>();
        readFromDisk();
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    public Coffee getCoffee(int id) {
        for (Coffee c : coffees) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public void updateCoffee(int id, Coffee update) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, update);
                writeToDisk();
                break;
            }
        }
    }

    public void addCoffee(Coffee coffee) {
        coffees.add(coffee);
        writeToDisk();
    }

    public int getLastId() {
        return coffees.isEmpty() ? 0 : coffees.get(coffees.size() - 1).getId();
    }

    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee c : coffees) {
                bw.write(c.getId() + "," + c.getName() + "," + c.getType() + "," + c.getSize() + "," + c.getPrice() + "," +
                        c.getRoastLevel() + "," + c.getOrigin() + "," + c.isDecaf() + "," + c.getStock() + "," +
                        String.join(";", c.getFlavorNotes()) + "," + c.getBrewMethod());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving coffee data: " + e.getMessage());
        }
    }

    public void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Coffee database file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                List<String> flavorNotes = List.of(data[9].split(";"));

                Coffee c = new Coffee(
                        Integer.parseInt(data[0]), data[1], data[2], data[3], Double.parseDouble(data[4]),
                        data[5], data[6], Boolean.parseBoolean(data[7]), Integer.parseInt(data[8]), flavorNotes, data[10]
                );
                coffees.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error reading coffee data: " + e.getMessage());
        }
    }
}
