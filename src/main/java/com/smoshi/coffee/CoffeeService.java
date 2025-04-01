package com.smoshi.coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoffeeService {
    private List<Coffee> coffees = new ArrayList<>();
    private static final String FILE_NAME = "coffee_database.csv";

    public CoffeeService() {
        readFromDisk();
    }

    public List<Coffee> getCoffees() {
        return new ArrayList<>(coffees); // Return copy to prevent modifications
    }

    public void deleteCoffee(int id) {
        if (coffees.removeIf(c -> c.getId() == id)) {
            writeToDisk();
        }
    }

    public Coffee getCoffee(int id) {
        return coffees.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void updateCoffee(int id, Coffee update) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, update);
                writeToDisk();
                return;
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

    private void writeToDisk() {
        if (coffees.isEmpty()) return; // Avoid writing empty files

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee c : coffees) {
                String csvLine = String.join(",",
                        String.valueOf(c.getId()), c.getName(), c.getType(), c.getSize(),
                        String.valueOf(c.getPrice()), c.getRoastLevel(), c.getOrigin(),
                        String.valueOf(c.isDecaf()), String.valueOf(c.getStock()), c.getBrewMethod(), c.getFlavorNotes());
                bw.write(csvLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving coffee data: " + e.getMessage());
        }
    }

    private void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No existing coffee database found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 11) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                try {
                    Coffee c = new Coffee();
                    c.setId(Integer.parseInt(data[0]));
                    c.setName(data[1]);
                    c.setType(data[2]);
                    c.setSize(data[3]);
                    c.setPrice(Double.parseDouble(data[4]));
                    c.setRoastLevel(data[5]);
                    c.setOrigin(data[6]);
                    c.setDecaf(Boolean.parseBoolean(data[7]));
                    c.setStock(Integer.parseInt(data[8]));
                    c.setBrewMethod(data[9]);
                    c.setFlavorNotes(data[10]); // Keeps flavorNotes as a string

                    coffees.add(c);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
            System.out.println("Coffee data successfully loaded.");
        } catch (IOException e) {
            System.err.println("Error reading coffee data: " + e.getMessage());
        }
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword.trim().isEmpty()) {
            return new ArrayList<>(coffees);
        }

        return coffees.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        c.getType().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
