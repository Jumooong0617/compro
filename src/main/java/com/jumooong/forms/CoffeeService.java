package com.jumooong.forms;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private List<Coffee> coffees;
    private final String FILE_NAME = "coffee_database.csv";

    public CoffeeService() {
        coffees = new ArrayList<>();
        readFromDisk();
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return coffees;
        }

        return coffees.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        c.getSize().toLowerCase().contains(keyword.toLowerCase()) ||
                        (c.getOrigin() != null && c.getOrigin().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    public Coffee getCoffee(int id) {
        return coffees.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
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
        int nextId = getNextId();
        coffee.setId(nextId);
        if (coffee.getFlavorNotes() == null) {
            coffee.setFlavorNotes(new ArrayList<>());
        }
        coffees.add(coffee);
        writeToDisk();
    }

    public int getNextId() {
        return coffees.stream().mapToInt(Coffee::getId).max().orElse(0) + 1;
    }

    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee c : coffees) {
                bw.write(String.join(",", List.of(
                        String.valueOf(c.getId()),
                        escape(c.getName()),
                        escape(c.getType()),
                        escape(c.getSize()),
                        String.valueOf(c.getPrice()),
                        escape(c.getRoastLevel()),
                        escape(c.getOrigin() != null ? c.getOrigin() : ""),
                        String.valueOf(c.isDecaf()),
                        String.valueOf(c.getStock()),
                        escape(String.join(";", c.getFlavorNotes() != null ? c.getFlavorNotes() : Collections.emptyList())),
                        escape(c.getBrewMethod())
                )));
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
                String[] data = line.split(",", -1); // -1 keeps empty strings
                if (data.length < 11) continue;

                List<String> flavorNotes = data[9].isEmpty() ? new ArrayList<>() : List.of(data[9].split(";"));
                Coffee coffee = new Coffee(
                        Integer.parseInt(data[0]),
                        data[1], data[2], data[3], Double.parseDouble(data[4]),
                        data[5], data[6], Boolean.parseBoolean(data[7]),
                        Integer.parseInt(data[8]), flavorNotes, data[10]
                );
                coffees.add(coffee);
            }
        } catch (IOException e) {
            System.out.println("Error reading coffee data: " + e.getMessage());
        }
    }

    private String escape(String input) {
        return input.replace(",", "&#44;").replace("\n", "").replace("\r", "");
    }
}
