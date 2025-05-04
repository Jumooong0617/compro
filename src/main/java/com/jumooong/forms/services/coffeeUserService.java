package com.jumooong.forms.services;

import com.jumooong.forms.models.CoffeeUser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class coffeeUserService {

    private List<CoffeeUser> coffeeUser;

    private final String FILE_PATH = "data/coffee_users.csv";

    @PostConstruct
    public void init() throws IOException {
        coffeeUser = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Check if the file exists, if not, create it
        if (!file.exists()) {
            file.getParentFile().mkdirs();  // Create directories if they don't exist
            file.createNewFile();  // Create the file
        }

        // Read users from the CSV file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        reader.readLine(); // skip header if it exists
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            CoffeeUser user = new CoffeeUser();
            user.setUsername(parts[0]);
            user.setPassword(parts[1]);
            coffeeUser.add(user);
        }
    }

    // Find a user by username
    public CoffeeUser findByUsername(String username) {
        return coffeeUser.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Save a new user to the CSV file
    public void save(CoffeeUser user) {
        // Add the user to the list
        coffeeUser.add(user);

        // Write all users to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Optional: Save all users to CSV (useful for saving multiple users at once)
    public void saveAll() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (CoffeeUser user : coffeeUser) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving all users: " + e.getMessage());
        }
    }
}
