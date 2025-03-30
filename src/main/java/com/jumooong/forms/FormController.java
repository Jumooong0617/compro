package com.jumooong.forms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller class that handles requests related to coffee inventory management.
 */
@Controller
public class FormController {
    private final CoffeeService coffeeService;

    /**
     * Constructor initializes the coffee service.
     */
    public FormController() {
        coffeeService = new CoffeeService();
    }

    /**
     * Displays the list of coffees.
     *
     * @param search (optional) search query for filtering coffee list.
     * @param model  Spring Model to pass data to the view.
     * @return The name of the HTML template (index.html).
     */
    @GetMapping("/")
    public String index(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("coffees", coffeeService.getCoffees());
        return "index";
    }

    /**
     * Deletes a coffee entry based on its ID.
     *
     * @param id ID of the coffee to be deleted.
     * @return Redirects back to the main page.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    /**
     * Displays the coffee creation form.
     *
     * @param model Spring Model to pass roast levels and brew methods.
     * @return The name of the HTML template (create.html).
     */
    @GetMapping("/new")
    public String create(Model model) {
        List<String> roastLevels = List.of("Light", "Medium", "Dark");
        List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "create";
    }

    /**
     * Saves a new coffee entry.
     *
     * @param name        Coffee name.
     * @param type        Type of coffee (e.g., Arabica, Robusta).
     * @param size        Size of the coffee (Small, Medium, Large).
     * @param price       Price of the coffee.
     * @param roastLevel  Roast level (Light, Medium, Dark).
     * @param origin      Country of origin.
     * @param isDecaf     Whether the coffee is decaffeinated.
     * @param stock       Available stock quantity.
     * @param flavorNotes List of flavor notes.
     * @param brewMethod  Preferred brewing method.
     * @return Redirects to the main page after saving.
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
                        @RequestParam List<String> flavorNotes,
                        @RequestParam String brewMethod) {

        Coffee c = new Coffee(coffeeService.getLastId() + 1, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod);
        coffeeService.addCoffee(c);
        return "redirect:/";
    }

    /**
     * Displays the edit form for a specific coffee.
     *
     * @param id    ID of the coffee to edit.
     * @param model Spring Model to pass coffee details, roast levels, and brew methods.
     * @return The name of the HTML template (edit.html) if the coffee exists; otherwise, redirects to the main page.
     */
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            List<String> roastLevels = List.of("Light", "Medium", "Dark");
            List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("coffee", c);
            return "edit";
        }
        return "redirect:/";
    }

    /**
     * Updates an existing coffee entry.
     *
     * @param id          ID of the coffee to update.
     * @param name        Updated coffee name.
     * @param type        Updated type of coffee.
     * @param size        Updated size of coffee.
     * @param price       Updated price.
     * @param roastLevel  Updated roast level.
     * @param origin      Updated country of origin.
     * @param isDecaf     Updated decaffeination status.
     * @param stock       Updated stock quantity.
     * @param flavorNotes Updated list of flavor notes.
     * @param brewMethod  Updated brew method.
     * @return Redirects to the main page after updating.
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
                         @RequestParam List<String> flavorNotes,
                         @RequestParam String brewMethod) {

        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            c.setName(name);
            c.setType(type);
            c.setSize(size);
            c.setPrice(price);
            c.setRoastLevel(roastLevel);
            c.setOrigin(origin);
            c.setDecaf(isDecaf);
            c.setStock(stock);
            c.setFlavorNotes(flavorNotes);
            c.setBrewMethod(brewMethod);

            coffeeService.updateCoffee(id, c);
        }
        return "redirect:/";
    }
}
