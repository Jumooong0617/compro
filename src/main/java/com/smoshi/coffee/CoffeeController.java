package com.smoshi.coffee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    private final String[] types = {"Affogato", "Espresso", "Americano", "Latte", "Cappuccino", "Mocha", "Flat White", "Iced Coffee"};
    private final String[] sizes = {"Small", "Medium", "Large"};
    private final String[] roastLevels = {"Light", "Medium", "Dark"};
    private final String[] brewMethods = {"Drip", "French Press", "Espresso", "Filter"};

    /**
     * Displays the home page with a list of coffees.
     *
     * @param search Search query to filter coffee entries.
     * @param model  Model object for passing data to the view.
     * @return Name of the Thymeleaf template to render.
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    /**
     * Deletes a coffee entry by ID.
     *
     * @param id The ID of the coffee to delete.
     * @return Redirects to the home page after deletion.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    /**
     * Displays the add coffee form.
     *
     * @param model Model object for passing data to the view.
     * @return Name of the Thymeleaf template to render.
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("coffee", new Coffee());
        model.addAttribute("types", types);
        model.addAttribute("sizes", sizes);
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "add";
    }

    /**
     * Handles submission of the add coffee form.
     *
     * @param coffee         The Coffee object populated from the form.
     * @param bindingResult  Validation result.
     * @param model          Model object for passing data back to the view if there are errors.
     * @return Redirects to the home page or reloads the add form on validation failure.
     */
    @PostMapping("/save")
    public String store(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "add";
        }
        coffee.setId(coffeeService.getLastId() + 1);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    /**
     * Displays the edit form for a specific coffee entry.
     *
     * @param id    The ID of the coffee to edit.
     * @param model Model object for passing data to the view.
     * @return Name of the Thymeleaf template to render, or redirect to home if coffee not found.
     */
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "edit";
        }
        return "redirect:/";
    }

    /**
     * Handles submission of the edit coffee form.
     *
     * @param coffee         The updated Coffee object.
     * @param bindingResult  Validation result.
     * @param model          Model object for passing data back to the view if there are errors.
     * @return Redirects to the home page or reloads the edit form on validation failure.
     */
    @PostMapping("/update")
    public String update(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "edit";
        }
        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }
}
