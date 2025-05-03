package com.smoshi.coffee.services;

import com.smoshi.coffee.models.CoffeeUser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoffeeUserService {
    private List<CoffeeUser> coffeeUser;

    @PostConstruct
    public void init() throws IOException {
        coffeeUser = new ArrayList<>();
        File file = new File("data/coffee_users.csv");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        reader.readLine(); // skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            CoffeeUser user = new CoffeeUser();
            user.setUsername(parts[0]);
            user.setPassword(parts[1]);
            coffeeUser.add(user);

        }
    }

    public CoffeeUser findByUsername(String username) {
        return coffeeUser.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(CoffeeUser user) {

    }
}
