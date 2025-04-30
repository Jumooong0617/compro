package com.smoshi.coffee;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeUserService {
    private List<CoffeeUser> coffeeUsers;

    @PostConstruct
    public void init() throws IOException {
        coffeeUsers = new ArrayList<>();
        File file = new File("data/coffee_users.csv");

        if (!file.exists()) {
            file.mkdirs();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    CoffeeUser user = new CoffeeUser();
                    user.setUsername(parts[0]);
                    user.setPassword(parts[1]);
                    coffeeUsers.add(user);
                }
            }
        }
    }

    public CoffeeUser findByUsername(String username) {
        return coffeeUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(CoffeeUser user) {

    }
}
