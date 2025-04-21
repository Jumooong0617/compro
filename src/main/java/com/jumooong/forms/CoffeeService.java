package com.jumooong.forms;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to manage Coffee objects.
 * Handles CRUD operations and persistence using a CSV file.
 */
@Service
public class CoffeeService {
    private List<Coffee> coffees;
    private final String FILE_NAME = "coffee_database.csv";

    /**
     * Constructor initializes the coffee list and loads data from disk.
     */
    public CoffeeService() {
        coffees = new ArrayList<>();
        readFromDisk();
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return coffees;
        }

        return coffees.stream()
                .filter(s -> s.getName().toLowerCase().contains(keyword.toLowerCase())
                        || s.getSize().toLowerCase().contains(keyword.toLowerCase())
                        || s.getOrigin().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the list of coffee objects.
     * @return List of Coffee
     */
    public List<Coffee> getCoffees() {
        return coffees;
    }

    /**
     * Deletes a coffee entry by ID.
     * @param id The ID of the coffee to delete
     */
    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    /**
     * Retrieves a coffee object by ID.
     * @param id The ID of the coffee
     * @return Coffee object or null if not found
     */
    public Coffee getCoffee(int id) {
        for (Coffee c : coffees) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    /**
     * Updates an existing coffee entry.
     * @param id The ID of the coffee to update
     * @param update The updated Coffee object
     */
    public void updateCoffee(int id, Coffee update) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, update);
                writeToDisk();
                break;
            }
        }
    }

    /**
     * Adds a new coffee entry to the list and saves to disk.
     * @param coffee The Coffee object to add
     */
    public void addCoffee(Coffee coffee) {
        coffees.add(coffee);
        writeToDisk();
    }

    /**
     * Retrieves the last used ID in the coffee list.
     * @return The last coffee ID or 0 if list is empty
     */
    public int getLastId() {
        return coffees.isEmpty() ? 0 : coffees.get(coffees.size() - 1).getId();
    }

    /**
     * Writes the coffee list to a CSV file for persistence.
     */
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

    /**
     * Reads coffee data from a CSV file and populates the coffee list.
     */
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