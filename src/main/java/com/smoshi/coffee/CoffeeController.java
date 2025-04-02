package com.smoshi.coffee;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CoffeeController {
    CoffeeService coffeeService;

    /**
     * Construct Coffee Controller of instant Coffee service
     */
    public CoffeeController() {
        coffeeService = new CoffeeService();
    }

    /**
     * Display coffee list filtered by search
     * @param search String search
     * @param model the object model
     * @return the html file index of default display of coffee list
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }


    /**
     * Deletes coffee entry by id
     * @param id identifier of coffee
     * @return the deleted coffee
     */
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    /**
     * Adds new coffee using form
     * @param model the object
     * @return the form of adding a new coffee
     */
    @GetMapping("/new")
    public String create(Model model) {
        List<String> roastLevels = List.of("Light", "Medium", "Dark");
        List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "add";
    }

    /**
     * it handles the post type request method (save)
     * @param name coffee name
     * @param type type of coffee
     * @param size size of coffee
     * @param price price of coffee
     * @param roastLevel roast level of coffee
     * @param origin origin of coffee
     * @param isDecaf boolean using yes or no if the coffee is decaffeinated
     * @param stock how many stock of coffee this the user ordered
     * @param flavorNotes flavor notes descriptions
     * @param brewMethod what method what they want
     * @return the post saves coffee entry
     */
    @PostMapping("/save")
    public String store(@RequestParam String name,
                        @RequestParam String type,
                        @RequestParam String size,
                        @RequestParam double price,
                        @RequestParam String roastLevel,
                        @RequestParam String origin,
                        @RequestParam boolean isDecaf,
                        @RequestParam int stock,
                        @RequestParam String flavorNotes,
                        @RequestParam String brewMethod) {

        Coffee coffee = new Coffee(coffeeService.getLastId() + 1, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    /**
     * it edits the form of coffee that they ordered
     * @param id identifier of coffee
     * @param model the object
     * @return the edited coffee form entry
     */
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            List<String> roastLevels = List.of("Light", "Medium", "Dark");
            List<String> brewMethods = List.of("Drip", "Cold Brew", "Espresso", "French Press");
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("coffee", coffee);
            return "edit";
        }
        return "redirect:/";
    }

    /**
     * updated coffee form entry
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
     * @return the updated coffee form
     */
    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam String type,
                         @RequestParam String size,
                         @RequestParam double price,
                         @RequestParam String roastLevel,
                         @RequestParam String origin,
                         @RequestParam boolean isDecaf,
                         @RequestParam int stock,
                         @RequestParam String flavorNotes,
                         @RequestParam String brewMethod) {
        // Retrieve the coffee object from the service by ID
        Coffee coffee = coffeeService.getCoffee(id);
        // Check if the coffee exists before updating
        if (coffee != null) {
            // Update coffee details with new values
            coffee.setName(name);
            coffee.setType(type);
            coffee.setSize(size);
            coffee.setPrice(price);
            coffee.setRoastLevel(roastLevel);
            coffee.setOrigin(origin);
            coffee.setDecaf(isDecaf);
            coffee.setStock(stock);
            coffee.setFlavorNotes(flavorNotes);
            coffee.setBrewMethod(brewMethod);
            // Save the updated coffee back to the service
            coffeeService.updateCoffee(id, coffee);
        }
        return "redirect:/";
    }
}