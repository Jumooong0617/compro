package com.jumooong.forms;

import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Coffee {
    private int id;

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Size is required")
    private String size;

    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private double price;

    @NotBlank(message = "Roast level is required")
    private String roastLevel;

    @Size(max = 100, message = "Origin must be at most 100 characters")
    private String origin;

    private boolean isDecaf;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    private List<String> flavorNotes = new ArrayList<>();

    @NotBlank(message = "Brew method is required")
    private String brewMethod;

    public Coffee() {
        this.name = "";
        this.type = "";
        this.size = "";
        this.price = 0.0;
        this.roastLevel = "";
        this.origin = "";
        this.isDecaf = false;
        this.stock = 0;
        this.brewMethod = "";
    }

    public Coffee(int id, String name, String type, String size, double price, String roastLevel, String origin, boolean isDecaf, int stock, List<String> flavorNotes, String brewMethod) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoastLevel() {
        return roastLevel;
    }

    public void setRoastLevel(String roastLevel) {
        this.roastLevel = roastLevel;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isDecaf() {
        return isDecaf;
    }

    public void setDecaf(boolean isDecaf) {
        this.isDecaf = isDecaf;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getFlavorNotes() {
        return flavorNotes;
    }

    public void setFlavorNotes(List<String> flavorNotes) {
        this.flavorNotes = flavorNotes;
    }

    public String getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(String brewMethod) {
        this.brewMethod = brewMethod;
    }
}
