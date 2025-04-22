package com.smoshi.coffee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class Coffee {

    private int id;

    @NotBlank(message = "Coffee name is required")
    private String name;

    @NotBlank(message = "Coffee type is required")
    private String type;

    @NotBlank(message = "Coffee size is required")
    private String size;

    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private double price;

    @NotBlank(message = "Roast level is required")
    private String roastLevel;

    @NotBlank(message = "Origin is required")
    private String origin;

    private boolean isDecaf;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    private List<String> flavorNotes;

    @NotBlank(message = "Brew method is required")
    private String brewMethod;

    // Constructors, getters, setters, etc.

    public Coffee() {}

    public Coffee(int id, String name, String type, String size, double price, String roastLevel,
                  String origin, boolean isDecaf, int stock, List<String> flavorNotes, String brewMethod) {
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

