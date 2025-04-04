package com.smoshi.coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing coffee data, including CRUD operations,
 * search functionality, and persistence to a CSV file.
 */
public class CoffeeService {
    private List<Coffee> coffees = new ArrayList<>();
    private static final String FILE_NAME = "coffee_database.csv";

    /**
     * Constructor that initializes the coffee list by reading from disk.
     */
    public CoffeeService() {
        readFromDisk();
    }

    /**
     * Retrieves the list of all coffees.
     *
     * @return a copy of the list of coffees to prevent modifications
     */
    public List<Coffee> getCoffees() {
        return new ArrayList<>(coffees); // Return copy to prevent modifications
    }

    /**
     * Searches for coffees based on a keyword.
     *
     * @param keyword the search keyword
     * @return a list of coffees that match the keyword in name or type
     */
    public List<Coffee> searchCoffee(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(coffees);
        }

        String keywordLower = keyword.toLowerCase().trim();

        return coffees.stream()
                .filter(c ->
                        c.getName().toLowerCase().contains(keywordLower) ||
                                c.getType().toLowerCase().contains(keywordLower) ||
                                c.getSize().toLowerCase().contains(keywordLower) ||
                                c.getRoastLevel().toLowerCase().contains(keywordLower) ||
                                c.getOrigin().toLowerCase().contains(keywordLower) ||
                                c.getFlavorNotes().toLowerCase().contains(keywordLower) ||
                                c.getBrewMethod().toLowerCase().contains(keywordLower) ||
                                (c.isDecaf() && (
                                        keywordLower.contains("decaf") ||
                                                keywordLower.contains("decaffeinated")
                                ))
                )
                .collect(Collectors.toList());
    }



    /**
     * Deletes a coffee entry by ID.
     *
     * @param id the ID of the coffee to delete
     */
    public void deleteCoffee(int id) {
        if (coffees.removeIf(c -> c.getId() == id)) {
            writeToDisk();
        }
    }

    /**
     * Retrieves a coffee entry by ID.
     *
     * @param id the ID of the coffee
     * @return the Coffee object if found, otherwise null
     */
    public Coffee getCoffee(int id) {
        return coffees.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates a coffee entry by replacing it with a new coffee object.
     *
     * @param id the ID of the coffee to update
     * @param update the new Coffee object
     */
    public void updateCoffee(int id, Coffee update) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, update);
                writeToDisk();
                return;
            }
        }
    }

    /**
     * Adds a new coffee entry.
     *
     * @param coffee the Coffee object to add
     */
    public void addCoffee(Coffee coffee) {
        coffees.add(coffee);
        writeToDisk();
    }

    /**
     * Gets the last used coffee ID.
     *
     * @return the last coffee ID, or 0 if no coffee exists
     */
    public int getLastId() {
        return coffees.isEmpty() ? 0 : coffees.get(coffees.size() - 1).getId();
    }

    /**
     * Writes the current coffee list to a CSV file.
     */
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
            System.out.println("Error saving coffee data: " + e.getMessage());
        }
    }

    /**
     * Reads coffee data from a CSV file and loads it into memory.
     */
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
                    System.out.println("Skipping malformed line: " + line);
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
                    System.out.println("Skipping invalid entry: " + line);
                }
            }
            System.out.println("Coffee data successfully loaded.");
        } catch (IOException e) {
            System.out.println("Error reading coffee data: " + e.getMessage());
        }
    }
}
