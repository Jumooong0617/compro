package com.jumooong.forms.services;

import com.jumooong.forms.models.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<AppUser> users;

    @PostConstruct
    public void init() throws IOException {
        users = new ArrayList<>();
        File file = new File("data/users.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;
                AppUser appUser = new AppUser();
                appUser.setUsername(parts[0].trim());
                appUser.setPassword(parts[1].trim());
                users.add(appUser);
            }
        }
    }

    public AppUser findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(AppUser appUser) {
        users.add(appUser);
    }
}
