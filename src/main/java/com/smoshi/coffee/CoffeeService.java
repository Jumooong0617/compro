package com.smoshi.coffee;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private ArrayList<Coffee> coffees;
    private final String FILE_NAME = "coffee_database.csv";

    public CoffeeService() {
        coffees = new ArrayList<>();
        readFromDisk();
    }

    public ArrayList<Coffee> getCoffees() {
        return coffees;
    }

    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return coffees;
        }

        String lower = keyword.toLowerCase();
        return coffees.stream().filter(c ->
                c.getName() != null && c.getName().toLowerCase().contains(lower) ||
                        c.getType() != null && c.getType().toLowerCase().contains(lower) ||
                        c.getSize() != null && c.getSize().toLowerCase().contains(lower) ||
                        c.getRoastLevel() != null && c.getRoastLevel().toLowerCase().contains(lower) ||
                        c.getOrigin() != null && c.getOrigin().toLowerCase().contains(lower) ||
                        c.getFlavorNotes() != null && c.getFlavorNotes().toString().toLowerCase().contains(lower) ||
                        c.getBrewMethod() != null && c.getBrewMethod().toLowerCase().contains(lower) ||
                        (c.isDecaf()  && (lower.contains("decaf") || lower.contains("decaffeinated")))
        ).collect(Collectors.toList());
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
        coffee.setId(getLastId() + 1);
        coffees.add(coffee);
        writeToDisk();
    }

    public int getLastId() {
        if (coffees.isEmpty()) {
            return 0;
        }
        return coffees.get(coffees.size() - 1).getId();
    }

    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee c : coffees) {
                bw.write(c.getId() + ","
                        + c.getName() + ","
                        + c.getType() + ","
                        + c.getSize() + ","
                        + c.getPrice() + ","
                        + c.getRoastLevel() + ","
                        + c.getOrigin() + ","
                        + c.isDecaf() + ","
                        + c.getStock() + ","
                        + c.getBrewMethod() + ","
                        + String.join(";", c.getFlavorNotes())); // Storing flavor notes as a semi-colon separated string
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Error writing: " + e.getMessage());
        }
    }

    public void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No coffee file found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 11) continue;

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

                if(data.length >= 11){
                    c.setFlavorNotes(data[10]);
                }else{
                    c.setFlavorNotes("");
                }

                coffees.add(c);
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Error reading: " + e.getMessage());
        }
    }
}

