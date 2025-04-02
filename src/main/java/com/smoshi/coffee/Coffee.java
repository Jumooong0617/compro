package com.smoshi.coffee;

public class Coffee {
    private int id;
    private String name;
    private String type;
    private String size;
    private double price;
    private String roastLevel;
    private String origin;
    private boolean isDecaf;
    private int stock;
    private String flavorNotes;
    private String brewMethod;

    public Coffee() {}

    /**
     * Parameterized constructor for creating a Coffee object with specified details.
     *
     * @param id          Unique identifier for the coffee
     * @param name        Name of the coffee
     * @param type        Type of coffee (e.g., espresso, cappuccino)
     * @param size        Size of the coffee (small, medium, large)
     * @param price       Price of the coffee
     * @param roastLevel  Roast level (light, medium, dark)
     * @param origin      Origin of the coffee beans
     * @param isDecaf     Whether the coffee is decaffeinated
     * @param stock       Number of units in stock
     * @param flavorNotes Description of the flavor profile
     * @param brewMethod  Preferred brewing method
     */
    public Coffee(int id, String name, String type, String size, double price, String roastLevel,
                  String origin, boolean isDecaf, int stock, String flavorNotes, String brewMethod) {
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

    public String getFlavorNotes() {
        return flavorNotes;
    }
    public void setFlavorNotes(String flavorNotes) {
        this.flavorNotes = flavorNotes;
    }

    public String getBrewMethod() {
        return brewMethod;
    }
    public void setBrewMethod(String brewMethod) {
        this.brewMethod = brewMethod;
    }
}
